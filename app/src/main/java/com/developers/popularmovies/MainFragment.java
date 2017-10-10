package com.developers.popularmovies;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.developers.popularmovies.activities.SettingActivity;
import com.developers.popularmovies.adapter.MoviesAdapter;
import com.developers.popularmovies.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private static final String TAG = MainFragment.class.getSimpleName();
    @BindView(R.id.movie_grid)
    RecyclerView movieRecyclerView;
    private ProgressDialog progress;
    private List<Movie> mList;
    private SharedPreferences sharedPreferences;
    private MoviesAdapter moviesAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by:
                startActivity(new Intent(getActivity(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart");
        String order = sharedPreferences.getString(getString(R.string.sort_key), "0");
        Log.d(TAG, order);
        new FetchPopularMovie().execute();
    }

    public class FetchPopularMovie extends AsyncTask<Void, Void, Integer> {
        private final String TAG = FetchPopularMovie.class.getSimpleName();
        private String order;
        private Uri uri;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Loading....");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int res = 0;
            order = sharedPreferences.getString(getString(R.string.sort_key), "0");
            if (order.equals("0")) {
                uri = Uri.parse(Constants.BASE_URL).buildUpon()
                        .appendPath(getString(R.string.popular))
                        .appendQueryParameter(getString(R.string.api_key_attr), BuildConfig.MOVIE_KEY)
                        .build();
                Log.d(TAG, uri + "");
            } else {
                uri = Uri.parse(Constants.BASE_URL).buildUpon()
                        .appendPath(getString(R.string.top_rated_attr))
                        .appendQueryParameter(getString(R.string.api_key_attr), BuildConfig.MOVIE_KEY)
                        .build();
                Log.d(TAG, uri + "");
            }
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            try {
                String response = "";
                URL url1 = new URL(uri.toString());
                connection = (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    Log.e(TAG, "input stream is null");
                }
                StringBuffer buffer = new StringBuffer();
                String line = "";
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                response = buffer.toString();
                if (response.length() > 0) {
                    parseDetails(response);
                    res = 1;
                }


            } catch (Exception e) {
                Log.e(TAG, " " + e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "" + e);
                    }
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(Integer o) {
            if (o == 1) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                movieRecyclerView.setLayoutManager(gridLayoutManager);
                moviesAdapter = new MoviesAdapter(getActivity(), mList);
                movieRecyclerView.setAdapter(moviesAdapter);
                progress.dismiss();
            }
        }

        public void parseDetails(String response) {
            Uri imageUri, posterUri;
            try {
                Log.d(TAG, response);
                JSONObject result = new JSONObject(response);
                JSONArray arr = result.getJSONArray("results");
                String poster, title, overview, release, rating, bannerimg;
                mList = new ArrayList<>(arr.length());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject movie = arr.getJSONObject(i);
                    posterUri = Uri.parse(Constants.BASE_URL_IMAGES).buildUpon()
                            .appendEncodedPath(movie.getString("poster_path")).build();
                    poster = posterUri.toString();
                    title = movie.getString("original_title");
                    overview = movie.getString("overview");
                    release = movie.getString("release_date");
                    rating = movie.getString("vote_average");
                    imageUri = Uri.parse(Constants.BASE_URL_IMAGES).buildUpon()
                            .appendEncodedPath(movie.getString("backdrop_path")).build();
                    bannerimg = imageUri.toString();
                    Movie movie1 = new Movie(poster, title, overview, release, rating, bannerimg);
                    mList.add(movie1);
                }
            } catch (Exception e) {
                Log.e(TAG, " " + e);
            }
        }
    }
}
