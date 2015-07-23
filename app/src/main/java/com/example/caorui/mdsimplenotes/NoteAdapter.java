package com.example.caorui.mdsimplenotes;


import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caorui on 2015/7/22.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<Note> noteList;
    private NoteDB noteDB;

    public NoteAdapter(Context context, List<Note> noteList1, NoteDB noteDB1) {
        this.context = context;
        noteList = noteList1;
        noteDB = noteDB1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getPosition();
                Toast.makeText(v.getContext(), "点击" + position, Toast.LENGTH_SHORT).show();
                int id = noteList.get(position).getId();
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("from_rec", true);
                intent.putExtra("note_id", id);
                context.startActivity(intent);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = vh.getPosition();
                Toast.makeText(v.getContext(), "点击" + position, Toast.LENGTH_SHORT).show();
                showDialog(position);
                return true;
            }
        });
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.noteFirst.setText("创建时间:" + noteList.get(position).getFirstTime());
        holder.noteLast.setText("更新时间:" + noteList.get(position).getLastTime());
        holder.noteText.setText(noteList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    private void showDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("选择操作");

        String[] dialogItems = new String[]{
                context.getString(R.string.delete_one_item),
                context.getString(R.string.add_one_item),
                context.getString(R.string.move_one_item),
                context.getString(R.string.change_one_item),
                context.getString(R.string.add_many_items),
        };
        builder.setItems(dialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //delete this item
                        noteDB.deleteNote(noteList.get(position).getId());
                        noteList.remove(position);
                        notifyItemRemoved(position);
                        break;
                    case 1:
                        //add one item
                        noteList.add(position, new Note());
                        notifyItemInserted(position);
                        break;
                    case 2:
                        //TODO remember to change the data set...
                        //move one item to another position
                        notifyItemMoved(position, position + 2);
                        //May cause IndexOutOfBoundsException. This is just a demo!
                        break;
                    case 3:
                        //change one item
                        noteList.get(position).setText("这是一个测试功能");
                        notifyItemChanged(position);
                        break;
                    case 4:
                        //add many items
                        List<Note> insertList = new ArrayList<Note>();
                        insertList.add(new Note());
                        insertList.add(new Note());

                        noteList.addAll(position, insertList);
                        notifyItemRangeInserted(position, insertList.size());
                        break;
                    default:
                        break;
                }
            }
        });

        builder.create().show();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteFirst, noteLast, noteText;

        public ViewHolder(View v) {
            super(v);

            noteFirst = (TextView) v.findViewById(R.id.note_firsttime);
            noteLast = (TextView) v.findViewById(R.id.note_lasttime);
            noteText = (TextView) v.findViewById(R.id.note_text);
        }
    }


}
