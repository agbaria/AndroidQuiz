package dev.agbaria.androidtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

        ListFragment fragment = new ListFragment();
        getFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();
    }

    private void saveData() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Name", name);
        editor.apply();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Send mail..."));
                return true;
            case R.id.action_call:
                intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
                return true;
            case R.id.action_gps:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
