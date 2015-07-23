package com.example.caorui.mdsimplenotes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private int mBackTime = 0;
    private Toast mToast;
    private RecyclerView recyclerView;
    private NoteDB noteDB;
    private List<String> dataList = new ArrayList<String>();
    private List<Note> noteList;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("简单笔记");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "action_settings", 0).show();
                        //noteList.clear();
                        //noteList = noteDB.loadNotes();
                        //noteAdapter = new NoteAdapter(MainActivity.this, noteList, noteDB);
                        //recyclerView.setAdapter(noteAdapter);
                        //noteAdapter.notifyDataSetChanged();
                        //noteAdapter.notifyItemRangeChanged(0, noteAdapter.getItemCount());
                        break;
                    case R.id.action_about:
                        Toast.makeText(MainActivity.this, "action_about", 0).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.nav);
        setupDrawerContent(mNavigationView);

        mFab = (FloatingActionButton) findViewById(R.id.myfab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "fab", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("from_fab", true);
                startActivity(intent);
            }
        });

        noteDB = NoteDB.getInstance(this);
        noteList = noteDB.loadNotes();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this, noteList, noteDB);
        recyclerView.setAdapter(noteAdapter);


        //recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        /*
        listView = (ListView) findViewById(R.id.list_view);
        noteDB = NoteDB.getInstance(this);
        noteList = noteDB.loadNotes();
        for(Note note : noteList) {
            dataList.add(note.getText());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        */

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
    @Override
    public void onBackPressed() {

        if (mBackTime == 0) {
            mToast = Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT);
            mToast.show();
            mBackTime = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackTime = 0;
                    }
                }
            }.start();
            return;
        } else {
            mToast.cancel();
            this.finish();
        }
        //super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //noteList.clear();

        noteList = noteDB.loadNotes();
        //Log.e("MainActivity1", noteList.get(0).getText());

        noteAdapter = new NoteAdapter(this, noteList, noteDB);
        //Log.d("问题", "new adapter");
        recyclerView.setAdapter(noteAdapter);
        //noteAdapter.notifyDataSetChanged();
        //Log.e("MainActivity2", noteList.get(0).getText());
        //noteAdapter.notifyItemRangeChanged(0, noteAdapter.getItemCount());
        /**
         *这里只要用了noteList.clean()，recyclerview就必然为空，即使后面重新为noteList赋值也没用，
         * 但不调用.clean()，只是重新为noteList赋值，notifyDataSetChanged()却没有作用，
         * 即notifyDataSetChanged()只对.clean()方法起作用，目前不知道是什么原因。再看看吧。
         */
    }



}
