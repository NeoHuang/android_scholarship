package com.example.android.movie1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by zhiyihuang on 17/04/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    protected MovieInfo[] mMovies;

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_thumbnail, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        if (mMovies == null || position >= mMovies.length) {
            return;
        }
        Picasso.with(holder.mThumbnail.getContext())
                .load(mMovies[position].getPoster())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.length;
    }

    public void setMovies(MovieInfo[] movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        public final ImageView mThumbnail;

        public MovieHolder(View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
        }
    }

}
