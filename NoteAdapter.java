package com.poonammishra.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Poonam Mishra on 08-06-2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    public static class ViewHolder{ TextView title;
    TextView body;
    ImageView noteIcon;}

    public NoteAdapter(Context context, ArrayList<Note> notes)
    {super(context, 0, notes);}

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {   Note note=getItem(pos);
        ViewHolder viewHolder;
        //this is the case when a note doesn't exist and we need t create a new one
        if(convertView==null)
        {   viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_row,parent,false);

            viewHolder.title=(TextView)convertView.findViewById(R.id.listNoteItemTitle);
            viewHolder.body=(TextView)convertView.findViewById(R.id.listNoteItemBody);
            viewHolder.noteIcon=(ImageView)convertView.findViewById(R.id.listItemNoteImg);
            convertView.setTag(viewHolder);

        }
        //it exists. find it and tag it
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        /*TextView noteTitle=(TextView)convertView.findViewById(R.id.listNoteItemTitle);
        TextView noteText=(TextView)convertView.findViewById(R.id.listNoteItemBody);
        ImageView noteImage=(ImageView)convertView.findViewById(R.id.listItemNoteImg);*/

        viewHolder.title.setText(note.getTitle());
        viewHolder.body.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());

        return convertView;

    }
}
