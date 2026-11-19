package com.pbd.vocabury.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDAO {

    protected DatabaseHelper helper;
    protected SQLiteDatabase db;

    public BaseDAO(Context context) {

        helper = new DatabaseHelper(context);

    }

    protected void openWritable() {

        db = helper.getWritableDatabase();

    }

    protected void openReadable() {

        db = helper.getReadableDatabase();

    }

    protected void close() {

        if (db != null && db.isOpen()) {
            db.close();
        }

    }

}
