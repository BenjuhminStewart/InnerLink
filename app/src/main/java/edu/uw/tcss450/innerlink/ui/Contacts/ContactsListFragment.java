package edu.uw.tcss450.innerlink.ui.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomViewModel;
import edu.uw.tcss450.innerlink.ui.Notification.NotificationGenerator;
import edu.uw.tcss450.innerlink.ui.Notification.NotificationRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListFragment extends Fragment {
    private ContactsViewModel mContactViewModel;

    private UserInfoViewModel mUserModel;

    private FragmentContactsListBinding binding;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        UserInfoViewModel mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactViewModel.setUserInfoViewModel(mUserModel);
        mContactViewModel.getContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactsListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactViewModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRootContacts.setAdapter(
                        new ContactsRecyclerViewAdapter(contactList));
            }
        });
    }
}