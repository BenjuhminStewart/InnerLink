package edu.uw.tcss450.innerlink.ui.Contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
 * A fragment that displays the list of the users contacts.
 */
public class ContactsListFragment extends Fragment {
    private ContactsViewModel mContactViewModel;

    private UserInfoViewModel mUserModel;

    private FragmentContactsListBinding binding;

    ContactsOutgoingRequestRecyclerViewAdapter adapter;

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
        mContactViewModel.getOutgoingRequests();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactsListBinding.inflate(inflater);

        EditText emailInput = (EditText) binding.getRoot().findViewById(R.id.editTextPersonName);
        Button addFriendButton = (Button) binding.getRoot().findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                addContact(emailInput.getText().toString());
                emailInput.setText("");
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactViewModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRootContacts.setAdapter(
                        new ContactsRecyclerViewAdapter(contactList, this));
            }
        });
        mContactViewModel.addOutgoingRequestsListObserver(getViewLifecycleOwner(), contactListOutgoing -> {
            binding.listRootContactsOutgoingRequests.setAdapter(
                    new ContactsOutgoingRequestRecyclerViewAdapter(contactListOutgoing, this));
        });
    }

    public void deleteContact(final String email) {
        mContactViewModel.deleteContact(email);
    }

    public void addContact(final String email) {
        mContactViewModel.sendRequest(email);
    }
}