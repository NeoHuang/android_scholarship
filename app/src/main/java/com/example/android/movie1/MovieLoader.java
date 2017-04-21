package com.example.android.movie1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by zhiyihuang on 18.04.17.
 */

public class MovieLoader {
    private static String TMD_RESULTS="results";
    private static String TMD_TITLE="title";
    private static String TMD_OVERVIEW="overview";
    private static String TMD_VOTE_AVG="vote_average";
    private static String TMD_RELEASE_DATE="release_date";
    private static String TMD_POSTER_PATH="poster_path";

    public static MovieInfo[] loadPopularMovies() {
        URL popUrl = NetworkUtils.buildPopularMovieUrl();
        return loadMovies(popUrl);
    }

    public static MovieInfo[] loadTopRatedMovies() {
        URL topRatedUrl = NetworkUtils.buildTopRateUrl();
        return loadMovies(topRatedUrl);
    }

    private static MovieInfo[] loadMovies(URL movieUrl) {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(movieUrl);
            JSONObject moviesJson = new JSONObject(response);

            if (!moviesJson.has(TMD_RESULTS)) {
                return null;
            }

            JSONArray movieArray = moviesJson.getJSONArray(TMD_RESULTS);
            MovieInfo[] movieInfos = new MovieInfo[movieArray.length()];
            for (int i= 0; i < movieArray.length(); i++){
                JSONObject movieJson = movieArray.getJSONObject(i);

                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setName(movieJson.getString(TMD_TITLE));
                movieInfo.setPoster(movieJson.getString(TMD_POSTER_PATH));
                movieInfo.setOverview(movieJson.getString(TMD_OVERVIEW));
                movieInfo.setVoteAvg(movieJson.getDouble(TMD_VOTE_AVG));
                movieInfo.setReleaseDate(movieJson.getString(TMD_RELEASE_DATE));
                movieInfos[i] = movieInfo;
            }
            return movieInfos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
