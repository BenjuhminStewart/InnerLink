package edu.uw.tcss450.innerlink.ui.Notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatBinding;
import edu.uw.tcss450.innerlink.ui.Chat.ChatFragmentArgs;

public class NotificationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatFragmentArgs args = ChatFragmentArgs.fromBundle(getArguments());

        FragmentChatBinding binding = FragmentChatBinding.bind(requireView());

        binding.textSender.setText(args.getChat().getSender());
        binding.textPreview.setText(args.getChat().getMessage());
    }
}