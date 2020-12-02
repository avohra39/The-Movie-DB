package com.android.alfaazpractical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.alfaazpractical.adapter.ListAdapter;
import com.android.alfaazpractical.api.ApiClient;
import com.android.alfaazpractical.api.ApiInterface;
import com.android.alfaazpractical.core.Constant;
import com.android.alfaazpractical.databinding.ActivityDetailBinding;
import com.google.gson.JsonObject;

import java.util.Objects;

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

    public void pushFragmentOnlyReplace(Fragment fragment) {
        FragmentManager fragmentManager = Objects.requireNonNull(this).getSupportFragmentManager();
        FragmentTransaction ftx = fragmentManager.beginTransaction();
        ftx.replace(R.id.flContainer, fragment);
        ftx.commitAllowingStateLoss();
    }

    public void pushFragmentIgnoreCurrent(Fragment fragment, int fragmentTransactionType) {
        switch (fragmentTransactionType) {
            case Constant.FRAGMENT_JUST_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, false, true, false, false);
                break;
            case Constant.FRAGMENT_JUST_ADD:
                pushFragments(R.id.flContainer, null, fragment, true, false, true, true, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, true, true, true, false, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_ADD:
                pushFragments(R.id.flContainer, null, fragment, true, true, true, true, false);
                break;
        }
    }

    public void pushFragmentIgnoreCurrentWithAnimation(Fragment fragment, int fragmentTransactionType) {
        switch (fragmentTransactionType) {
            case Constant.FRAGMENT_JUST_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, false, true, false, true);
                break;
            case Constant.FRAGMENT_JUST_ADD:
                pushFragments(R.id.flContainer, null, fragment, false, false, true, true, true);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, true, true, false, true);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_ADD:
                pushFragments(R.id.flContainer, null, fragment, false, true, true, true, true);
                break;
        }
    }

    public void pushFragmentDontIgnoreCurrent(Fragment fragment, int fragmentTransactionType) {
        switch (fragmentTransactionType) {
            case Constant.FRAGMENT_JUST_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, false, false, false, false);
                break;
            case Constant.FRAGMENT_JUST_ADD:
                pushFragments(R.id.flContainer, null, fragment, false, false, false, true, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, true, false, false, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_ADD:
                pushFragments(R.id.flContainer, null, fragment, false, true, false, true, false);
                break;
        }
    }

    public void pushFragmentDontIgnoreCurrentWithAnimation(Fragment fragment, int fragmentTransactionType) {
        switch (fragmentTransactionType) {
            case Constant.FRAGMENT_JUST_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, false, false, false, false, false);
                break;
            case Constant.FRAGMENT_JUST_ADD:
                pushFragments(R.id.flContainer, null, fragment, true, false, false, true, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_REPLACE:
                pushFragments(R.id.flContainer, null, fragment, true, true, false, false, false);
                break;
            case Constant.FRAGMENT_ADD_TO_BACKSTACK_AND_ADD:
                pushFragments(R.id.flContainer, null, fragment, true, true, false, true, false);
                break;
        }
    }

    public void pushFragments(final int id, final Bundle args, final Fragment fragment, boolean shouldAnimateLeftRight, final boolean shouldAdd, final boolean ignoreIfCurrent, final boolean justAdd, boolean shouldAnimateTopBottom) {

        try {
            hideKeyboard();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
            if (ignoreIfCurrent && currentFragment != null && fragment != null && currentFragment.getClass().equals(fragment.getClass())) {
                return;
            }

            // assert fragment != null;
            if (fragment.getArguments() == null) {
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (shouldAdd) {
                ft.addToBackStack(null);
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            if (justAdd) {
                //System.out.println("if");
                removeIfExists(fragmentManager, fragment);
                ft.add(id, fragment, fragment.getClass().getCanonicalName());
                if (getSupportFragmentManager().findFragmentById(R.id.flContainer) != null) {
                    // Fragment fragmentNew = context.getSupportFragmentManager().findFragmentById(R.id.container);
                    ft.hide(getSupportFragmentManager().findFragmentById(R.id.flContainer));
                }
            } else {
                ft.replace(R.id.flContainer, fragment);
            }
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
            /*view.clearFocus();
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY|InputMethodManager.RESULT_HIDDEN);*/
        }
    }

    private void removeIfExists(FragmentManager fragmentManager, Fragment fragment) {
        Fragment f = fragmentManager.findFragmentByTag(fragment.getClass().getCanonicalName());
        if (f != null && f.getClass().equals(fragment.getClass())) {
            fragmentManager.beginTransaction().remove(f).commit();
        }
    }

    public void clearBackStackFragmets() {
        // in my case I get the support fragment manager, it should work with the native one too
        FragmentManager fragmentManager = getSupportFragmentManager();
        // this will clear the back stack and displays no animation on the screen
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            for (int i = 0; i < count - 1; i++) {
                fm.popBackStack();
            }
        }
    }
}