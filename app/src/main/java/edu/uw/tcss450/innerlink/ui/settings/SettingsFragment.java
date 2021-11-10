package edu.uw.tcss450.innerlink.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.SettingsActivity;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.root_preferences);

    }
}