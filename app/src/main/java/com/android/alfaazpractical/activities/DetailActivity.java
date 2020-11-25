package com.android.alfaazpractical.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.alfaazpractical.adapter.ListAdapter;
import com.android.alfaazpractical.api.ApiClient;
import com.android.alfaazpractical.api.ApiInterface;
import com.android.alfaazpractical.core.Constant;
import com.android.alfaazpractical.databinding.ActivityDetailBinding;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getIntExtra("id", 0);

        getDetail();
    }

    private void getDetail() {

        Call<JsonObject> call = ApiClient.getClient().create(ApiInterface.class).getMovieDetail(id, Constant.TMDB_API);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null && response.isSuccessful()){
                    if (response.code() == 200){
                        ListAdapter.loadImage(DetailActivity.this, binding.ivCover, Constant.BACKDROP_PATH + response.body().get("backdrop_path").getAsString());
                        ListAdapter.loadImage(DetailActivity.this, binding.ivPoster, Constant.BACKDROP_PATH + response.body().get("poster_path").getAsString());
                        binding.tvTitle.setText(response.body().get("title").getAsString());
                        binding.tvOverview.setText(response.body().get("overview").getAsString());
                        binding.tvReleaseDate.setText(response.body().get("release_date").getAsString());
                        binding.tvRating.setText("IMDB : " + response.body().get("vote_average").getAsDouble());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}