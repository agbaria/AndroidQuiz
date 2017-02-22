package dev.agbaria.androidtest;


import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        new HttpTask(getActivity(), recyclerView).execute(getString(R.string.api_link));
        return v;
    }


    public class HttpTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        Activity activity;
        RecyclerView recyclerView;

        HttpTask(Activity activity, RecyclerView recyclerView) {
            this.activity = activity;
            this.recyclerView = recyclerView;
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            ArrayList<Movie> movies = new ArrayList<>();
            Gson gson = new Gson();
            try {
                String str_json = HttpManager.downloadData(strings[0]);
                JSONObject json = new JSONObject(str_json);
                JSONArray children = json.getJSONArray("results");
                for (int i = 0; i < children.length(); i++) {
                    JSONObject data = children.getJSONObject(i);
                    Movie movie = gson.fromJson(data.toString(), Movie.class);
                    movies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            if(movies.size() > 0) {
                MovieAdapter adapter = new MovieAdapter(movies, activity);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            }
        }
    }
}
