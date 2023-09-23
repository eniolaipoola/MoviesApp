package com.ehnyn.moviesapp.fragments;

import android.os.Bundle;
import androidx.core.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ehnyn.moviesapp.R;

public class AppErrorViewFragment extends DialogFragment {

    public static AppErrorViewFragment newInstance(String errorMessage) {
        AppErrorViewFragment fragment = new AppErrorViewFragment();
        Bundle args = new Bundle();
        args.putString("message", errorMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_error_page, container, false);

        String message = null;
        if (getArguments() != null) {
            message = getArguments().getString("message");
        }
        TextView messageTextView = rootView.findViewById(R.id.errorMessage);
        messageTextView.setText(message);

        TextView retryTextView = rootView.findViewById(R.id.retryText);
        retryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
