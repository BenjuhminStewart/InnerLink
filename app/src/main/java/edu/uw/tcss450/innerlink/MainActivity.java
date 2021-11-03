package edu.uw.tcss450.innerlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatListFragment;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastFragment;
import edu.uw.tcss450.innerlink.ui.Home.HomeFragment;
import edu.uw.tcss450.innerlink.ui.Notification.NotificationListFragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                switch (menuItem.getItemId()){
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
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i("Orientation Change", "landscape");
        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Log.i("Orientation Change", "portrait");
        }
    }
}
