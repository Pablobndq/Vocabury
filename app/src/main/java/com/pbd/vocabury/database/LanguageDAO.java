package com.pbd.vocabury.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pbd.vocabury.model.Language;

import java.util.ArrayList;

public class LanguageDAO extends BaseDAO {

    public LanguageDAO(Context context) {
        super(context);
    }

    public void addLanguage(Language language){

        openWritable();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COL_LANGUAGE_CODE,
                language.getCode());

        values.put(DatabaseHelper.COL_LANGUAGE_NAME,
                language.getName());

        db.insert(DatabaseHelper.TABLE_LANGUAGES,null,values);

        close();

    }

    public ArrayList<Language> getLanguages(){

        openReadable();

        ArrayList<Language> list = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_LANGUAGES,
                null,
                null,
                null,
                null,
                null,
                "name ASC");

        while(cursor.moveToNext()){

            list.add(cursorToLanguage(cursor));

        }

        cursor.close();

        close();

        return list;

    }

    public Language getLanguageById(int id){

        openReadable();

        Language language = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_LANGUAGES,
                null,
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor.moveToFirst()){

            language = cursorToLanguage(cursor);

        }

        cursor.close();

        close();

        return language;

    }

    private Language cursorToLanguage(Cursor cursor){

        Language language = new Language();

        language.setId(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LANGUAGE_ID)));

        language.setCode(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LANGUAGE_CODE)));

        language.setName(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LANGUAGE_NAME)));

        return language;

    }

    public void deleteLanguage(int id){

        openWritable();

        db.delete(
                DatabaseHelper.TABLE_LANGUAGES,
                DatabaseHelper.COL_LANGUAGE_ID + "=?",
                new String[]{
                        String.valueOf(id)
                });

        close();

    }

}
