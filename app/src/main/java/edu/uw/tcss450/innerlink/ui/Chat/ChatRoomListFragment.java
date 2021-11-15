package edu.uw.tcss450.innerlink.ui.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Chat screen where all of a user's active Chat Rooms are listed and displayed.
 */
public class ChatRoomListFragment extends Fragment {
    private ChatRoomListViewModel mChatRoomListModel;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_room_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatRoomListModel = new ViewModelProvider(getActivity()).get(ChatRoomListViewModel.class);
        // TODO: API ENDPOINT FOR GETTING LIST OF CHAT ROOMS BY EMAIL
        mChatRoomListModel.connectGet();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomListBinding binding = FragmentChatRoomListBinding.bind(getView());
        mChatRoomListModel.addChatRoomListObserver(getViewLifecycleOwner(), chatRoomList -> {
            if (!chatRoomList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRoomRecyclerViewAdapter(chatRoomList));
            }
        });
    }
}