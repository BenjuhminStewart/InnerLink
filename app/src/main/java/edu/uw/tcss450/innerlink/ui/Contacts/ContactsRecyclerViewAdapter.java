package edu.uw.tcss450.innerlink.ui.Contacts;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentNotificationCardBinding;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRoomListFragmentDirections;
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

    //Store the expanded state for each List item, true -> expanded, false -> not
    private final Map<ContactsModel, Boolean> mExpandedFlags;

    public ContactsRecyclerViewAdapter(List<ContactsModel> items) {
        this.mContactsModel = items;
        mExpandedFlags = mContactsModel.stream()
                .collect(Collectors.toMap(Function.identity(), contact -> false));
    }

    // TODO: Navigate to a contacts card
    @NonNull
    @Override
    public ContactsRecyclerViewAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewAdapter.ContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ContactsViewHolder holder, int position) {
        holder.setContactsModel(mContactsModel.get(position));
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
            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }

        void setContactsModel(final ContactsModel contactsModel) {
            mContactsModel = contactsModel;
            binding.textUsername.setText(mContactsModel.getmUsername());
            binding.textEmailCard.setText(mContactsModel.getmEmail());
            binding.textFirstnameCard.setText(mContactsModel.getmFirstName());
            binding.textLastnameCard.setText(mContactsModel.getmLastName());
        }

        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mContactsModel, !mExpandedFlags.get(mContactsModel));
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (mExpandedFlags.get(mContactsModel)) {
                binding.textEmailCard.setVisibility(View.VISIBLE);
                binding.textFirstnameCard.setVisibility(View.VISIBLE);
                binding.textLastnameCard.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textEmailCard.setVisibility(View.GONE);
                binding.textFirstnameCard.setVisibility(View.GONE);
                binding.textLastnameCard.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
    }
}