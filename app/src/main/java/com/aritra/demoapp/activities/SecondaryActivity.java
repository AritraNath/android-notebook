/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.aritra.demoapp.R;
import com.aritra.demoapp.helper.SnackBarGenerator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
        String subject = sharedPreferences.getString("SUBJECT", "NA");
        String date = sharedPreferences.getString("DATE", "NA");
        String time = sharedPreferences.getString("TIME", "NA");
        if (toolbar != null) {
            getSupportActionBar().setTitle(subject);
            getSupportActionBar().setSubtitle(getString(R.string.saved_on_subtitle, date, time));
        }
        TextView noteText = findViewById(R.id.tvTextFromMainActivity);
        TextView dateFooter = findViewById(R.id.dateFooter);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        try {
            Date date1 = dateFormat.parse(date);
            Date time1 = timeFormat.parse(time);

            SimpleDateFormat longDateFormat = new SimpleDateFormat("EEEE, dd MMMM YYYY", Locale.UK);
            assert date1 != null;
            String longDate = longDateFormat.format(date1);
            SimpleDateFormat longTimeFormat = new SimpleDateFormat("hh:mm a", Locale.UK);
            assert time1 != null;
            String longTime = longTimeFormat.format(time1);
            dateFooter.setText(getString(R.string.date_footer, longDate, longTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
