package edu.uw.tcss450.innerlink.ui.Chat;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatManageBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentChatUserCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter;

/**
 * A fragment that allows the user to manage a chat room. The user can add a new user to the chat room,
 * delete a user to the chat room, and view the list of users by email that are in the chat room.
 */
public class ChatManageFragment extends Fragment {
    private ChatManageViewModel mChatManageViewModel;
    private UserInfoViewModel mUserModel;
    FragmentChatManageBinding binding;
    ChatManageFragmentArgs args;
    public ChatManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatManageViewModel = provider.get(ChatManageViewModel.class);

        args = ChatManageFragmentArgs.fromBundle(getArguments());
        int chatId = args.getChatid();
        mChatManageViewModel.getUsers(chatId, mUserModel.getmJwt());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatManageBinding binding = FragmentChatManageBinding.bind(getView());
        mChatManageViewModel.addUsersListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRootUsers.setAdapter(
                        new ChatUsersRecyclerViewAdapter(contactList));
            }
        });

        EditText emailInput = (EditText) binding.getRoot().findViewById(R.id.editTextUser);
        Button addUserButton = (Button) binding.getRoot().findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                mChatManageViewModel.addUser(args.getChatid(), emailInput.getText().toString(), mUserModel.getmJwt());
                emailInput.setText("");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatManageBinding.inflate(inflater);
        return binding.getRoot();
    }
}