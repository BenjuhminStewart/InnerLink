package edu.uw.tcss450.apaule.lab1myfirstapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.apaule.lab1myfirstapp.R;
import edu.uw.tcss450.apaule.lab1myfirstapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instantiate the Binding object and Inflate the layout for this fragment
        mBinding = FragmentFirstBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void processMessage() {
        final String message = mBinding.editMessage.getText().toString();
        Log.d("MESSAGE", message);

        FirstFragmentDirections.ActionFirstFragmentToSecondFragment directions =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(message);

        Navigation.findNavController(getView())
                .navigate(directions);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Use a Lambda expression to add the OnClickListener
        mBinding.buttonSend.setOnClickListener(button -> processMessage());
    }
}