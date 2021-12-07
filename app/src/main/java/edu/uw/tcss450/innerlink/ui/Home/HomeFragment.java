package edu.uw.tcss450.innerlink.ui.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.SettingsActivity;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsRequestListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentHomeBinding;
import edu.uw.tcss450.innerlink.model.LocationViewModel;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRequestRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsViewModel;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastCurrentViewModel;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastDailyViewModel;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastHourlyRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastHourlyViewModel;
import edu.uw.tcss450.innerlink.ui.Location.LocationListAddViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Home screen.
 */
public class HomeFragment extends Fragment {
    private ContactsViewModel mContactViewModel;
    private ForecastCurrentViewModel mForecastCurrentViewModel;
    private ForecastHourlyViewModel mForecastHourlyViewModel;
    private UserInfoViewModel mUserModel;
    private FragmentHomeBinding binding;
    private LocationViewModel mModel;
    private LocationListAddViewModel mAddLocationModel;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactViewModel.setUserInfoViewModel(mUserModel);
        mContactViewModel.getRequests();
        mForecastHourlyViewModel = new ViewModelProvider(getActivity()).get(ForecastHourlyViewModel.class);;
        mForecastCurrentViewModel = new ViewModelProvider(getActivity()).get(ForecastCurrentViewModel.class);
        mAddLocationModel = new ViewModelProvider(getActivity()).get(LocationListAddViewModel.class);
        mModel = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);
        if (mContactViewModel.getRequestFrom().size() == 0) {
            binding.textView2.setText("No Incoming Friend Requests");
        } else if (mContactViewModel.getRequestFrom().size() == 1) {
            binding.textView2.setText(mContactViewModel.getRequestFrom().size() + " Incoming Friend Request");
        } else {
            binding.textView2.setText(mContactViewModel.getRequestFrom().size() + " Incoming Friend Requests");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactViewModel.addRequestsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRootContactsRequests.setAdapter(
                        new ContactsRequestRecyclerViewAdapter(contactList, this.mContactViewModel));
            }
        });

        mModel.addLocationObserver(getViewLifecycleOwner(), location -> {
            ArrayList<Float> coords = fetchLatLong(location.toString());
            float lat = coords.get(0);
            System.out.println(lat);
            float lon = coords.get(1);
            System.out.println(lon);
            mForecastCurrentViewModel.getCurrConditionsLatLong(lat, lon);
            mForecastHourlyViewModel.getHourlyConditions(lat,lon);
        });







        mForecastCurrentViewModel.addForecastObserver(getViewLifecycleOwner(), forecast -> {
            binding.textCity.setText(forecast.getCity() + " | " + forecast.getTemperature());
            binding.textCondition.setText(forecast.getCondition());

            String condition = forecast.getCondition();
            if (condition.equalsIgnoreCase("Clouds")) {
                binding.imageCondition.setImageResource(R.drawable.ic_cloudy);
            } else if (condition.equalsIgnoreCase("Rain")) {
                binding.imageCondition.setImageResource(R.drawable.ic_rainy);
            } else if (condition.equalsIgnoreCase("Snow")) {
                binding.imageCondition.setImageResource(R.drawable.ic_snowy);
            } else if (condition.equalsIgnoreCase("Clear")) {
                binding.imageCondition.setImageResource(R.drawable.ic_sunny);
            } else if (condition.equalsIgnoreCase("Thunderstorm")) {
                binding.imageCondition.setImageResource(R.drawable.ic_thunder);
            } else if (condition.equalsIgnoreCase("Drizzle")) {
                binding.imageCondition.setImageResource(R.drawable.ic_drizzle);
            } else {
                binding.imageCondition.setImageResource(R.drawable.ic_misty);
            }
        });

        // hourly forecast
        mForecastHourlyViewModel.addForecastObserver(getViewLifecycleOwner(), forecastList -> {
            binding.listForecastHourly.setAdapter(new ForecastHourlyRecyclerViewAdapter(forecastList));
        });




    }

    private ArrayList<Float> fetchLatLong(String location) {
        ArrayList<Float> coords = new ArrayList<Float>();
        String locationArr[] = location.split(" ");
        String coordsComma = locationArr[1];
        String locationCommaArr[] = coordsComma.split(",");
        coords.add(Float.parseFloat(locationCommaArr[0]));
        coords.add(Float.parseFloat(locationCommaArr[1]));
        return coords;
    }
}