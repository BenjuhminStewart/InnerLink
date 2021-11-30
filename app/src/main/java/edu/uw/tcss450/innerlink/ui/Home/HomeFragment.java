package edu.uw.tcss450.innerlink.ui.Home;

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
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.SettingsActivity;
import edu.uw.tcss450.innerlink.databinding.FragmentHomeBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Home screen.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton notificationButton = (FloatingActionButton) view.findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_notification);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
    }
}
