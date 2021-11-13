package edu.uw.tcss450.innerlink.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomCardBinding;

/**
 * Allows the user to cycle/scroll through the list of active Chats on the Chat screen.
 */
public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ChatRoomViewHolder> {
    //Store all of the Chat Rooms (Lists of ChatMessages) to present
    private final List<ChatRoom> mChatRooms;

    public ChatRoomRecyclerViewAdapter(List<ChatRoom> chatRooms) {
        this.mChatRooms = chatRooms;
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
        holder.setChatRoom(mChatRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatRooms.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatRoomCardBinding binding;
        private ChatRoom mChatRoom;

        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatRoomCardBinding.bind(view);
        }

        void setChatRoom(final ChatRoom chatRoom) {
            mChatRoom = chatRoom;
            // TODO: APP LOGS OUT ON SELECTION, NAVIGATE TO CHAT ROOM
//            binding.buttonFullChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections.actionNavigationChatsToChatFragment(chat));
//            });
            // TODO: GET SENDER FROM CHATID...
            binding.textSender.setText(mChatRoom.getChatId());
        }
    }
}
