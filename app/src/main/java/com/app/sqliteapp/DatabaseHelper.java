package com.app.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student" +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surename TEXT, marks INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public boolean insertData(String name, String surename, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surename", surename);
        contentValues.put("marks", marks);

        long result = db.insert("student", null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM student", null);
    }
}
