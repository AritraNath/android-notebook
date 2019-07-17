/*
 * Copyright (c) 2019 Aritra Nath. All rights reserved.
 */

package com.aritra.demoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aritra.demoapp.model.Note;
import com.aritra.demoapp.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SimpleDateFormat date = new SimpleDateFormat("EEEE, dd MMMM YYYY", Locale.UK);
    private SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.UK);

    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_NOTE_SUBJECT + " TEXT, "
                + Constants.KEY_NOTE_CONTENT + " TEXT, "
                + Constants.KEY_DATE_ADDED + " TEXT, "
                + Constants.KEY_TIME_ADDED + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /* CRUD Operations */
    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.KEY_NOTE_SUBJECT, note.getSubject());
        contentValues.put(Constants.KEY_NOTE_CONTENT, note.getContent());
        contentValues.put(Constants.KEY_DATE_ADDED, date.format(new Date()));
        contentValues.put(Constants.KEY_TIME_ADDED, time.format(new Date()));

        db.insert(Constants.TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_NOTE_SUBJECT,
                        Constants.KEY_NOTE_SUBJECT, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED},
                null, null, null, null,
                Constants.KEY_DATE_ADDED + " DESC");

        List<Note> noteList = new ArrayList<>();

        if (cursor.moveToNext()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID)));
                String subject = cursor.getString(cursor.getColumnIndex(Constants.KEY_NOTE_SUBJECT));
                String content = cursor.getString(cursor.getColumnIndex(Constants.KEY_NOTE_CONTENT));
                String date = cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED));
                String time = cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED));

                Note note = new Note(subject, content, date, time, id);
                noteList.add(note);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return noteList;
    }

    public Note getNoteByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_NOTE_SUBJECT,
                        Constants.KEY_NOTE_SUBJECT, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED},
                Constants.KEY_ID + " =?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        String subject = cursor.getString(cursor.getColumnIndex(Constants.KEY_NOTE_SUBJECT));
        String content = cursor.getString(cursor.getColumnIndex(Constants.KEY_NOTE_CONTENT));
        String date = cursor.getString(cursor.getColumnIndex(Constants.KEY_DATE_ADDED));
        String time = cursor.getString(cursor.getColumnIndex(Constants.KEY_TIME_ADDED));

        Note note = new Note(subject, content, date, time, id);
        cursor.close();
        db.close();
        return note;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.KEY_NOTE_SUBJECT, note.getSubject());
        contentValues.put(Constants.KEY_NOTE_CONTENT, note.getContent());
        contentValues.put(Constants.KEY_DATE_ADDED, date.format(new Date()));
        contentValues.put(Constants.KEY_TIME_ADDED, date.format(new Date()));

        db.update(Constants.TABLE_NAME, contentValues,
                Constants.KEY_ID + " =?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " =?", new
                String[]{String.valueOf(id)});
        db.close();
    }

    public int getNotesCount() {
        int count;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_NOTE_SUBJECT,
                        Constants.KEY_NOTE_SUBJECT, Constants.KEY_DATE_ADDED, Constants.KEY_TIME_ADDED},
                null, null, null, null, null);
        cursor.moveToFirst();
        count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
