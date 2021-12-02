package edu.uw.tcss450.innerlink.ui.Forecasts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentForecastBinding;


/**
 * Represents the Forecast screen.
 */
public class ForecastFragment extends Fragment {
    private ForecastsViewModel mForecastModel;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForecastModel = new ViewModelProvider(getActivity())
                .get(ForecastsViewModel.class);
        mForecastModel.getCurrConditions(98335);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentForecastBinding binding = FragmentForecastBinding.bind(getView());

        mForecastModel.addForecastObserver(getViewLifecycleOwner(), forecast -> {
            binding.textCity.setText(forecast.getCity());
            binding.textTemperature.setText(forecast.getTemperature());
            binding.textCondition.setText(forecast.getCondition());
        });
    }

//    private void displayText() {
//        binding.forecastResponse.setText(mForecastModel.getmResponse().toString());
//    }
}