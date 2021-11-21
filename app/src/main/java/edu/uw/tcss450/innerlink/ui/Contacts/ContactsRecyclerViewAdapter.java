package edu.uw.tcss450.innerlink.ui.Contacts;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentNotificationCardBinding;
import edu.uw.tcss450.innerlink.ui.Notification.Notification;
import edu.uw.tcss450.innerlink.ui.Notification.NotificationRecyclerViewAdapter;

/**
 * Allows the user to cycle/scroll through the list of active Notifications on the Notification screen.
 */
public class ContactsRecyclerViewAdapter extends
        RecyclerView.Adapter<edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter.
                ContactsViewHolder> {
    //Store all of the notifications to present
    private final List<ContactsModel> mContactsModel;

    public ContactsRecyclerViewAdapter(List<ContactsModel> items) {
        this.mContactsModel = items;
    }
    // TODO: Navigate to a contacts card
    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewAdapter.ContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contacts, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ContactsViewHolder holder, int position) {
        holder.setContactsModel(mContactsModel.get(position));
        // Click anywhere on the card to navigate to the notification location
        holder.itemView.setOnClickListener(v -> {

            Log.e("CARD CLICKED", "You clicked contact number " + mContactsModel.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mContactsModel.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Notification Recycler View.
     */
    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactCardBinding binding;
        private ContactsModel mContactsModel;

        public ContactsViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactCardBinding.bind(view);
        }

        void setContactsModel(final ContactsModel contactsModel) {
            mContactsModel = contactsModel;
            binding.textEmail.setText(mContactsModel.getmEmail());
            binding.textFirstName.setText(mContactsModel.getmFirstName());
            binding.textUsername.setText(mContactsModel.getmUsername());
            binding.textLastName.setText(mContactsModel.getmLastName());
        }
    }
}