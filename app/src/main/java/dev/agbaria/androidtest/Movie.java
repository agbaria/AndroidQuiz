package dev.agbaria.androidtest;

import java.io.Serializable;

/**
 * Created by agbaria on 21/02/2017.
 *
 */

public class Movie implements Serializable {

    private String poster_path;
    private String backdrop_path;
    private String title;
    private String vote_average;
    private String overview;
    private int[] genre_ids;

    public Movie(String poster_path, String backdrop_path, String title, String vote_average, String overview, int[] genre_ids) {
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.genre_ids = genre_ids;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
