package edu.uw.tcss450.innerlink.ui.Chat;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * Represents the Chat screen where all of a user's active Chat Rooms are listed and displayed.
 */
public class ChatRoomListFragment extends Fragment {
    private ChatRoomListViewModel mChatRoomListModel;
    private UserInfoViewModel mUserModel;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatRoomListModel = provider.get(ChatRoomListViewModel.class);
        mChatRoomListModel.connectGet(mUserModel.getmJwt());


        // TODO: Get first messages for all chat rooms HERE rather than in ChatRoomFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_room_list, container, false);

        FloatingActionButton newChatButton = (FloatingActionButton) view.findViewById(R.id.new_chat_button);
        newChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoom();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomListBinding binding = FragmentChatRoomListBinding.bind(getView());
        mChatRoomListModel.addChatRoomListObserver(getViewLifecycleOwner(), chatRoomList -> {
//            if (!chatRoomList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRoomRecyclerViewAdapter(chatRoomList));
//            }
        });
    }

    private void createRoom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Create Chat Room");
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
            mChatRoomListModel.createChatRoom(oldPass.getText().toString(), mUserModel.getmJwt());

            AlertDialog.Builder builderDecline = new AlertDialog.Builder(getContext());
            builderDecline.setTitle("Success!");
            builderDecline.setMessage("Chat room created.");
            builderDecline.setPositiveButton("Confirm", null);
            AlertDialog alertDialogDecline = builderDecline.create();
            alertDialogDecline.show();
        });
        builder.setNegativeButton(R.string.dialog_remove_cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}