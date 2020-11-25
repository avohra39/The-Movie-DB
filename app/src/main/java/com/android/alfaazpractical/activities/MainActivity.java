package com.android.alfaazpractical.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.alfaazpractical.R;
import com.android.alfaazpractical.adapter.ListAdapter;
import com.android.alfaazpractical.api.ApiClient;
import com.android.alfaazpractical.api.ApiInterface;
import com.android.alfaazpractical.core.Constant;
import com.android.alfaazpractical.database.Database;
import com.android.alfaazpractical.databinding.ActivityMainBinding;
import com.android.alfaazpractical.model.Main;
import com.android.alfaazpractical.model.Movie;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private int pastVisibleItem, visibleItemCount, totalItemCount;
    private List<Movie> movieList = new ArrayList<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private int page = 1;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Constant.isInternetAvailable(this)) {
            setAdapter();
            getTopRatedMovies();
        } else {
            getFromLocal();
        }
    }

    private void getTopRatedMovies() {

        Call<JsonObject> call = ApiClient.getClient().create(ApiInterface.class).getTopRaed(Constant.TMDB_API, page);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.code() == 200) {
                        Type type = new TypeToken<ArrayList<Movie>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ArrayList<Movie> movies = gson.fromJson(response.body().get("results").getAsJsonArray(), type);
                        movieList.addAll(movies);
                        page++;
                        loading = true;
                        adapter.notifyDataSetChanged();
                        //adding to local database
                        insertToLocal(movieList);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }

    private void insertToLocal(List<Movie> movies) {
        Task<List<Movie>> task = Tasks.call(executor, () -> {

            //adding to database
            Database.getInstance(this).getAppDatabase().movieDao().insert(movies);

            return movies;
        });
        task.addOnCompleteListener(task1 -> {
            if (task.isSuccessful()) {
            }
        });
    }

    private void getFromLocal() {
        Task<List<Movie>> task = Tasks.call(executor, () -> {

            List<Movie> list = Database.getInstance(this).getAppDatabase().movieDao().getAll();

            return list;
        });
        task.addOnCompleteListener(task1 -> {
            movieList = task1.getResult();
            if (movieList != null) {
                Collections.reverse(movieList);
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvTopRated.setLayoutManager(linearLayoutManager);
        adapter = new ListAdapter(this, movieList, (view, position) -> {
            if (view.getId() == R.id.cvClick) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("id", movieList.get(position).getId());
                startActivity(intent);
            }
        });
        binding.rvTopRated.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                            loading = false;
                            getTopRatedMovies();
                        }
                    }
                }
            }
        });
        binding.rvTopRated.setAdapter(adapter);
        binding.rvTopRated.setHasFixedSize(true);
    }
}