package edu.uw.tcss450.innerlink.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatCardBinding;

/**
 * Allows the user to cycle/scroll through the list of active Chats on the Chat screen.
 */
public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {
    //Store all of the blogs to present
    private final List<ChatMessage> mChatMessages;

    public ChatRoomRecyclerViewAdapter(List<ChatMessage> items) {
        this.mChatMessages = items;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        holder.setChat(mChatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatMessage mChatMessage;

        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        void setChat(final ChatMessage chatMessage) {
            mChatMessage = chatMessage;
            // TODO: APP LOGS OUT ON SELECTION, NAVIGATE TO CHAT ROOM
//            binding.buttonFullChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections.actionNavigationChatsToChatFragment(chat));
//            });
            binding.textSender.setText(mChatMessage.getSender());
            binding.textDate.setText(mChatMessage.getTimeStamp());
            binding.textPreview.setText(mChatMessage.getMessage());
        }
    }
}
