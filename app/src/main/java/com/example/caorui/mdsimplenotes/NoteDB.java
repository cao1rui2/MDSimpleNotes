package com.example.caorui.mdsimplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caorui on 2015/7/21.
 */
public class NoteDB {
    
    public static final String DB_NAME = "simple_notes";
    public static final int VERSION = 1;
    private static NoteDB noteDB;
    private SQLiteDatabase db;

    private NoteDB(Context context) {
        NoteOpenHelper dbHelp = new NoteOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelp.getWritableDatabase();
    }

    public synchronized static NoteDB getInstance(Context context) {
        if (noteDB == null) {
            noteDB = new NoteDB(context);
        }
        return noteDB;
    }

    public void saveNote(Note note) {
        if (!TextUtils.isEmpty(note.getText())) {
            ContentValues values = new ContentValues();
            values.put("note_first", note.getFirstTime());
            values.put("note_last", note.getLastTime());
            values.put("note_text", note.getText());
            db.insert("Note", null, values);
        }
    }

    public List<Note> loadNotes() {
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = db.query("Note", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setFirstTime(cursor.getString(cursor.getColumnIndex("note_first")));
                note.setLastTime(cursor.getString(cursor.getColumnIndex("note_last")));
                note.setText(cursor.getString(cursor.getColumnIndex("note_text")));
                list.add(note);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteNote(int id) {
        db.delete("Note", "id = ?", new String[] { Integer.toString(id) });
    }

    public void updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put("note_last", note.getLastTime());
        values.put("note_text", note.getText());
        db.update("Note", values, "id = ?", new String[]{Integer.toString(note.getId())});
    }

    public Note loadNote(int id) {
        Note note = new Note();
        Cursor cursor = db.query("Note", null, "id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setFirstTime(cursor.getString(cursor.getColumnIndex("note_first")));
                note.setLastTime(cursor.getString(cursor.getColumnIndex("note_last")));
                note.setText(cursor.getString(cursor.getColumnIndex("note_text")));
                if (note.getId() == id) {
                    return note;
                }
            } while (cursor.moveToNext());
        }
        return note;
    }
}
