/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aritra.demoapp.R;
import com.aritra.demoapp.SnackBarGenerator;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SimpleDateFormat date, time;
    ConstraintLayout mainLayout;
    SnackBarGenerator snackBarGenerator;
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String note = sharedPreferences.getString("NOTE", "");

        mainLayout = findViewById(R.id.mainLayout);
        etNote = findViewById(R.id.etNote);
        etNote.setText(note);

        snackBarGenerator = new SnackBarGenerator(mainLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        snackBarGenerator.generate(item.getTitle() + " was clicked", 2);
        return super.onOptionsItemSelected(item);
    }

    public void saveAndChangeActivity(View view) {
        String note = etNote.getText().toString();
        date = new SimpleDateFormat("EEEE, dd MMMM YYYY", Locale.UK);
        time = new SimpleDateFormat("hh:mm a", Locale.UK);
        if (note.isEmpty())
            etNote.setError("Cannot be empty");
        else {
            SharedPreferences.Editor shared_prefs = sharedPreferences.edit();
            shared_prefs.putString("NOTE", note);
            shared_prefs.putString("DATE", date.format(new Date()));
            shared_prefs.putString("TIME", time.format(new Date()));
            shared_prefs.apply();
            startActivity(new Intent(MainActivity.this, SecondaryActivity.class));
        }
    }

    public void changeActivity(View view) {
        startActivity(new Intent(MainActivity.this, SecondaryActivity.class));
    }

    public void genToast(View view) {
        Toast.makeText(this, (new Date()).toString(), Toast.LENGTH_LONG).show();
    }

    public void showSnackBar(View view) {
        final Snackbar snackbar = Snackbar.make(mainLayout, "This is a snack bar", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        }).show();
    }
}

