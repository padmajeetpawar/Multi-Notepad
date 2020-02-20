package com.padmajeet.multinotepad.models;

import java.io.Serializable;
import java.util.List;

public class NoteList implements Serializable {
    private List<Note> noteList;

    public NoteList() {
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public String toString() {
        return "NoteList{" +
                "noteList=" + noteList +
                '}';
    }
}
