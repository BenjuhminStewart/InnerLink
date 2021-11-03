package edu.uw.tcss450.innerlink.ui.Notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.ui.Chat.ChatGenerator;
import edu.uw.tcss450.innerlink.ui.Chat.ChatRecyclerViewAdapter;

public class NotificationListFragment extends Fragment {

    public NotificationListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 1));
            ((RecyclerView) view).setAdapter(
                    new NotificationRecyclerViewAdapter(NotificationGenerator.getNotificationList()));
        }

        return view;
    }
}