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
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import edu.uw.tcss450.innerlink.databinding.FragmentHomeBinding;
import edu.uw.tcss450.innerlink.ui.Maps.LocationMaps;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentLocationBinding;
import edu.uw.tcss450.innerlink.model.LocationViewModel;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomListFragmentDirections;
import edu.uw.tcss450.innerlink.ui.Maps.LocationMapsViewModel;

/**
 * Represents the list of locations that the user would like to have forecasts for.
 */
public class LocationFragment extends Fragment {
    private UserInfoViewModel mUserModel;
    private LocationListViewModel mLocationListModel;
    private LocationListAddViewModel mAddLocationModel;
    private LocationMapsViewModel mLocationMapViewModel;
    private FragmentLocationBinding binding;


    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        System.out.println("Made it to location fragment");
        mUserModel = provider.get(UserInfoViewModel.class);
        mLocationListModel = provider.get(LocationListViewModel.class);
        mAddLocationModel = provider.get(LocationListAddViewModel.class);
        mLocationMapViewModel = provider.get(LocationMapsViewModel.class);
        mLocationListModel.connectGet(mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_location, container, false);
        binding = FragmentLocationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());

        mLocationListModel.addLocationListObserver(getViewLifecycleOwner(), locationList -> {
            System.out.println("Made it to onViewCreated");
            binding.listRoot.setAdapter(new LocationRecyclerViewAdapter(locationList, this));
            System.out.println("Made it past SetAdapter");
            System.out.println(locationList.toString());
        });



        // Add location button was clicked. Add the location via the LocationListAddViewModel
        binding.buttonAddLocationZip.setOnClickListener(button -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            EditText input = new EditText(getContext());
            input.setHint("   Zipcode");



            input.setHighlightColor(getResources().getColor(R.color.colorPrimary));

            input.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            input.setHintTextColor(getResources().getColor(R.color.lightGrey));

            LinearLayout layout = new LinearLayout(new ContextThemeWrapper(getContext(), R.style.DarkAppTheme));
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(input);


            dialog.setView(layout);

            dialog
                .setTitle("Add a location?")
                .setMessage("Please type in the Zipcode")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int zipcode = Integer.parseInt(input.getText().toString());
                            mAddLocationModel.addLocation(zipcode, mUserModel.getmJwt());
                            AlertDialog.Builder builderLeft = new AlertDialog.Builder(getActivity());
                            builderLeft.setTitle("Success!");
                            builderLeft.setMessage("You have added a location.");
                            builderLeft.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Navigation.findNavController(getView()).navigate(
                                            LocationFragmentDirections.actionNavigationLocationsSelf());
                                }
                            });
                            AlertDialog alertDialogLeft = builderLeft.create();
                            alertDialogLeft.show();
                        }
                        catch (NumberFormatException e){
                            AlertDialog.Builder builderDecline = new AlertDialog.Builder(getContext());
                            builderDecline.setTitle("Failure");
                            builderDecline.setMessage("Entry is not a zipcode. Please try again.");
                            builderDecline.setNegativeButton("Cancel", null);
                            AlertDialog alertDialogDecline = builderDecline.create();
                            alertDialogDecline.show();

                        }
                    }


                })
                .setNegativeButton("Cancel", null)
                .show().setCanceledOnTouchOutside(true);

        });

        binding.buttonAddLocation.setOnClickListener(button -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());


            EditText input = new EditText(getContext());
            input.setHint("Latitude");
            EditText input2 = new EditText(getContext());
            input2.setHint("Longitude");
            input.setHighlightColor(getResources().getColor(R.color.colorPrimary));
            input2.setHighlightColor(getResources().getColor(R.color.colorPrimary));

            input.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            input.setHintTextColor(getResources().getColor(R.color.lightGrey));
            input2.setHintTextColor(getResources().getColor(R.color.lightGrey));
            input2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            LinearLayout layout = new LinearLayout(new ContextThemeWrapper(getContext(), R.style.DarkAppTheme));
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(input);

            layout.addView(input2);

            dialog.setView(layout);

            dialog
                    .setTitle("Add a location?")
                    .setMessage("Please type in the Latitude and Longitude")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                String lat = input.getText().toString();
                                String lon = input2.getText().toString();
                                float latFloat = Float.parseFloat(lat);
                                float lonFloat = Float.parseFloat(lon);

                                mAddLocationModel.addLocationCoords(latFloat, lonFloat, mUserModel.getmJwt());
                                AlertDialog.Builder builderLeft = new AlertDialog.Builder(getActivity());
                                builderLeft.setTitle("Success!");
                                builderLeft.setMessage("You have added a location.");
                                builderLeft.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Navigation.findNavController(getView()).navigate(
                                                LocationFragmentDirections.actionNavigationLocationsSelf());
                                    }
                                });
                                AlertDialog alertDialogLeft = builderLeft.create();
                                alertDialogLeft.show();
                            }
                            catch (NumberFormatException e) {
                                AlertDialog.Builder builderDecline = new AlertDialog.Builder(getContext());
                                builderDecline.setTitle("Failure");
                                builderDecline.setMessage("Invalid Coordinates. Please try again.");
                                builderDecline.setNegativeButton("Cancel", null);
                                AlertDialog alertDialogDecline = builderDecline.create();
                                alertDialogDecline.show();
                            }



                        }


                    })
                    .setNegativeButton("Cancel", null)
                    .show().setCanceledOnTouchOutside(true);

            System.out.println("Made it past dialog");
        });

        binding.buttonAddLocationMap.setOnClickListener(button -> {
                Navigation.findNavController(getView()).navigate(LocationFragmentDirections.actionNavigationLocationsToLocationMaps());
        });


    }
}