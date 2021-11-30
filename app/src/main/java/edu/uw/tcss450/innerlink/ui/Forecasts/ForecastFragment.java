package edu.uw.tcss450.innerlink.ui.Forecasts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import edu.uw.tcss450.innerlink.databinding.FragmentContactsBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentForecastBinding;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsFragmentArgs;


/**
 * Represents the Forecast screen.
 */
public class ForecastFragment extends Fragment {

    private FragmentForecastBinding binding;

    private ForecastsViewModel mForecastModel;

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForecastModel = new ViewModelProvider(getActivity())
                .get(ForecastsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForecastBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.hourly.setOnClickListener(button -> {
            mForecastModel.getHourlyConditions(98335);
            displayText();
        });

        binding.currentConditions.setOnClickListener(button -> {mForecastModel.getCurrConditions(98335);
            displayText();
            //binding.textForecastMain.setText();
//            try {
//                ForecastModel currentForecastModel = ForecastModel.createFromJsonString(mForecastModel.getmResponse().toString());
//                binding.textForecastMain.setText(currentForecastModel.getmConditionMain());
//                binding.textForecastDescription.setText(currentForecastModel.getmConditionDesc());
//                binding.textForecastString.setText(currentForecastModel.getmConditionString());
//                System.out.println(currentForecastModel.getmConditionDesc());
//                System.out.println("here");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        });

        binding.fiveDay.setOnClickListener(button -> {mForecastModel.getDailyConditions(98335);
            displayText();
        });

    }

    private void displayText() {
        binding.forecastResponse.setText(mForecastModel.getmResponse().toString());
    }

}