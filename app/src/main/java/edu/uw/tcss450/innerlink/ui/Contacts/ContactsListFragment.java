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
 * Represents the Notifications screen where all of a user's active Notifications are listed and displayed.
 */
public class ContactsListFragment extends Fragment {
    private ContactsViewModel mContactViewModel;
    private UserInfoViewModel mUserModel;

    private ContactsViewModel mModel;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Returns an existing instance of the ViewModel if it exists, otherwise creates
        // a new one.
//        ViewModelProvider provider = new ViewModelProvider(getActivity());
//        mUserModel = provider.get(UserInfoViewModel.class);
//        mContactViewModel = provider.get(ContactsViewModel.class);
//        mContactViewModel.getContactsListByEmail(mUserModel.getEmail());

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        UserInfoViewModel mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactViewModel.getContactsListByEmail(mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_contacts_list, container, false);
//
//        rootView.setTag("RecyclerViewFragment");
//        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.list_root_contacts);
//
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler.setLayoutManager(layoutManager);
//        // TODO: "E/Populate chatIds list with actual chatId values that the user has.
//        ContactsRecyclerViewAdapter adapter =
//                new ContactsRecyclerViewAdapter(mContactViewModel.getContactsListByEmail(mUserModel.getEmail()));
//        recycler.setAdapter(adapter);
//
//        return rootView;
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactsListBinding binding = FragmentContactsListBinding.bind(getView());

//        mContactViewModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
//            //if (!contactList.isEmpty()) {
//                binding.listRootContacts.setAdapter(
//                        new ContactsRecyclerViewAdapter(contactList));
//            //}
//        });
    }
}