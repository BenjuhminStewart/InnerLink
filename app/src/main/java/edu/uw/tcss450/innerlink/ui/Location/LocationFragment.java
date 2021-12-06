package edu.uw.tcss450.innerlink.ui.Location;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * Represents the list of locations that the user would like to have forecasts for.
 */
public class LocationFragment extends Fragment {
    private UserInfoViewModel mUserModel;
    private LocationListViewModel mLocationListModel;
    private LocationListAddViewModel mAddLocationModel;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mLocationListModel = provider.get(LocationListViewModel.class);
        mAddLocationModel = provider.get(LocationListAddViewModel.class);
        mLocationListModel.connectGet(mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());

        mLocationListModel.addLocationListObserver(getViewLifecycleOwner(), locationList -> {
            binding.listRoot.setAdapter(new LocationRecyclerViewAdapter(locationList));
        });

        // Add location button was clicked. Add the location via the LocationListAddViewModel
        binding.buttonAddLocation.setOnClickListener(button -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            EditText input = new EditText(getContext());
            dialog.setView(input);
            dialog
                .setTitle("Add a location?")
                .setMessage("Please type in the zipcode")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int zipcode = Integer.parseInt(input.getText().toString());
                        mAddLocationModel.addLocation(zipcode, mUserModel.getmJwt());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }
}