/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aritra.demoapp.R;
import com.aritra.demoapp.helper.SnackBarGenerator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DateFormat date, time;
    Date today;
    ConstraintLayout mainLayout;
    SnackBarGenerator snackBarGenerator;
    TextInputEditText etSubject, etNote;
    TextInputLayout subLayout, noteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String note = sharedPreferences.getString("NOTE", "");
        String subject = sharedPreferences.getString("SUBJECT", "");

        mainLayout = findViewById(R.id.mainLayout);
        noteLayout = findViewById(R.id.etNoteLayout);
        etNote = findViewById(R.id.etNote);
        etNote.setText(note);
        subLayout = findViewById(R.id.etSubjectLayout);
        etSubject = findViewById(R.id.etSubject);
        etSubject.setText(subject);
        etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (subLayout.getError() != null) {
                    subLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (noteLayout.getError() != null) {
                    noteLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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

    public void saveNote(View view) {
        String note = etNote.getText().toString();
        String subject = etSubject.getText().toString();
        date = DateFormat.getDateInstance(DateFormat.SHORT);
        time = DateFormat.getTimeInstance(DateFormat.SHORT);
        today = new Date();
        if (note.isEmpty()) {
            noteLayout.setError("Cannot be empty");
        }
        if (subject.isEmpty()) {
            subLayout.setError("Cannot be empty");
        }
        if (!(note.isEmpty() && subject.isEmpty())) {
            SharedPreferences.Editor shared_prefs = sharedPreferences.edit();
            shared_prefs.putString("NOTE", note);
            shared_prefs.putString("SUBJECT", subject);
            shared_prefs.putString("DATE", date.format(today));
            shared_prefs.putString("TIME", time.format(today));
            shared_prefs.apply();
            Snackbar.make(mainLayout, "Your note was saved", Snackbar.LENGTH_LONG)
                    .setAction("Show", snackView -> showFullNote(view)).show();
        }
    }

    public void showFullNote(View view) {
        startActivity(new Intent(MainActivity.this, SecondaryActivity.class));
    }

    public void showDateAndTime(View view) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG);
        today = new Date();
        snackBarGenerator.generate(dateFormat.format(today), 2);
    }
}

