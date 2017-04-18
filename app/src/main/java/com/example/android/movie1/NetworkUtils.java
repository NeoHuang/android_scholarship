package com.example.android.movie1;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by zhiyihuang on 18.04.17.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POP_PATH = "popular";
    private static final String TOP_RATED_PATH = "top_rated";
    private static final String API_KEY = "api_key";

    public static URL buildPopularMovieUrl() {
        return buildUrlWithKey(MOVIE_URL + POP_PATH);
    }

    public static URL buildTopRateUrl() {
        return buildUrlWithKey(MOVIE_URL + TOP_RATED_PATH);
    }

    private static URL buildUrlWithKey(String urlString) {
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DB_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
