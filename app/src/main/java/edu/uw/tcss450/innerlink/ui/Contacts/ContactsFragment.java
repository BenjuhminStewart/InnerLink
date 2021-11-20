package edu.uw.tcss450.innerlink.ui.Contacts;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatSendViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private String mEmail;
    private String mContacts;
    private String mRequestsFrom;
    private String mRequestsTo;

    public ContactsFragment(String email, String contacts,
                            String requestsFrom, String requestsTo) {
        mEmail = email;
        mContacts = contacts;
        mRequestsFrom = requestsFrom;
        mRequestsTo = requestsTo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    public void sendRequest(String receiver) {

    }
}