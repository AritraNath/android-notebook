/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.aritra.demoapp.R;
import com.aritra.demoapp.SnackBarGenerator;

import java.util.Objects;

public class SecondaryActivity extends AppCompatActivity {

    CoordinatorLayout secondaryLayout;
    SnackBarGenerator snackBarGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        secondaryLayout = findViewById(R.id.secondaryLayout);
        snackBarGenerator = new SnackBarGenerator(secondaryLayout);
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String note = sharedPreferences.getString("NOTE", "*** Nothing has been saved yet ***");
        String date = sharedPreferences.getString("DATE", "NA");
        String time = sharedPreferences.getString("TIME", "NA");

        TextView noteText = findViewById(R.id.tvTextFromMainActivity);
        TextView dateFooter = findViewById(R.id.dateFooter);
        dateFooter.setText(getString(R.string.saved_on_actual_date, date, time));
        noteText.setText(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() != null)
            snackBarGenerator.generate(item.getTitle() + " was clicked", 2);
        return super.onOptionsItemSelected(item);
    }
}
