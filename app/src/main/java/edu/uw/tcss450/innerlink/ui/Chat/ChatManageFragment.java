package edu.uw.tcss450.innerlink.ui.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatManageBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentChatUserCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.auth.signin.SignInFragmentDirections;

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
                Navigation.findNavController(getView()).navigate(
                        ChatManageFragmentDirections.actionChatManageFragmentSelf(args.getChatid()));
            }
        });

        FloatingActionButton editChatName = (FloatingActionButton) view.findViewById(R.id.edit_chat_name_button);
        editChatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName();
            }
        });


        FloatingActionButton leaveButton = (FloatingActionButton) binding.getRoot().findViewById(R.id.leave_chat_button);
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Leave Chat");
                    builder.setMessage("Are you sure you want to leave this chat room?");
                    builder.setPositiveButton(R.string.dialog_remove_confirm, (dialog, which) -> {
                        mChatManageViewModel.leaveChat(args.getChatid(), mUserModel.getmJwt());
                        AlertDialog.Builder builderLeft = new AlertDialog.Builder(getContext());
                        builderLeft.setTitle("Success!");
                        builderLeft.setMessage("You have left the chat room.");
                        builderLeft.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Navigation.findNavController(getView()).navigate(
                                        ChatManageFragmentDirections.actionChatManageFragmentToNavigationChats());
                            }
                        });
                        AlertDialog alertDialogLeft = builderLeft.create();
                        alertDialogLeft.show();
                    });
                    builder.setNegativeButton(R.string.dialog_remove_cancel, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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

    private void editName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Chat Room Name");
        final EditText oldPass = new EditText(this.getActivity());
        final TextView oldPassText = new TextView(this.getActivity());
        oldPassText.setText("       Please name your new chat room:");
        oldPass.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        oldPassText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        LinearLayout layout = new LinearLayout(new ContextThemeWrapper(getContext(), R.style.DarkAppTheme));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(oldPassText);
        layout.addView(oldPass);
        builder.setView(layout);
        builder.setPositiveButton(R.string.dialog_remove_confirm, (dialog, which) -> {
            mChatManageViewModel.changeChatName(args.getChatid(), oldPass.getText().toString(), mUserModel.getmJwt());
        });
        builder.setNegativeButton(R.string.dialog_remove_cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}