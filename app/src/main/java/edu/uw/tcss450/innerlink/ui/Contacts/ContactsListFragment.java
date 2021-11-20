package edu.uw.tcss450.innerlink.ui.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Chat screen where all of a user's active Chat Rooms are listed and displayed.
 */
public class ContactsListFragment extends Fragment {
    private ContactsViewModel mContactsViewModel;
    private UserInfoViewModel mUserModel;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}