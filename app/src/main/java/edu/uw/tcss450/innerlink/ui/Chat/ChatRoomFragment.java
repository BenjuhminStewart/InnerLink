package edu.uw.tcss450.innerlink.ui.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * Represents a Chat Room between the user and one or more other members.
 * Displays the list of messages.
 */
public class ChatRoomFragment extends Fragment {
    private ChatSendViewModel mSendModel;
    private ChatRoomViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    public ChatRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatRoomViewModel.class);
        mSendModel = provider.get(ChatSendViewModel.class);

        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        int chatId = args.getChatRoom().getChatId();
        mChatModel.getFirstMessages(chatId, mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        int chatId = args.getChatRoom().getChatId();

        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatMessageRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(chatId),
                mUserModel.getEmail()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(chatId, mUserModel.getmJwt());
        });

        mChatModel.addMessageObserver(chatId, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        // Send button was clicked. Send the message via the SendViewModel
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(
                    chatId,
                    mUserModel.getmJwt(),
                    binding.editMessage.getText().toString());
        });

        // When we get the response back from the server, clear the editText
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editMessage.setText(""));
    }
}