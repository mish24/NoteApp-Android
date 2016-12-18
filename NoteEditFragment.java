package com.poonammishra.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
    private ImageButton noteCatButton;
    private Note.Category savedButtonCategory;
    private EditText title, message;
    private AlertDialog categoryDialogObject, confirmDialogObject;
    private boolean newNote= false;
    private long noteId=0;


    public NoteEditFragment() {
        // Required empty public constructor
    }

//the only difference between edit and view is that edit allows us to edit at the same time. hence grabbing the info is needed on both the classes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            newNote=bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA,false);
        }
        View fragmentLayout= inflater.inflate(R.layout.fragment_note_edit, container, false);
         title=(EditText)fragmentLayout.findViewById(R.id.editNoteTitle);
         message=(EditText)fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton=(ImageButton)fragmentLayout.findViewById(R.id.editNoteButton);
        Button savedButton= (Button)fragmentLayout.findViewById(R.id.saveNote);


        //this line grabs the widget reference from the layout and populates them with note data using the keys
        Intent intent=getActivity().getIntent();

        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA,""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_BODY_EXTRA,""));
        noteId=intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA, 0);

        if(savedButtonCategory!=null)
        {
            noteCatButton.setImageResource(Note.categoryToDrawable(savedButtonCategory));

        }
        else if(!newNote){
        Note.Category noteCat=(Note.Category)intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
        savedButtonCategory=noteCat;
        noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));}

        buildCategoryDialog();
        buildConfirmDialog();
        //don't forgt to call the function created on the onclick view.
        noteCatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                categoryDialogObject.show();
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                confirmDialogObject.show();
            }
        });

        return fragmentLayout;
    }
    private void buildCategoryDialog()
    {
        final String[] categories= new String[]{"Personal","Technical","Quote","Finance"};
        AlertDialog.Builder categoryBuilder=new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Type");
        categoryBuilder.setSingleChoiceItems(categories,0,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                categoryDialogObject.cancel();
                switch (item)
                {
                    case 0: savedButtonCategory= Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:savedButtonCategory= Note.Category.TECHNICAL;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;
                    case 2:savedButtonCategory= Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.q);
                        break;
                    case 3: savedButtonCategory= Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;

                }
            }
                }




        );
        categoryDialogObject=categoryBuilder.create();
    }

    private void buildConfirmDialog()
    {
        AlertDialog.Builder confirmBuilder= new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("The changes will be edited and upgraded");
        confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                Log.d("Save note", "Note title: " + title.getText() + "Note message" + message.getText()
                + "Note category" + savedButtonCategory);
                NoteDbAdapter dbAdapter= new NoteDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();

                if(newNote)
                {
                    dbAdapter.createNote(title.getText() + "", message.getText()+ "",(savedButtonCategory == null)? Note.Category.PERSONAL : savedButtonCategory);
                }
                else {
                    dbAdapter.updateNote(noteId, title.getText()+ "", message.getText()+ "",savedButtonCategory);

                }
                dbAdapter.close();

                Intent intent= new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
                }
        );
        confirmDialogObject=confirmBuilder.create();
    }


}
