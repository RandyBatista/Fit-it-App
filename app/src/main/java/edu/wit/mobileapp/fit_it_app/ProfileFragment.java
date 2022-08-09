package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

    EditText usernameTV;
    EditText genderTV;
    EditText ageGroupTV;
    EditText bustTV;
    EditText waistTV;
    EditText hipsTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        usernameTV = rootView.findViewById(R.id.username_TV);
        genderTV = rootView.findViewById(R.id.gender_TV);
        ageGroupTV = rootView.findViewById(R.id.ageGroup_TV);
        bustTV = rootView.findViewById(R.id.bust_TV);
        waistTV = rootView.findViewById(R.id.waist_TV);
        hipsTV = rootView.findViewById(R.id.hips_TV);

        Button back_btn = rootView.findViewById(R.id.back_Btn);
        Button edit_btn = rootView.findViewById(R.id.edit_Btn);
        Button createProfile = rootView.findViewById(R.id.createProfile_Btn);


        return rootView;
    }
}