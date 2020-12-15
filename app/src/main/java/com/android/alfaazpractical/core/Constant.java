package com.android.alfaazpractical.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constant {

    public static final String TMDB_API = "2453aa4c5860e55458e1e5f7865bd71b";
    public static final String API_ACCES_TOKEN = "2453aa4c5860e55458e1e5f7865bd71b";
    public static final String BACKDROP_PATH = "https://image.tmdb.org/t/p/w185";

    public static final int FRAGMENT_JUST_REPLACE = 0;
    public static final int FRAGMENT_JUST_ADD = 1;
    public static final int FRAGMENT_ADD_TO_BACKSTACK_AND_REPLACE = 2;
    public static final int FRAGMENT_ADD_TO_BACKSTACK_AND_ADD = 3;

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static int getImageId(Context context, String code) {
        if (code != null)
            code = code.toLowerCase();
        String prefix = "international_country_" + code;
        return context.getResources().getIdentifier("drawable/" + prefix, null, context.getPackageName());
    }
}
