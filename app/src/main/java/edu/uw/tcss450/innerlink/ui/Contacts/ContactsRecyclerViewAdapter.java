package edu.uw.tcss450.innerlink.ui.Contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessage;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessageRecyclerViewAdapter;

public class ContactsRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder> {

    private final String mEmail;

    public ContactsRecyclerViewAdapter(String email) {
        mEmail = email;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewAdapter.ContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contacts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}