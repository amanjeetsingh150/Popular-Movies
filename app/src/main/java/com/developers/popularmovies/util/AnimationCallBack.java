package com.developers.popularmovies.util;

import android.widget.ImageView;
import android.widget.TextView;

import com.developers.popularmovies.Movie;

/**
 * Created by Amanjeet Singh on 11/10/17.
 */

public interface AnimationCallBack {
    void launchDetailsActivity(Movie movie, TextView movieTextView, ImageView movieImageView);
}
