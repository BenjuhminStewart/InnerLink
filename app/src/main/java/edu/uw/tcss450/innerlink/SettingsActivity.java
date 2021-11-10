package edu.uw.tcss450.innerlink;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import edu.uw.tcss450.innerlink.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}