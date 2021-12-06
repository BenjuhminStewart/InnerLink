package edu.uw.tcss450.innerlink.ui.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentChatUserCardBinding;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsOutgoingRequestRecyclerViewAdapter;

public class ChatUsersRecyclerViewAdapter extends RecyclerView.Adapter<ChatUsersRecyclerViewAdapter.ChatUsersViewHolder> {
    //Store all of the Chat Rooms to present
    private final List<String> mUsers;


    public ChatUsersRecyclerViewAdapter(List<String> mUsers) {
        this.mUsers = mUsers;
        Collections.sort(mUsers, String.CASE_INSENSITIVE_ORDER);
    }

    @NonNull
    @Override
    public ChatUsersRecyclerViewAdapter.ChatUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatUsersRecyclerViewAdapter.ChatUsersViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_chat_user_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUsersRecyclerViewAdapter.ChatUsersViewHolder holder, int position) {
        holder.setChatUsers(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatUsersViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatUserCardBinding binding;

        public ChatUsersViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatUserCardBinding.bind(view);
        }

        void setChatUsers(final String email) {
            binding.textEmail.setText(email);
        }
    }
}
