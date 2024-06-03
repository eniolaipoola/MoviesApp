package com.tei.moviesapp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tei.moviesapp.R;

public class AppLoadingViewFragment extends DialogFragment {

    public static AppLoadingViewFragment newInstance(String progressMessage) {
        AppLoadingViewFragment fragment = new AppLoadingViewFragment();
        Bundle args = new Bundle();
        args.putString("progressMessage", progressMessage);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading_view, container, false);
        TextView progressTextView = rootView.findViewById(R.id.progressTextView);

        if (getArguments() != null) {
            String progressMessage = getArguments().getString("progressMessage");
            progressTextView.setText(progressMessage);
        }

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
