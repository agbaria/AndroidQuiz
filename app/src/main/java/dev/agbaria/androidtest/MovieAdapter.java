package dev.agbaria.androidtest;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static dev.agbaria.androidtest.Utils.getGenres;

/**
 * Created by ANDROID on 21/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies, Activity activity) {
        this.inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        String url = "http://image.tmdb.org/t/p/w185/" + movie.getBackdrop_path() + "?api_key=b3b1492d3e91e9f9403a2989f3031b0c";
        Picasso.with(activity).load(url).resize(250, 250).into(holder.image);
        holder.title.setText(movie.getTitle());
        holder.rating.setText(movie.getVote_average());
        holder.genres.setText(getGenres(movie.getGenre_ids()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieFragment fragment = MovieFragment.getInstance(movie);
                activity.getFragmentManager().beginTransaction().replace(R.id.content_main, fragment)
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView rating;
        TextView genres;
        RelativeLayout layout;

        public ViewHolder(View v) {
            super(v);

            image = (ImageView) v.findViewById(R.id.ivImage);
            title = (TextView) v.findViewById(R.id.tvName);
            rating = (TextView) v.findViewById(R.id.tvRating);
            genres = (TextView) v.findViewById(R.id.tvGenres);
            layout = (RelativeLayout) v.findViewById(R.id.movieItem);
        }
    }
}
