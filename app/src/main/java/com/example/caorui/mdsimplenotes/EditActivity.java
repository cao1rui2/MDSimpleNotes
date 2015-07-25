package com.example.caorui.mdsimplenotes;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by caorui on 2015/7/21.
 */
public class EditActivity extends AppCompatActivity {
    private Toolbar eToolbar;
    private EditText edit;
    private Note note = new Note();
    private NoteDB noteDB;
    private boolean isfromfab;
    private String sharedText;
    private boolean isfromrec;
    private int noteId;
    private String action;
    private String type;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        noteDB = NoteDB.getInstance(this);
        edit = (EditText) findViewById(R.id.edit);

        isfromfab = getIntent().getBooleanExtra("from_fab", false);
        isfromrec = getIntent().getBooleanExtra("from_rec", false);
        noteId = getIntent().getIntExtra("note_id", 0);

        action = getIntent().getAction();
        type = getIntent().getType();
        if (isfromfab || (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type))) {

            edit.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String firsttime = formatter.format(curDate);
            note.setFirstTime(firsttime);
            noteDB.saveNote(note);//此时的note没有id
            List<Note> noteList = noteDB.loadNotes();
            note = noteList.get(noteList.size() - 1);//获取id，位之后的update做准备
        } else if (isfromrec) {
            note = noteDB.loadNote(noteId);
            edit.setText(note.getText());
            edit.setSelection(note.getText().length());
        }

        eToolbar = (Toolbar) findViewById(R.id.toolbar);
        eToolbar.setTitle("查看编辑");
        setSupportActionBar(eToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        eToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_copy:
                        //Toast.makeText(EditActivity.this, "action_copy", 0).show();
                        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        cmb.setText(edit.getText().toString());
                        Toast.makeText(EditActivity.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, edit.getText().toString());
                        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));
                        break;
                    case R.id.action_empty:
                        //Toast.makeText(EditActivity.this, "action_empty", 0).show();
                        edit.setText("");
                        break;
                    case R.id.action_size:
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                        builder.setTitle("设置字体大小");
                        String[] dialogItems = new String[] {"小", "中", "大"};
                        builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        edit.setTextSize(16);
                                        break;
                                    case 1:
                                        edit.setTextSize(20);
                                        break;
                                    case 2:
                                        edit.setTextSize(24);
                                        break;
                                }

                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.action_delete:
                        //Toast.makeText(EditActivity.this, "action_delete", 0).show();
                        edit.setText("");
                        EditActivity.this.finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        /*
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, edit.getText().toString());
        intent.setType("text/*");
        mShareActionProvider.setShareIntent(intent);
        */
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                EditActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {

        if (isfromfab || (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type))) {
            if (TextUtils.isEmpty(edit.getText().toString())) {
                noteDB.deleteNote(note.getId());
                this.finish();
            } else {
                note.setText(edit.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String lasttime = formatter.format(curDate);
                note.setLastTime(lasttime);
                noteDB.updateNote(note);
            }
        } else {
            if (TextUtils.isEmpty(edit.getText().toString())) {
                noteDB.deleteNote(noteId);
                this.finish();
            } else if (!edit.getText().toString().equals( note.getText())) {
                note.setText(edit.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String lasttime = formatter.format(curDate);
                note.setLastTime(lasttime);
                noteDB.updateNote(note);
                //List<Note> noteList = noteDB.loadNotes();
                //Log.e("EditActivity", noteList.get(0).getText());
            } else {

            }
        }
        super.onPause();
    }
}
