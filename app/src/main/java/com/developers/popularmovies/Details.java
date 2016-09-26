package com.developers.popularmovies;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    private ImageView banner,po;
    private TextView release,rate,title,summary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Movie movie=(Movie)getIntent().getParcelableExtra("movie");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie.getTitle());
        collapsingToolbar.setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        banner= (ImageView) findViewById(R.id.toolbar_image);
        po= (ImageView) findViewById(R.id.mposter);
        release=(TextView)findViewById(R.id.release);
        summary=(TextView)findViewById(R.id.overview);
        rate=(TextView)findViewById(R.id.rating);
        title=(TextView)findViewById(R.id.mtitle);
        Picasso.with(this).load(movie.getBannerimg()).into(banner);
        Picasso.with(this).load(movie.getPoster()).into(po);
        release.setText("Release: "+movie.getRelease());
        rate.setText("Rating: "+movie.getRating());
        title.setText(movie.getTitle());
        summary.setText(movie.getOverview());
    }
}
