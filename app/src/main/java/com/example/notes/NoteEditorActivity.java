package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        final EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        int noteID = intent.getIntExtra("noteID",-1);

        if(noteID != -1)
            editText.setText(MainActivity.notes.get(noteID));
        else{
            MainActivity.notes.add("");
            noteID = MainActivity.notes.size() - 1;
        }

        final int finalNoteID = noteID;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.notes.set(finalNoteID,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("Notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
