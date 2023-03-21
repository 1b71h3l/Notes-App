package com.example.tp4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteLongClickListener ,NotesAdapter.OnNoteModifyClickListener {
    private RecyclerView recyView;
    List<Note> notes = new ArrayList<Note>();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyView = (RecyclerView) findViewById(R.id.recyclerView);

        sharedPreferences = getSharedPreferences("notes", Context.MODE_PRIVATE);

        FloatingActionButton addNote = (FloatingActionButton) findViewById(R.id.add_note);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add a new note");
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.add_note, null);
                builder.setView(view);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Title = view.findViewById(R.id.title);
                        EditText Content = view.findViewById(R.id.content);
                        String title = Title.getText().toString();
                        String content = Content.getText().toString();
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        notes.add(new Note(title, content, R.drawable.note,date));
                        storeNotes();
                        recyView.getAdapter().notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
        retrieveNotes();
        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.setAdapter(new NotesAdapter(notes,MainActivity.this,this, this));
    }
    private void storeNotes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonNotes = gson.toJson(notes);
        editor.putString("notes", jsonNotes);
        editor.apply();
    }

    private void retrieveNotes() {
        String jsonNotes = sharedPreferences.getString("notes", "");
        Log.d("Notes",jsonNotes);
        Gson gson = new Gson();
        Note[] noteArray = gson.fromJson(jsonNotes, Note[].class);
        notes.clear();
        if (noteArray != null) {
            notes.addAll(Arrays.asList(noteArray));
        }


    }

    @Override
    public void onNoteLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete note");
        builder.setMessage("Are you sure you want to delete this note?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notes.remove(position);
                storeNotes();
                recyView.getAdapter().notifyItemRemoved(position);
            }
        });
        builder.show();
    }

    @Override
    public void showModifyDialog(Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Edit note");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_note, null);
        EditText Title = view.findViewById(R.id.edit_title);
        EditText Content = view.findViewById(R.id.edit_content);
        TextView Date = view.findViewById(R.id.edit_date);
        Title.setText(note.titre);
        Content.setText(note.description);
        Date.setText("Created on :" + note.date);
        builder.setView(view);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = Title.getText().toString();
                String content = Content.getText().toString();
                Note note = notes.get(position);
                note.setDescription(content);
                note.setTitre(title);
                storeNotes();
                recyView.getAdapter().notifyDataSetChanged();
            }
        });
        builder.show();
    }
}


