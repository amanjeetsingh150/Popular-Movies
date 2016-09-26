package com.developers.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amanjeet Singh on 25-Sep-16.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Movie> movie;
    ImageView mImg;
    TextView title;
    public CustomAdapter(Context context, List<Movie> movie){
        super();
        this.context=context;
        this.movie=movie;
    }
    @Override
    public int getCount() {
        return movie.size();
    }

    @Override
    public Object getItem(int position) {
        return movie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.grid_list,null);
        mImg= (ImageView) convertView.findViewById(R.id.img);
        title= (TextView) convertView.findViewById(R.id.mtitle);
        Movie mMovie=movie.get(position);
        title.setText(mMovie.getTitle());
        Picasso.with(context).load(mMovie.getPoster()).into(mImg);
        return convertView;
    }
}
