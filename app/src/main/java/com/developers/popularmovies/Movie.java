package com.developers.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amanjeet Singh on 25-Sep-16.
 */
public class Movie implements Parcelable {
    String poster;
    String title;
    String overview;
    String release;
    String rating;
    String bannerimg;
    public Movie(String poster,String title,String overview,String release,String rating,String bannerimg){
     this.poster=poster;
     this.title=title;
     this.overview=overview;
     this.release=release;
     this.rating=rating;
     this.bannerimg=bannerimg;
    }

    protected Movie(Parcel in) {
        poster = in.readString();
        title = in.readString();
        overview = in.readString();
        release = in.readString();
        rating = in.readString();
        bannerimg=in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release);
        dest.writeString(rating);
        dest.writeString(bannerimg);
    }
    public String getTitle(){
        return title;
    }
    public String getPoster(){
        return poster;
    }
    public String getOverview(){
        return overview;
    }
    public String getRelease(){
        return release;
    }
    public String getRating(){
        return rating;
    }
    public String getBannerimg(){
        return bannerimg;
    }
}
