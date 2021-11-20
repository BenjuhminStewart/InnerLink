package edu.uw.tcss450.innerlink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import edu.uw.tcss450.innerlink.model.PushyTokenViewModel;
import me.pushy.sdk.Pushy;

/**
 * Represents the User authorization cycle to use the app.
 * New users are required to register and validate their input information.
 * Only registered and verified users are authorized to sign in and use the app.
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //  setAppTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // If it is not already running, start the Pushy listening service
        Pushy.listen(this);
        initiatePushyTokenRequest();
    }

    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }

    //public void setAppTheme() {
    //    final String[] themeValues = getResources().getStringArray(R.array.theme_values);
    //    Start up App Theme
    //    String pref = PreferenceManager.getDefaultSharedPreferences(this)
    //            .getString(getString(R.string.theme), getString(R.string.theme_def_value));
    // Compares values for either Light or Dark
    //    if (pref.equals(themeValues[0])) {
    //        setTheme(R.style.AppTheme);
    //    }
    //    if (pref.equals(themeValues[1])) {
    //        setTheme(R.style.DarkAppTheme);
    //   }
    //}

}
