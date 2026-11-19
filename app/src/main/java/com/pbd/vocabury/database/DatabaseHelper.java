package com.pbd.vocabury.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vocabury.db";
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_LANGUAGES = "languages";
    public static final String TABLE_WORDS = "words";

    public static final String COL_LANGUAGE_ID = "id";
    public static final String COL_LANGUAGE_CODE = "code";
    public static final String COL_LANGUAGE_NAME = "name";

    public static final String COL_WORD_ID = "id";
    public static final String COL_WORD_LANGUAGE_ID = "languageId";
    public static final String COL_WORD_SPANISH = "spanish";
    public static final String COL_WORD_FOREIGN = "foreignWord";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE_LANGUAGES + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "code TEXT," +
                        "name TEXT NOT NULL" +
                        ");");

        db.execSQL(
                "CREATE TABLE " + TABLE_WORDS + "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "languageId INTEGER," +
                        "spanish TEXT," +
                        "foreignWord TEXT," +
                        "FOREIGN KEY(languageId) REFERENCES " +
                        TABLE_LANGUAGES + "(id) ON DELETE CASCADE" +
                        ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);

        onCreate(db);

    }

}
