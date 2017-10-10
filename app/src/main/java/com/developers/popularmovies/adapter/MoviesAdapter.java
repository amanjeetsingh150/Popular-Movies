package com.developers.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developers.popularmovies.util.AnimationCallBack;
import com.developers.popularmovies.util.Constants;
import com.developers.popularmovies.Movie;
import com.developers.popularmovies.R;
import com.developers.popularmovies.activities.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amanjeet Singh on 10/10/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private AnimationCallBack animationCallBack;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public MoviesAdapter() {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationCallBack.launchDetailsActivity(movieList.get(position),
                        holder.movieTextView,
                        holder.moviePoster);
            }
        });
        holder.movieTextView.setText(movieList.get(position).getTitle());
        Picasso.with(context).load(movieList.get(position).getPoster()).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setAnimationCallBack(AnimationCallBack animationCallBack) {
        this.animationCallBack = animationCallBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.grid_background)
        LinearLayout linearLayout;
        @BindView(R.id.movie_image_view)
        ImageView moviePoster;
        @BindView(R.id.movie_text_view)
        TextView movieTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
