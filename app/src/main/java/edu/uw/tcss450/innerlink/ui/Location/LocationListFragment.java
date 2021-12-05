package edu.uw.tcss450.innerlink.ui.Location;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

public class LocationListFragment extends Fragment {
    private UserInfoViewModel mUserModel;
    private LocationListViewModel mLocationListModel;
    private LocationListAddViewModel mAddLocationModel;

    public LocationListFragment() {
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
        return inflater.inflate(R.layout.fragment_location_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentLocationListBinding binding = FragmentLocationListBinding.bind(getView());

        mLocationListModel.addLocationListObserver(getViewLifecycleOwner(), locationList -> {
            binding.listRoot.setAdapter(new LocationRecyclerViewAdapter(locationList));
        });

        // Add location button was clicked. Add the location via the LocationListAddViewModel

    }
}