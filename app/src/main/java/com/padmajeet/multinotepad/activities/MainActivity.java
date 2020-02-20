package com.padmajeet.multinotepad.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.padmajeet.multinotepad.R;
import com.padmajeet.multinotepad.adapters.NoteListAdapter;
import com.padmajeet.multinotepad.models.Note;
import com.padmajeet.multinotepad.models.NoteList;
import com.padmajeet.multinotepad.utility.CommonFunction;
import com.padmajeet.multinotepad.utility.OnClickListener;
import com.padmajeet.multinotepad.utility.OnItemClick;

public class MainActivity extends AppCompatActivity implements OnItemClick {

    private RecyclerView notesRecyclerView;
    private NoteListAdapter noteListAdapter;
    private TextView errorTextView;
    private Toolbar toolBar;
    private TextView toolBarTitle;
    private List<Note> noteList = new ArrayList<>();
    private static final String fileName = "storage.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = findViewById(R.id.toolbar);
        toolBarTitle = findViewById(R.id.toolbar_title);

        errorTextView = findViewById(R.id.tv_no_not_error);
        notesRecyclerView = findViewById(R.id.rv_notes);
        setSupportActionBar(toolBar);

        boolean isFilePresent = isFilePresent(MainActivity.this, fileName);
        if (isFilePresent) {
            new FetchData().execute();
        } else {
            create(MainActivity.this);
            setAdapter();
        }
    }

    private class FetchData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return read(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            Gson gson = new Gson();
            NoteList tmpNoteList = gson.fromJson(jsonString, NoteList.class);
            noteList = tmpNoteList.getNoteList();
            setAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_addnote:
                intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String read(Context context) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private void create(Context context) {
        try {
            File newFile = new File(context.getFilesDir().getAbsolutePath(), fileName);
            newFile.createNewFile();
        } catch (Exception fileNotFound) {
            fileNotFound.printStackTrace();
        }

    }

    private void writeToFile(File file, List<Note> noteList) {
        NoteList notes = new NoteList();
        notes.setNoteList(noteList);
        String jsonString = new Gson().toJson(notes);
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(jsonString);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    private void setAdapter() {
        if (noteList != null && !noteList.isEmpty()) {
            toolBarTitle.setText("Multi Note (" + noteList.size() + ")");
            errorTextView.setVisibility(View.GONE);
            notesRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            notesRecyclerView.setHasFixedSize(true);
            notesRecyclerView.setLayoutManager(llm);
            noteListAdapter = new NoteListAdapter(MainActivity.this, noteList, this);
            notesRecyclerView.setAdapter(noteListAdapter);
        } else {
            toolBarTitle.setText("Multi Note");
            errorTextView.setVisibility(View.VISIBLE);
            notesRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        writeToFile(new File(MainActivity.this.getFilesDir().getAbsolutePath(), fileName), noteList);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 101) {
                if (resultCode == Activity.RESULT_OK) {
                    Note note = (Note) data.getSerializableExtra("NEW_NOTE");
                    if (note != null) {
                        if (note.isNew()) {
                            noteList.add(note);
                        } else {
                            for (Note note1 : noteList) {
                                if (note1 != null && note1.getId() != null && note1.getId().equals(note.getId())) {
                                    noteList.remove(note1);
                                    noteList.add(note);
                                    setAdapter();
                                }
                            }
                        }
                    }
                    setAdapter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemLongClick(Object obj) {
        final Note note = (Note) obj;
        CommonFunction.getInstance().showAlertDialog(MainActivity.this, "Delete Note '" + note.getTitle() + "'", "YES", "NO", new OnClickListener() {
            @Override
            public void OnPositiveButtonClick() {
                noteList.remove(note);
                setAdapter();
            }

            @Override
            public void OnNegativeButtonClick() {

            }
        });
    }

    @Override
    public void onItemClick(Object obj) {
        Note note = (Note) obj;
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("OLD_NOTE", note);
        startActivityForResult(intent, 101);
    }
}
