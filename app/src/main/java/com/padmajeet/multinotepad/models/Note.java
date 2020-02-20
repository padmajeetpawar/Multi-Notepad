package com.padmajeet.multinotepad.models;

import java.io.Serializable;

public class Note implements Serializable {
    private String id;
    private String title;
    private String lastSavedDate;
    private String noteText;
    private boolean isNew;

    public Note(String id, String title, String lastSavedDate, String noteText) {
        this.id = id;
        this.title = title;
        this.lastSavedDate = lastSavedDate;
        this.noteText = noteText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastSavedDate() {
        return lastSavedDate;
    }

    public void setLastSavedDate(String lastSavedDate) {
        this.lastSavedDate = lastSavedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", lastSavedDate='" + lastSavedDate + '\'' +
                ", noteText='" + noteText + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
