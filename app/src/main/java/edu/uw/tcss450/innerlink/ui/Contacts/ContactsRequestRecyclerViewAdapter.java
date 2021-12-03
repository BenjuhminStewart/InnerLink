package edu.uw.tcss450.innerlink.ui.Contacts;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsRequestCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentHomeBinding;
import edu.uw.tcss450.innerlink.ui.Home.HomeFragment;

public class ContactsRequestRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactsRequestRecyclerViewAdapter.ContactsRequestViewHolder> {

    //Store all of the notifications to present
    private final List<String> mContactsRequests;

    // Store the parent fragment
    private final ContactsViewModel mParent;

    public ContactsRequestRecyclerViewAdapter(List<String> contactsRequests, ContactsViewModel parent) {
        this.mContactsRequests = contactsRequests;
        this.mParent = parent;
    }

    @NonNull
    @Override
    public ContactsRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRequestViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_contacts_request_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRequestRecyclerViewAdapter.ContactsRequestViewHolder holder, int position) {
        holder.setRequestModel(mContactsRequests.get(position));
    }

    @Override
    public int getItemCount() {
            return mContactsRequests.size();
    }

    public class ContactsRequestViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactsRequestCardBinding binding;

        public ContactsRequestViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactsRequestCardBinding.bind(view);
        }
        void setRequestModel(final String contactsModel) {
            binding.textUsername.setText(contactsModel);
            binding.acceptButton.setOnClickListener(view -> acceptRequest(contactsModel, this));
            binding.cancelButton.setOnClickListener(view -> declineRequest(contactsModel, this));
        }
    }

    public void acceptRequest(final String email, ContactsRequestViewHolder view) {
        mContactsRequests.remove(email);
        notifyItemRemoved(view.getLayoutPosition());
        mParent.acceptContact(email);
    }


    private void declineRequest(final String email, ContactsRequestViewHolder view) {
        mContactsRequests.remove(email);
        notifyItemRemoved(view.getLayoutPosition());
        mParent.deleteContact(email);
    }
}
