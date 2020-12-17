package com.example.webim_test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int noteId;

    private TextView textView_title, textView_text;

    public static final String EXTRA_NOTE_TITLE = "noteTitle";
    public static final String EXTRA_NOTE_TEXT = "noteText";
    public static final String EXTRA_NOTE_ID = "noteId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView_title = findViewById(R.id.title_detail);
        textView_text = findViewById(R.id.text_detail);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);


        String title = (String) getIntent().getExtras().get(EXTRA_NOTE_TITLE);
        String text = (String) getIntent().getExtras().get(EXTRA_NOTE_TEXT);
        noteId = (int) getIntent().getExtras().get(EXTRA_NOTE_ID);

        actionBar.setTitle(title);

        textView_title.setText(title);
        textView_text.setText(text);


    }

    public void onCloseClick(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onDeleteClick(View view){
        Intent data = new Intent();
        data.putExtra(MainActivity.ACCESS_ID_DELETE, noteId);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

}