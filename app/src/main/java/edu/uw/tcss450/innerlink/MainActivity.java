package edu.uw.tcss450.innerlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Set;

import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatListFragment;
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

    BottomNavigationView navigationView;

    String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.getDefaultSharedPreferences(this);
        setAppTheme();

        theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.theme), getString(R.string.theme_def_value));

        super.onCreate(savedInstanceState);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.navigation_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                HomeFragment homeFragment = null;
                NotificationListFragment notificationListFragment = null;
                ChatListFragment chatListFragment = null;
                ForecastFragment forecastFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, homeFragment).commit();
                        break;

                    case R.id.navigation_chats:
                        chatListFragment = new ChatListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, chatListFragment).commit();
                        break;

                    case R.id.navigation_notification:
                        notificationListFragment = new NotificationListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, notificationListFragment).commit();
                        break;

                    case R.id.navigation_forecast:
                        forecastFragment = new ForecastFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, forecastFragment).commit();
                        break;
                }

                return true;
            }
        });
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
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!theme.equals(prefs.getString(getString(R.string.theme), "none"))) {
            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }
}