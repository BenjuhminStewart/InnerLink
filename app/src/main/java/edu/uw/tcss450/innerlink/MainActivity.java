package edu.uw.tcss450.innerlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.material.badge.BadgeDrawable;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Set;

import edu.uw.tcss450.innerlink.databinding.ActivityMainBinding;
import edu.uw.tcss450.innerlink.model.NewMessageCountViewModel;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.services.PushReceiver;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomViewModel;


import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomListFragment;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastFragment;
import edu.uw.tcss450.innerlink.ui.Home.HomeFragment;
import edu.uw.tcss450.innerlink.ui.Notification.NotificationListFragment;
import edu.uw.tcss450.innerlink.ui.settings.SettingsFragment;

/**
 * Represents user navigation through the app.
 * Users navigate through the bottom navigation or other on-screen links.
 *
 * @author Satchit Dahal
 * @author Alec Paule
 * @author Alex Perez
 * @author Benjamin Stewart
 *
 * @version 1 (Sprint 1R)
 */
public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainPushMessageReceiver mPushMessageReceiver;
    private NewMessageCountViewModel mNewMessageModel;

    String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.getDefaultSharedPreferences(this);
        setAppTheme();

        theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.theme), getString(R.string.theme_def_value));

        super.onCreate(savedInstanceState);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        new ViewModelProvider(
                this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt()))
                .get(UserInfoViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO Make a button to navigate to notifications
        // TODO navigation_notification changed to navigation_contacts for now
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of IDs because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contacts, R.id.navigation_chats, R.id.navigation_forecast)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Gain access to the message count ViewModel
        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        // Display or remove the notification badge from the bottom navigation
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.chatRoomFragment) {
                //When the user navigates to the chats page, reset the new message count.
                //This will need some extra logic for your project as it should have
                //multiple chat rooms.
                mNewMessageModel.reset();
            }
        });

        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.navigation_chats);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("Orientation Change", "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("Orientation Change", "portrait");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            Log.d("SETTINGS", "Clicked");
            return true;
        }
        if (id == R.id.action_sign_out) {
            signOut();
            Log.d("SIGN OUT", "Clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The sign out functionality for the sign out button.
     */
    public void signOut() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);

        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, iFilter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!theme.equals(prefs.getString(getString(R.string.theme), "none"))) {
            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null){
            unregisterReceiver(mPushMessageReceiver);
        }
    }

    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {
        private ChatRoomViewModel mModel =
                new ViewModelProvider(MainActivity.this)
                        .get(ChatRoomViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc =
                    Navigation.findNavController(
                            MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                //If the user is not on the chat screen, update the
                // NewMessageCountView Model.
                if (nd.getId() != R.id.chatRoomFragment) {
                    mNewMessageModel.increment();
                }

                //Inform the view model holding chatroom messages of the new
                //message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
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

    /**
     * Getter method to get the current user info view model.
     */
    public UserInfoViewModel getUserInfoViewModel() {
        return this.getUserInfoViewModel();
    }

}

