package com.pbd.vocabury.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pbd.vocabury.model.Word;

import java.util.ArrayList;

public class WordDAO extends BaseDAO {

    public WordDAO(Context context) {
        super(context);
    }

    public void addWord(Word word){

        openWritable();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COL_WORD_LANGUAGE_ID,
                word.getLanguageId());

        values.put(DatabaseHelper.COL_WORD_SPANISH,
                word.getSpanish());

        values.put(DatabaseHelper.COL_WORD_FOREIGN,
                word.getForeign());

        db.insert(DatabaseHelper.TABLE_WORDS,
                null,
                values);

        close();

    }

    public void updateWord(Word word){

        openWritable();

        ContentValues values = new ContentValues();

        values.put(
                DatabaseHelper.COL_WORD_LANGUAGE_ID,
                word.getLanguageId());

        values.put(
                DatabaseHelper.COL_WORD_SPANISH,
                word.getSpanish());

        values.put(
                DatabaseHelper.COL_WORD_FOREIGN,
                word.getForeign());

        db.update(
                DatabaseHelper.TABLE_WORDS,
                values,
                DatabaseHelper.COL_WORD_ID + "=?",
                new String[]{String.valueOf(word.getId())});

        close();

    }

    public void deleteWord(int id){

        openWritable();

        db.delete(
                DatabaseHelper.TABLE_WORDS,
                DatabaseHelper.COL_WORD_ID + "=?",
                new String[]{String.valueOf(id)});

        close();

    }

    public Word getWordById(int id){

        openReadable();

        Word word = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORDS,
                null,
                DatabaseHelper.COL_WORD_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor.moveToFirst()){

            word = cursorToWord(cursor);

        }

        cursor.close();

        close();

        return word;

    }

    public ArrayList<Word> getWordsByLanguage(int languageId){

        openReadable();

        ArrayList<Word> list = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORDS,
                null,
                DatabaseHelper.COL_WORD_LANGUAGE_ID + "=?",
                new String[]{String.valueOf(languageId)},
                null,
                null,
                DatabaseHelper.COL_WORD_SPANISH + " ASC");

        while(cursor.moveToNext()){

            list.add(cursorToWord(cursor));

        }

        cursor.close();

        close();

        return list;

    }

    public ArrayList<Word> getWords(){

        openReadable();

        ArrayList<Word> list = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_WORDS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COL_WORD_SPANISH + " ASC");

        while(cursor.moveToNext()){

            list.add(cursorToWord(cursor));

        }

        cursor.close();

        close();

        return list;

    }

    private Word cursorToWord(Cursor cursor){

        Word word = new Word();

        word.setId(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_WORD_ID)));

        word.setLanguageId(
                cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_WORD_LANGUAGE_ID)));

        word.setSpanish(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_WORD_SPANISH)));

        word.setForeign(
                cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_WORD_FOREIGN)));

        return word;

    }

    public ArrayList<Word> getLastWordsByLanguage(
            int languageId,
            int limit){

        openReadable();

        ArrayList<Word> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM " + DatabaseHelper.TABLE_WORDS +
                        " WHERE " +
                        DatabaseHelper.COL_WORD_LANGUAGE_ID +
                        " = ?" +

                        " ORDER BY " +
                        DatabaseHelper.COL_WORD_ID +
                        " DESC LIMIT ?",

                new String[]{
                        String.valueOf(languageId),
                        String.valueOf(limit)
                });

        while(cursor.moveToNext()){

            list.add(cursorToWord(cursor));

        }

        cursor.close();

        close();

        return list;

    }

    public int getWordCountByLanguage(int languageId){

        openReadable();

        Cursor cursor = db.rawQuery(

                "SELECT COUNT(*) FROM " +
                        DatabaseHelper.TABLE_WORDS +
                        " WHERE " +
                        DatabaseHelper.COL_WORD_LANGUAGE_ID +
                        "=?",

                new String[]{
                        String.valueOf(languageId)
                });

        int count = 0;

        if(cursor.moveToFirst()){

            count = cursor.getInt(0);

        }

        cursor.close();

        close();

        return count;

    }

    public ArrayList<Word> getRandomWordsByLanguage(int languageId,
                                                    int limit){

        openReadable();

        ArrayList<Word> words = new ArrayList<>();

        Cursor cursor = db.rawQuery(

                "SELECT * FROM " +
                        DatabaseHelper.TABLE_WORDS +

                        " WHERE " +

                        DatabaseHelper.COL_WORD_LANGUAGE_ID +

                        "=? ORDER BY RANDOM() LIMIT ?",

                new String[]{
                        String.valueOf(languageId),
                        String.valueOf(limit)
                });

        while(cursor.moveToNext()){

            words.add(cursorToWord(cursor));

        }

        cursor.close();

        close();

        return words;

    }




}
