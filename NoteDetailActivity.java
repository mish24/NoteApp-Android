package com.poonammishra.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class NoteDetailActivity extends AppCompatActivity {
    public  static final String NEW_NOTE_EXTRA="New Note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        //don't forget to add the function into onCreate().
        createAndAddFragment();
    }
    private  void createAndAddFragment()
    {
        Intent intent=getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch=
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        switch (fragmentToLaunch)
        {
            case VIEW:NoteViewFragment noteViewFragment=new NoteViewFragment();
                setTitle(R.string.viewNoteFragment);
                //first parameter gives the class to which it is added, second one gives the object of the class.
                ft.add(R.id.activitynotedetail,noteViewFragment,"NOTE_VIEW_FRAGMENT");

                break;
            case EDIT:NoteEditFragment noteEditFragment=new NoteEditFragment();
                setTitle(R.string.editNoteFragment);
                //first parameter gives the class to which it is added, second one gives the object of the class.
                ft.add(R.id.activitynotedetail,noteEditFragment,"NOTE_EDIT_FRAGMENT");

                break;
            case CREATE:NoteEditFragment noteCreatefragment=new NoteEditFragment();
                setTitle(R.string.createNoteFragment);
                Bundle bundle=new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA,true);
                noteCreatefragment.setArguments(bundle);
                ft.add(R.id.activitynotedetail,noteCreatefragment,"NOTE_CREATE_FRAGMENT");
                break;
        }

        ft.commit();

    }
}
