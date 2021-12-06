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
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsViewModel;
import edu.uw.tcss450.innerlink.ui.auth.signin.SignInViewModel;

/**
 * Represents the Settings screen which is populated by the preference fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private SettingsViewModel mSettingsViewModel;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        mSettingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        UserInfoViewModel mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mSettingsViewModel.setUserInfoViewModel(mUserModel);
        addPreferencesFromResource(R.xml.root_preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference){
        if (preference.getKey().equals(getContext().getString(R.string.settings_change_password))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Change Password");
            builder.setMessage("");
            final EditText oldPass = new EditText(this.getActivity());
            final EditText newPass = new EditText(this.getActivity());
            final EditText confirmPass = new EditText(this.getActivity());
            final TextView oldPassText = new TextView(this.getActivity());
            final TextView newPassText = new TextView(this.getActivity());
            final TextView confirmPassText = new TextView(this.getActivity());
            oldPass.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            newPass.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            confirmPass.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            oldPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            newPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            confirmPassText.setTextColor(getResources().getColor(R.color.colorPrimary));
            LinearLayout layout = new LinearLayout(new ContextThemeWrapper(getContext(), R.style.DarkAppTheme));
            layout.setOrientation(LinearLayout.VERTICAL);
            oldPassText.setText("   Old Password");
            newPassText.setText("   New Password");
            confirmPassText.setText("   Confirm Password");
            oldPassText.setSelected(true);
            layout.addView(oldPassText);
            layout.addView(oldPass);
            layout.addView(confirmPassText);
            layout.addView(confirmPass);
            layout.addView(newPassText);
            layout.addView(newPass);
            builder.setView(layout);
            builder.setPositiveButton(R.string.dialog_remove_confirm, (dialog, which) -> {
                if (!(oldPass.getText().toString().equals(confirmPass.getText().toString()))) {
                    AlertDialog.Builder builderDecline = new AlertDialog.Builder(getContext());
                    builderDecline.setTitle("Failure");
                    builderDecline.setMessage("Passwords do not match. Please try again.");
                    builderDecline.setNegativeButton("Cancel", null);
                    AlertDialog alertDialogDecline = builderDecline.create();
                    alertDialogDecline.show();
                } else {
                    mSettingsViewModel.changePassword(confirmPass.getText().toString(), newPass.getText().toString());
                }
            });
            builder.setNegativeButton(R.string.dialog_remove_cancel, null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }
        return false;
    }
}