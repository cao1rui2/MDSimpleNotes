package com.example.caorui.mdsimplenotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caorui on 2015/7/21.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_NOTE = "create table Note (" + "id integer primary key autoincrement, " + "note_first text, " + "note_last text, " + "note_text text)";

    public NoteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
