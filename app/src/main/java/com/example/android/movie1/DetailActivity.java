package com.example.android.movie1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView mPoster;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRate;
    TextView mOverview;
    private static String TMD_IMG_BASE = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_detail);
        setContentView(R.layout.activity_detail);
        mPoster = (ImageView) findViewById(R.id.iv_poster);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mReleaseDate = (TextView) findViewById(R.id.tv_release);
        mRate = (TextView) findViewById(R.id.tv_rate);
        mOverview = (TextView) findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            MovieInfo movie = (MovieInfo) intent.getSerializableExtra("Movie");

            mTitle.setText(movie.getName());
            Picasso.with(this)
                    .load(TMD_IMG_BASE + movie.getPoster())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder_error)
                    .into(mPoster);
            mReleaseDate.setText(movie.getReleaseDate());
            mRate.setText(movie.getVoteAvg() + "/10");
            mOverview.setText(movie.getOverview());

        }

    }
}
