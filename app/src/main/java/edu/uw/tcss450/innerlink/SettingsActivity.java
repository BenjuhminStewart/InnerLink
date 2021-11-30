package edu.uw.tcss450.innerlink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;

import edu.uw.tcss450.innerlink.ui.settings.SettingsFragment;

/**
 * Represents the settings the user can navigate to through the floating settings button.
 * Includes changing themes.
 *
 * @author Satchit Dahal
 * @author Alec Paule
 * @author Alex Perez
 * @author Benjamin Stewart
 *
 * @version 1 (Sprint 1R)
 */
public class SettingsActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    String theme;
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.getDefaultSharedPreferences(this);
        setAppTheme();
        theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.theme), getString(R.string.theme_def_value));

        super.onCreate(savedInstanceState);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        setContentView(R.layout.activity_settings);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Method that sets the app theme upon a change in the shared preference menu.
     */
    public void setAppTheme() {
        final String[] themeValues = getResources().getStringArray(R.array.theme_values);
        // The apps theme is decided depending upon the saved preferences on app startup
        String pref = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.theme), getString(R.string.theme_def_value));
        // Compares values for either Light or Dark
        if (pref.equals(themeValues[0])) {
            setTheme(R.style.AppTheme);
        }
        if (pref.equals(themeValues[1])) {
            setTheme(R.style.DarkAppTheme);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String themeString = getString(R.string.theme);
        if (key != null && sharedPreferences != null)
            if (key.equals(themeString)) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                if (!theme.equals(prefs.getString(getString(R.string.theme), "none"))) {
                    recreate();
                    recreate();
                }
            }
    }
}