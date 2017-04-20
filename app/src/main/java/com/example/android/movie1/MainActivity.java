package com.example.android.movie1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    private static final String TAG = "Movie1";
    protected RecyclerView mRecyclerView;
    protected MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;

    @Override
    public void onClick(MovieInfo movie) {
        Class detailClass = DetailActivity.class;
        Intent detailIntent = new Intent(this, detailClass);
        detailIntent.putExtra("Movie", movie);
        startActivity(detailIntent);

    }

    enum Sort {
        Pop, TopRated
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieAdapter);
        Log.v(TAG, BuildConfig.MOVIE_DB_KEY);

        setTitle(R.string.menu_pop_title);
        new FetchMovieTask().execute(Sort.Pop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_pop:
                setTitle(R.string.menu_pop_title);
                new FetchMovieTask().execute(Sort.Pop);
                return true;
            case R.id.action_rate:
                setTitle(R.string.menu_rate_title);
                new FetchMovieTask().execute(Sort.TopRated);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<Sort, Void, MovieInfo[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieInfo[] doInBackground(Sort... params) {
            if (params.length == 0) {
                return null;
            }

            Sort sort = params[0];
            switch (sort) {
                case Pop:
                    return MovieLoader.loadPopularMovies();
                case TopRated:
                    return MovieLoader.loadTopRatedMovies();
                default:
                    return null;
            }

        }

        @Override
        protected void onPostExecute(MovieInfo[] movieInfos) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieInfos != null) {
                // COMPLETED (45) Instead of iterating through every string, use mForecastAdapter.setWeatherData and pass in the weather data
                mMovieAdapter.setMovies(movieInfos);
            } else {
                Toast.makeText(MainActivity.this, "Load movie Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
