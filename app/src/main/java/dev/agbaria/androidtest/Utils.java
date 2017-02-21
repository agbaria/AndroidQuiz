package dev.agbaria.androidtest;

import java.util.HashMap;

/**
 * Created by ANDROID on 21/02/2017.
 */

public class Utils {

    private static HashMap<Integer, String> genres;

    public static void initGenres() {
        genres = new HashMap<>();
        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(36, "History");
        genres.put(27, "Horror");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV Movie");
        genres.put(53, "Thriller");
        genres.put(10752, "War");
        genres.put(37, "Western");
    }

    public static String getGenres(int[] genre_ids) {
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < genre_ids.length; i++)
            str.append(genres.get(genre_ids[i]) + " ");
        return str.toString();
    }
}
