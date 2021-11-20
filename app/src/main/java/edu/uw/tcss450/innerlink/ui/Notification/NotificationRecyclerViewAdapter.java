package edu.uw.tcss450.innerlink.ui.Notification;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentNotificationCardBinding;

/**
 * Allows the user to cycle/scroll through the list of active Notifications on the Notification screen.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder> {
    //Store all of the notifications to present
    private final List<Notification> mNotifications;

    public NotificationRecyclerViewAdapter(List<Notification> items) {
        this.mNotifications = items;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_notification_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.setNotification(mNotifications.get(position));

        // Click anywhere on the card to navigate to the notification location
        holder.itemView.setOnClickListener(v -> {
            // TODO: Navigate to notification location

            // Test onClick
            Log.e("CARD CLICKED", "You clicked card number " + mNotifications.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Notification Recycler View.
     */
    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentNotificationCardBinding binding;
        private Notification mNotification;

        public NotificationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentNotificationCardBinding.bind(view);
        }

        void setNotification(final Notification notification) {
            mNotification = notification;
            binding.textNotificationType.setText(mNotification.getType());
            binding.textDate.setText(mNotification.getDate());
            binding.textNotificationMessage.setText(mNotification.getMessage());
        }
    }
}
