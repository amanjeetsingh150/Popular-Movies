package com.developers.popularmovies.activities;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.popularmovies.Movie;
import com.developers.popularmovies.R;
import com.developers.popularmovies.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_image_view)
    ImageView banner;
    @BindView(R.id.detail_poster_image_view)
    ImageView poster;
    @BindView(R.id.movie_title_text_view)
    TextView title;
    @BindView(R.id.release_text_view)
    TextView release;
    @BindView(R.id.rating_text_view)
    TextView rate;
    @BindView(R.id.overview_text_view)
    TextView summary;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getParcelableExtra(Constants.KEY_MOVIES);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(this).load(movie.getBannerimg()).into(banner);
        Picasso.with(this).load(movie.getPoster()).into(poster);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        release.setText(getString(R.string.release_text) + movie.getRelease());
        rate.setText(getString(R.string.rating) + movie.getRating());
        title.setText(movie.getTitle());
        summary.setText(movie.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
