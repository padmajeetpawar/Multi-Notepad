package com.padmajeet.multinotepad.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.padmajeet.multinotepad.R;
import com.padmajeet.multinotepad.models.Note;
import com.padmajeet.multinotepad.utility.CommonFunction;
import com.padmajeet.multinotepad.utility.OnClickListener;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TextView toolBarTitle;
    private EditText etNote;
    private EditText etTitle;
    private Note oldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolBar = findViewById(R.id.toolbar);
        toolBarTitle = findViewById(R.id.toolbar_title);
        etTitle = findViewById(R.id.etTitle);
        etNote = findViewById(R.id.etNote);
        toolBarTitle.setText("Multi Notes");
        setSupportActionBar(toolBar);
        if (getIntent().getExtras() != null) {
            oldNote = (Note) getIntent().getSerializableExtra("OLD_NOTE");
            if (oldNote != null) {
                etTitle.setText(oldNote.getTitle());
                etNote.setText(oldNote.getNoteText());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meni_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                if (etTitle.getText() != null && etTitle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(EditActivity.this, "Un-titled activity was not saved.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    saveNote();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!etTitle.getText().toString().trim().isEmpty()) {
            showAlert();
        } else
            finish();
    }

    private void showAlert() {
        String noteTitle = "'" + etTitle.getText().toString().trim() + "'";
        CommonFunction.getInstance().showAlertDialog(EditActivity.this, "Your note is not saved! Save note " + noteTitle, "YES", "NO", new OnClickListener() {
            @Override
            public void OnPositiveButtonClick() {
                saveNote();
            }

            @Override
            public void OnNegativeButtonClick() {
                finish();
            }
        });
    }

    private void saveNote() {
        //Sun, Feb 10, 12:41 AM
        SimpleDateFormat sdf = new SimpleDateFormat("E,MMM d, hh:mm a");
        String lastSavedDate = sdf.format(new Date());
        String id;
        boolean isNew = true;
        if (oldNote != null && oldNote.getId() != null && !oldNote.getId().isEmpty()) {
            id = oldNote.getId();
            isNew = false;
        } else
            id = UUID.randomUUID().toString();
        Note note = new Note(id, etTitle.getText().toString().trim(), lastSavedDate, etNote.getText().toString().trim());
        note.setNew(isNew);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("NEW_NOTE", note);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
