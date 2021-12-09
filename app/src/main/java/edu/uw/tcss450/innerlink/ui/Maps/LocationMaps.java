package edu.uw.tcss450.innerlink.ui.Maps;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationMapsBinding;
import edu.uw.tcss450.innerlink.model.LocationViewModel;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Location.LocationListAddViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationMaps#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LocationMaps extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private LocationMapsViewModel mModel;
    private LocationListAddViewModel mAddLocation;
    private GoogleMap mMap;
    private UserInfoViewModel mUserModel;

    public LocationMaps() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        FragmentLocationMapsBinding binding = FragmentLocationMapsBinding.bind(getView());
        mAddLocation = new ViewModelProvider(getActivity()).get(LocationListAddViewModel.class);
        mModel = new ViewModelProvider(getActivity())
                .get(LocationMapsViewModel.class);
        mModel.addLocationObserver(getViewLifecycleOwner(), location -> {
                });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationViewModel model = new ViewModelProvider(getActivity())
                .get(LocationViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);

                final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
            }
        });

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Log.d("LAT/LONG", latLng.toString());
        String[] latLong = latLng.toString().split(" ");
        String latLongParent = latLong[1];
        String latLongFormatted = latLongParent.substring(1, latLongParent.length() - 2);
        String[] coordsString = latLongFormatted.split(",");
        ArrayList<Float> coords = new ArrayList<Float>();
        coords.add(Float.parseFloat(coordsString[0]));
        coords.add(Float.parseFloat(coordsString[1]));



        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());


        dialog
                .setTitle("Are you sure you want to add this location?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAddLocation.addLocationCoords(coords.get(0), coords.get(1), mUserModel.getmJwt());
                    }

                })
                .setNegativeButton("Cancel", null)
                .show().setCanceledOnTouchOutside(true);
        System.out.println("Made it past dialog");

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker"));

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));

    }
}