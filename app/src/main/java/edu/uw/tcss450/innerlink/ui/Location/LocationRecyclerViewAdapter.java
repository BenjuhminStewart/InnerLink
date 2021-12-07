package edu.uw.tcss450.innerlink.ui.Location;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.security.Provider;
import java.util.List;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationCardBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastCurrentViewModel;

/**
 * Allows the user to scroll through the list of locations.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder>{
    //Store all of the Locations to present
    private final List<edu.uw.tcss450.innerlink.ui.Location.Location> mLocationList;
    private final LocationFragment mLocationFragment;
    private final UserInfoViewModel mUserModel;
    private final LocationListRemoveViewModel mRemoveLocation;

    public LocationRecyclerViewAdapter(List<edu.uw.tcss450.innerlink.ui.Location.Location> locationList, LocationFragment parent) {
        mLocationList = locationList;
        mLocationFragment = parent;
        ViewModelProvider provider = new ViewModelProvider(mLocationFragment.getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mRemoveLocation = provider.get(LocationListRemoveViewModel.class);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationRecyclerViewAdapter.LocationViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_location_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.setLocation(mLocationList.get(position));

        holder.itemView.setOnClickListener(v -> {
            Navigation.findNavController(holder.mView).navigate(
                    LocationFragmentDirections.actionNavigationLocationsToForecastFragment(holder.mLocation)
            );

        });

    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Location Recycler View.
     */
    public class LocationViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentLocationCardBinding binding;
        private edu.uw.tcss450.innerlink.ui.Location.Location mLocation;

        public LocationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentLocationCardBinding.bind(view);
        }

        void setLocation(final Location location) {
            mLocation = location;
            binding.textCity.setText(location.getCity());

            binding.buittonMore2.setOnClickListener( v -> {
                int key = location.getKey();
                removeLocation(location, this, key);
            });

        }


    }

    private void removeLocation(final Location location, LocationRecyclerViewAdapter.LocationViewHolder view, int key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mLocationFragment.getActivity());
        builder.setTitle(R.string.dialog_remove_title);
        builder.setMessage(R.string.dialog_remove_message);
        builder.setPositiveButton(R.string.dialog_remove_confirm, (dialog, which) -> {
            mRemoveLocation.removeLocation(key, mUserModel.getmJwt());
            AlertDialog.Builder builderLeft = new AlertDialog.Builder(mLocationFragment.getActivity());
            builderLeft.setTitle("Success!");
            builderLeft.setMessage("You have Deleted the Location.");
            builderLeft.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Navigation.findNavController(mLocationFragment.getView()).navigate(
                            LocationFragmentDirections.actionNavigationLocationsSelf());
                }
            });
            AlertDialog alertDialogLeft = builderLeft.create();
            alertDialogLeft.show();

        });
        builder.setNegativeButton(R.string.dialog_remove_cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
