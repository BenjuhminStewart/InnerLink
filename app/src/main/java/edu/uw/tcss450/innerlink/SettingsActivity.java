package edu.uw.tcss450.innerlink;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String themeString = getString(R.string.theme);
        if (key != null && sharedPreferences != null)
            if (key.equals(themeString)) {
                final String[] themeValues = getResources().getStringArray(R.array.theme_values);
                // Current App theme that is selected
                String pref = PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(getString(R.string.theme), getString(R.string.theme_def_value));
                // Checking which theme is selected
                if (pref.equals(themeValues[0])) {
                    setTheme(R.style.DarkAppTheme);
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit()
                            .putBoolean("theme_changed", true)
                            .apply();
                }
                if (pref.equals(themeValues[1])) {
                    setTheme(R.style.DarkAppTheme);
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit()
                            .putBoolean("theme_changed", true)
                            .apply();
                }
            }
    }
}