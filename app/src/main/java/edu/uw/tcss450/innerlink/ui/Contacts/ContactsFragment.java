package edu.uw.tcss450.innerlink.ui.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Notification screen.
 *
 * NOTE: Class may be modified in future versions. When a user selects a Notification, they are
 * navigated directly to the appropriate screen (i.e. New Connection -> Connections, New Message ->
 * Messages)
 */
public class ContactsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }
}