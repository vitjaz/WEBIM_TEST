package com.example.webim_test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText ed_title, ed_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ed_title = findViewById(R.id.edit_text_title);
        ed_text = findViewById(R.id.edit_text_text);

//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onAddClick(View view){
        //заводим новую заметку
        String newTitle = ed_title.getText().toString();
        String newText = ed_text.getText().toString();

        if(newTitle.equals("") || newText.equals("")){
            Toast.makeText(this, "Please enter new Title and new Text", Toast.LENGTH_SHORT).show();
        }else{
            //возвращаем данные в MainActivity и закрываем активность
            Intent data = new Intent();
            data.putExtra(MainActivity.ACCESS_TITLE, newTitle);
            data.putExtra(MainActivity.ACCESS_TEXT, newText);
            setResult(RESULT_OK, data);
            finish();
            //Toast.makeText(this, newTitle + "\n" + newText, Toast.LENGTH_SHORT).show();
        }

    }
}