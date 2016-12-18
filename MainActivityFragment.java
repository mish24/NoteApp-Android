package com.poonammishra.notebook;

//the fragment to launch is needed because by default the noteDetailActivity would be opened in the view mode which is not gonna be the option when it is long pressed.
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends ListFragment{

    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes;
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /*String[] values=new String[]{"Android","WebOS","Blackberry","Windows","Ubuntu","Linux","Mac OS X","Windows 7","iPhone","OS/2"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter); */
        /*
        notes=new ArrayList<Note>();
        notes.add(new Note("Hi!(edited)", "This is the second body(edited)", Note.Category.FINANCE));
        notes.add(new Note("I kicked that ceiling","Who the hell are you?", Note.Category.TECHNICAL));
        notes.add(new Note("OK now i'm tired","You and me together", Note.Category.PERSONAL));
        notes.add(new Note("Boom Clap","What you gotta say?", Note.Category.QUOTE));
        notes.add(new Note("Just like fire blah blah blah blah blah blah blah blah blah blah blah blah","Just like fire lighting up the way, if i could light up the world for just one day, just like magic.....wtf", Note.Category.TECHNICAL));
        notes.add(new Note("This shit requires time", "I don't know what to write and i'm so lonely", Note.Category.PERSONAL));
        notes.add(new Note("Milkshake","My milkshake brings all the boys to the yard", Note.Category.FINANCE));
        notes.add(new Note("Gun","Watch out for that girl she's got a gun for a tongue", Note.Category.TECHNICAL)); */
        //the noteAdapter created is a new object of class NoteAdapter which passes the current activity we're in and the object/variable of the data type which displays the notes. don't forget this.

        NoteDbAdapter dbAdapter = new NoteDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes = dbAdapter.getAllNotes();
        dbAdapter.close();
        noteAdapter= new NoteAdapter(getActivity(),notes);
        setListAdapter(noteAdapter);

        //getListView().setDivider(ContextCompat.getDrawable(getActivity(),android.R.color.black));
        //getListView().setDividerHeight(1);
        registerForContextMenu(getListView());

    }
//this function when used for viewing only requires the position as the argument. but in reality it also needs an option of the type of launchDetailActivity to be launched
    //for the functions which are used to only display the data, we need the position only
    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl,int pos)
    {
        Note note=(Note)getListAdapter().getItem(pos);
        Intent intent=new Intent(getActivity(),NoteDetailActivity.class);
        intent.putExtra(MainActivity.NOTE_ID_EXTRA,note.getNoteId());
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA,note.getTitle());
        intent.putExtra(MainActivity.NOTE_BODY_EXTRA,note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA,note.getCategory());

        switch (ftl)
        {
            case VIEW: intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT: intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity(intent);

    }

    @Override

    public void onListItemClick(ListView l, View v, int pos, long id)
    {
        super.onListItemClick(l,v,pos,id);
        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,pos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public  boolean onContextItemSelected(MenuItem item)
    {   //give me the position of the item i long pressed on
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition=info.position;

        Note note= (Note) getListAdapter().getItem(rowPosition);
        switch (item.getItemId())
        {
            case R.id.edit:
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,rowPosition);
                return true;

            case R.id.delete:
                NoteDbAdapter dbAdapter = new NoteDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getNoteId());

                // this is done so as to display the changes immediately after deleting the note (one at a time)
                //we use the global variable 'notes' to modify and see the changes

                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();
                dbAdapter.close();
        }
        return super.onContextItemSelected(item);
    }
}



