package dev.agbaria.androidtest;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static dev.agbaria.androidtest.Utils.getGenres;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final String MOVIE = "movie";
    private Movie movie;

    public static MovieFragment getInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MOVIE, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        movie = (Movie) arguments.getSerializable(MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        ImageView imageView = (ImageView) v.findViewById(R.id.ivImage);
        String url = "http://image.tmdb.org/t/p/w185/" + movie.getPoster_path() + "?api_key=b3b1492d3e91e9f9403a2989f3031b0c";
        Picasso.with(getActivity()).load(url).resize(400, 600).into(imageView);

        ((TextView) v.findViewById(R.id.tvName)).setText(movie.getTitle());
        ((TextView) v.findViewById(R.id.tvRating)).setText(movie.getVote_average());
        ((TextView) v.findViewById(R.id.tvGenres)).setText(getGenres(movie.getGenre_ids()));
        ((TextView) v.findViewById(R.id.tvOverview)).setText(movie.getOverview());
        return v;
    }
}
