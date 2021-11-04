package edu.uw.tcss450.innerlink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Represents the User authorization cycle to use the app.
 * New users are required to register and validate their input information.
 * Only registered and verified users are authorized to sign in and use the app.
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}
