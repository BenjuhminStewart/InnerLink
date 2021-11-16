package edu.uw.tcss450.innerlink.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomCardBinding;

/**
 * Allows the user to cycle/scroll through the list of active Chat Rooms on the Chat screen.
 */
public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {
    //Store all of the Chat Rooms to present
    private final List<Integer> mChatIds;

    public ChatRoomRecyclerViewAdapter(List<Integer> chatIds) {
        mChatIds = chatIds;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.fragment_chat_room_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        holder.setChatRoom(mChatIds.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatIds.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatRoomCardBinding binding;
        private int mChatId;

        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatRoomCardBinding.bind(view);
        }

        void setChatRoom(final int chatId) {
            mChatId = chatId;
            // TODO: App logs out on selection when navigating to ChatRoomFragment - onSupportUpNav in MainActivity
//            binding.buttonFullChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatRoomListFragmentDirections.actionNavigationChatsToChatFragment(chatRoom));
//            });
            // TODO: Get sender from chatID
            binding.textSender.setText("sender xyz");
        }
    }
}
