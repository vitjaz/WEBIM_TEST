package com.example.webim_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    //реквест код
    private static  final int REQUEST_ACCESS_TYPE=1;

    private static  final int REQUEST_ACCESS_TYPE_DEL=2;
    //ключ для наших данных
    static final String ACCESS_TITLE="ACCESS_TITLE";
    static final String ACCESS_TEXT="ACCESS_TEXT";

    static final String ACCESS_ID_DELETE="ACCESS_ID";

    //список наших заметок
    public List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNotes();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this, notes);
        recyclerView.setAdapter(noteAdapter);


        //передаем  позицию в новую активити
        noteAdapter.setListener(new NoteAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                //получим  объект по его индексу из списка
                Note note = notes.get(position);

                //получим необходимые данные
                String title = note.getmTitle();
                String text = note.getmText();

                //положим в наш интент дату
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_TITLE, title);
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_TEXT, text);
                intent.putExtra(NoteDetailActivity.EXTRA_NOTE_ID, position);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE_DEL);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_note:
                Intent intent = new Intent(this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //для добавления
        if(requestCode == REQUEST_ACCESS_TYPE){
            if(resultCode == RESULT_OK){
                String title = data.getExtras().get(ACCESS_TITLE).toString();
                String text = data.getExtras().get(ACCESS_TEXT).toString();
                notes.add(new Note(title, text));
                noteAdapter.notifyDataSetChanged();
            }
        }

        //для удаления
        if(requestCode == REQUEST_ACCESS_TYPE_DEL){
            if(resultCode == RESULT_OK){
                int removeIndex = (int)data.getExtras().get(ACCESS_ID_DELETE);
                notes.remove(removeIndex);
                noteAdapter.notifyItemRemoved(removeIndex);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //загружаем список заметок, а если его нет - создаем новый
    private void loadNotes(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("note list", null);
        Type type = new TypeToken<ArrayList<Note>>() {}.getType();
        notes = gson.fromJson(json, type);

        if(notes == null){
            notes = new ArrayList<>();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNotes();
    }

    //сохраняем список заметок
    private void saveNotes(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        editor.putString("note list", json);
        editor.apply();
    }
}