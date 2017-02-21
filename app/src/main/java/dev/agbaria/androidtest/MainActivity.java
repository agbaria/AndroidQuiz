package dev.agbaria.androidtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getSharedPreferences("Notes", Context.MODE_PRIVATE);
        Utils.initGenres();
        welcome();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        new HttpTask(this, recyclerView).execute(getString(R.string.api_link));
    }

    private void saveData() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Name", name);
        editor.commit();
    }

    private void welcome() {
        name = pref.getString("Name", "");

        if(name.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Insert your name");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = input.getText().toString();
                    saveData();
                }
            });

            builder.show();
        }

        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class HttpTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        Activity activity;
        RecyclerView recyclerView;

        public HttpTask(Activity activity, RecyclerView recyclerView) {
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
