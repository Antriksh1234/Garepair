package com.atandroidlabs.garepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AllDoneFragment extends Fragment {
    private TextView helloUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_done, container, false);
        helloUser = view.findViewById(R.id.hello_user);
        setUpTextView();
        return view;
    }

    private void setUpTextView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null  || user.getDisplayName() == null) {
            helloUser.setText("Hello User");
        } else {
            String name = "Welcome " + user.getDisplayName().substring(0, user.getDisplayName().indexOf(" "));
            helloUser.setText(name);
        }
    }
}
