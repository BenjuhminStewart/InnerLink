package edu.uw.tcss450.innerlink.ui.Contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentContactOutgoingRequestCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsRequestCardBinding;

/**
 * Allows the user to scroll through a list of pending outgoing contact requests.
 */
public class ContactsOutgoingRequestRecyclerViewAdapter
        extends RecyclerView.Adapter<ContactsOutgoingRequestRecyclerViewAdapter.ContactsOutgoingRequestViewHolder> {

    //Store all of the notifications to present
    private final List<String> mContactsRequests;

    // Store the parent fragment
    private final ContactsListFragment mParent;

    public ContactsOutgoingRequestRecyclerViewAdapter(List<String> contactsRequests, ContactsListFragment parent) {
        this.mContactsRequests = contactsRequests;
        Collections.sort(mContactsRequests, String.CASE_INSENSITIVE_ORDER);
        this.mParent = parent;
    }

    @NonNull
    @Override
    public ContactsOutgoingRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsOutgoingRequestViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_contact_outgoing_request_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsOutgoingRequestRecyclerViewAdapter.ContactsOutgoingRequestViewHolder holder, int position) {
        holder.setRequestModel(mContactsRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactsRequests.size();
    }

    public class ContactsOutgoingRequestViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactOutgoingRequestCardBinding binding;

        public ContactsOutgoingRequestViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactOutgoingRequestCardBinding.bind(view);
        }
        void setRequestModel(final String contactsModel) {
            binding.textUsername.setText(contactsModel);
            binding.cancelButton.setOnClickListener(view -> declineRequest(contactsModel, this));
        }
    }

    private void declineRequest(final String email, ContactsOutgoingRequestViewHolder view) {
        mContactsRequests.remove(email);
        notifyItemRemoved(view.getLayoutPosition());
        mParent.deleteContact(email);
    }
}
