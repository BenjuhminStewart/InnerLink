package edu.uw.tcss450.innerlink.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.SettingsActivity;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.auth.signin.SignInViewModel;

/**
 * Represents the Settings screen which is populated by the preference fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private SettingsViewModel mSettingsViewModel;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.root_preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference){
        if (preference.getKey().equals(getContext().getString(R.string.settings_change_password))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Change Password");
            builder.setMessage("");
            final EditText oldPass = new EditText(getContext());
            final EditText newPass = new EditText(this.getActivity());
            final EditText confirmPass = new EditText(this.getActivity());
            final TextView oldPassText = new TextView(this.getActivity());
            final TextView newPassText = new TextView(this.getActivity());
            final TextView confirmPassText = new TextView(this.getActivity());
            oldPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            newPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            confirmPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            LinearLayout ll = new LinearLayout(new ContextThemeWrapper(getContext(), R.style.DarkAppTheme));
            ll.setOrientation(LinearLayout.VERTICAL);
            oldPassText.setText("   Old Password");
            newPassText.setText("   New Password");
            confirmPassText.setText("   Confirm Password");
            oldPassText.setSelected(true);
            ll.addView(oldPassText);
            ll.addView(oldPass);
            ll.addView(confirmPassText);
            ll.addView(confirmPass);
            ll.addView(newPassText);
            ll.addView(newPass);
            builder.setView(ll);
            builder.setPositiveButton(R.string.dialog_remove_confirm, (dialog, which) -> {
                System.out.println(confirmPass.getText().toString());
                System.out.println(newPass.getText().toString());
                mSettingsViewModel.changePassword(confirmPass.getText().toString(), newPass.getText().toString());
            });
            builder.setNegativeButton(R.string.dialog_remove_cancel, null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }
        return false;
    }
}