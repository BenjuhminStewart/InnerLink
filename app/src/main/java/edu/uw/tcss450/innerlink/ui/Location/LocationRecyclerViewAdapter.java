package edu.uw.tcss450.innerlink.ui.Location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationCardBinding;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastCurrentViewModel;

/**
 * Allows the user to scroll through the list of locations.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder>{
    //Store all of the Locations to present
    private final List<edu.uw.tcss450.innerlink.ui.Location.Location> mLocationList;

    public LocationRecyclerViewAdapter(List<edu.uw.tcss450.innerlink.ui.Location.Location> locationList) {
        mLocationList = locationList;
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

        }


    }
}
