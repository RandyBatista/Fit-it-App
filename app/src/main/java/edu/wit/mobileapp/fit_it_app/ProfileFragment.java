package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

    EditText username_ET;
    EditText gender_ET;
    EditText ageGroup_ET;
    EditText bust_ET;
    EditText waist_ET;
    EditText hips_ET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        username_ET = rootView.findViewById(R.id.username_ET);
        gender_ET = rootView.findViewById(R.id.gender_ET);
        ageGroup_ET = rootView.findViewById(R.id.ageGroup_ET);
        bust_ET = rootView.findViewById(R.id.gender_ET);
        waist_ET = rootView.findViewById(R.id.gender_ET);
        hips_ET = rootView.findViewById(R.id.gender_ET);

        return rootView;
    }
}