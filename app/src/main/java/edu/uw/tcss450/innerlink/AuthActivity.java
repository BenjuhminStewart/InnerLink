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

    /**
     * Method initiates the pushy token request for retrieval.
     */
    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }
}
