package edu.uw.tcss450.innerlink.ui.Chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Chat screen where all of a user's active Chats are listed and displayed.
 */
public class ChatListFragment extends Fragment {

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 1));
            ((RecyclerView) view).setAdapter(
                    new ChatRecyclerViewAdapter(ChatGenerator.getChatList()));
        }

        return view;
    }
}