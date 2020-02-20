package com.padmajeet.multinotepad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.padmajeet.multinotepad.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
