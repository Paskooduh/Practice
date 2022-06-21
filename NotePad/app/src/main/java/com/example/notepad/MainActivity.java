package com.example.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.widget.Button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.notepad.Adapter.NotesListAdapter;
import com.example.notepad.DataBase.RoomDB;
import com.example.notepad.Models.Notes;



public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button button_Add;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_Grid);
        button_Add = findViewById(R.id.button_Add);

        database = RoomDB.getInstance(this);
        notes = database.mainDao().getAll();

        updateRecycle(notes);

        button_Add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
            Intent intent =new Intent(MainActivity.this,NotesTakerActivity.class);
            startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDao().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesListAdapter.notifyDataSetChanged();

            }
        }
    }



    private void updateRecycle(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }
    private NotesClickListener notesClickListener =new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };
}