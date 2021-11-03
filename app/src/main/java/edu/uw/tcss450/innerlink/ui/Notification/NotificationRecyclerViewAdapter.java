package edu.uw.tcss450.innerlink.ui.Notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentNotificationCardBinding;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder> {
    //Store all of the blogs to present
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
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List * of rows in the Blog Recycler View.
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
//            binding.buttonFullNotification.setOnClickListener(view -> {
//                Navigation.findNavController(mView).navigate(
//                        ChatListFragmentDirections.actionNavigationChatsToChatFragment(notification));
//            });
            binding.textNotificationType.setText(mNotification.getType());
            binding.textDate.setText(mNotification.getDate());
            binding.textNotificationMessage.setText(mNotification.getMessage());
        }
    }
}
