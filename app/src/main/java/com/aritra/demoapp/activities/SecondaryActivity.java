/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.aritra.demoapp.R;
import com.aritra.demoapp.helper.SnackBarGenerator;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SecondaryActivity extends AppCompatActivity {

    CoordinatorLayout secondaryLayout;
    NestedScrollView scrollView;
    SnackBarGenerator snackBarGenerator;
    ExtendedFloatingActionButton fabEditNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        secondaryLayout = findViewById(R.id.secondaryLayout);
        scrollView = findViewById(R.id.scrollView);
        snackBarGenerator = new SnackBarGenerator(secondaryLayout);
        Toolbar toolbar = findViewById(R.id.toolbar_secondary);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String note = sharedPreferences.getString("NOTE", "*** Nothing has been saved yet ***");
        String subject = sharedPreferences.getString("SUBJECT", "NA");
        String date = sharedPreferences.getString("DATE", "NA");
        String time = sharedPreferences.getString("TIME", "NA");
        if (toolbar != null) {
            getSupportActionBar().setTitle(subject);
            getSupportActionBar().setSubtitle(getString(R.string.saved_on_subtitle, date, time));
        }
        TextView noteText = findViewById(R.id.tvTextFromMainActivity);
        fabEditNote = findViewById(R.id.fabEditNote);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY < oldScrollY)
                fabEditNote.extend();
            if (scrollY > oldScrollY)
                fabEditNote.shrink();
        });
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

    public void editNote(View view) {
        Snackbar.make(secondaryLayout, "You selected an option to edit this note",
                BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
