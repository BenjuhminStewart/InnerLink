package edu.uw.tcss450.innerlink.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatCardBinding;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder> {
    //Store all of the blogs to present
    private final List<Chat> mChats;

    public ChatRecyclerViewAdapter(List<Chat> items) {
        this.mChats = items;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private Chat mChat;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }

        void setChat(final Chat chat) {
            mChat = chat;
            // TODO: APP LOGS OUT ON SELECTION
//            binding.buttonFullChat.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections.actionNavigationChatsToChatFragment(chat));
//            });
            binding.textSender.setText(mChat.getSender());
            binding.textDate.setText(mChat.getDate());
            binding.textPreview.setText(mChat.getMessage());
        }
    }
}
