package com.developers.popularmovies;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    private ProgressDialog progress;
    private List<Movie> mList;
    private GridView grid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        grid=(GridView)v.findViewById(R.id.gridView);
        setHasOptionsMenu(true);
        new FetchPopularMovie().execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Details.class);
                intent.putExtra("movie",mList.get(position));
                startActivity(intent);
            }
        });
        return v;
    }
  public class FetchPopularMovie extends AsyncTask<Void,Void,Integer>{
      private final String TAG = FetchPopularMovie.class.getSimpleName();

      @Override
      protected void onPreExecute() {
          progress=new ProgressDialog(getActivity());
          progress.setMessage("Loading....");
          progress.show();
      }
      @Override
      protected Integer doInBackground(Void... params) {
          int res=0;
          String url="http://api.themoviedb.org/3/movie/popular?api_key=*******************************";
          HttpURLConnection connection=null;
          BufferedReader bufferedReader=null;
          try{
              String response="";
              URL url1=new URL(url);
              connection= (HttpURLConnection) url1.openConnection();
              connection.setRequestMethod("GET");
              connection.connect();
              InputStream inputStream=connection.getInputStream();
              if(inputStream==null){
                  Log.e(TAG,"input stream is null");
              }
              StringBuffer buffer=new StringBuffer();
              String line="";
              bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
              while((line=bufferedReader.readLine())!=null){
                  buffer.append(line+"\n");
              }
              response=buffer.toString();
              if(response.length()>0){
                  parseDetails(response);
                  res=1;
              }


          }
          catch(Exception e){
              Log.e(TAG," "+e);
          }finally {
              if(connection != null){
                  connection.disconnect();
              }
              if(bufferedReader != null){
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
           if(o==1){
             CustomAdapter mAdapter=new CustomAdapter(getActivity(),mList);
             grid.setAdapter(mAdapter);
             progress.dismiss();
           }
      }

      public void parseDetails(String response){
          try{
              JSONObject result=new JSONObject(response);
              JSONArray arr=result.getJSONArray("results");
              String poster,title,overview,release,rating,bannerimg;
              mList = new ArrayList<>(arr.length());
              for(int i=0;i<arr.length();i++){
                  JSONObject movie=arr.getJSONObject(i);
                  poster="http://image.tmdb.org/t/p/w185"+movie.getString("poster_path");
                  title=movie.getString("original_title");
                  overview=movie.getString("overview");
                  release=movie.getString("release_date");
                  rating=movie.getString("vote_average");
                  bannerimg="http://image.tmdb.org/t/p/w185"+movie.getString("backdrop_path");
                  Movie movie1=new Movie(poster,title,overview,release,rating,bannerimg);
                  mList.add(movie1);
              }
          }
          catch(Exception e){
              Log.e(TAG," "+e);
          }
      }
  }
    public class FetchTopMovie extends AsyncTask<Void,Void,Integer>{
        private final String TAG = FetchPopularMovie.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading....");
            progress.show();
        }
        @Override
        protected Integer doInBackground(Void... params) {
            int res=0;
            String url="http://api.themoviedb.org/3/movie/top_rated?api_key=*******************************";
            HttpURLConnection connection=null;
            BufferedReader bufferedReader=null;
            try{
                String response="";
                URL url1=new URL(url);
                connection= (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                if(inputStream==null){
                    Log.e(TAG,"input stream is null");
                }
                StringBuffer buffer=new StringBuffer();
                String line="";
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line+"\n");
                }
                response=buffer.toString();
                if(response.length()>0){
                    parseDetails(response);
                    res=1;
                }


            }
            catch(Exception e){
                Log.e(TAG," "+e);
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
                if(bufferedReader != null){
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
            if(o==1){
                CustomAdapter mAdapter=new CustomAdapter(getActivity(),mList);
                grid.setAdapter(mAdapter);
                progress.dismiss();
            }
        }

        public void parseDetails(String response){
            try{
                JSONObject result=new JSONObject(response);
                JSONArray arr=result.getJSONArray("results");
                String poster,title,overview,release,rating,bannerimg;
                mList = new ArrayList<>(arr.length());
                for(int i=0;i<arr.length();i++){
                    JSONObject movie=arr.getJSONObject(i);
                    poster="http://image.tmdb.org/t/p/w185"+movie.getString("poster_path");
                    title=movie.getString("original_title");
                    overview=movie.getString("overview");
                    release=movie.getString("release_date");
                    rating=movie.getString("vote_average");
                    bannerimg="http://image.tmdb.org/t/p/w185"+movie.getString("backdrop_path");
                    Movie movie1=new Movie(poster,title,overview,release,rating,bannerimg);
                    mList.add(movie1);
                }
            }
            catch(Exception e){
                Log.e(TAG," "+e);
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
              new FetchPopularMovie().execute();
        }
        if(id==R.id.action_settings1){
            new FetchTopMovie().execute();
        }
        return super.onOptionsItemSelected(item);
    }
}
