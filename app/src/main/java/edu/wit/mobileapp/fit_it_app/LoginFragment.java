package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    EditText emailET;
    EditText passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.log_in_fragment, container, false);

        emailET = rootView.findViewById(R.id.Email_ET);
        passwordET = rootView.findViewById(R.id.password_ET);
        Button registerBtn = rootView.findViewById(R.id.register_btn);
        Button guestBtn = rootView.findViewById(R.id.guest_btn);
        Button submitBtn = rootView.findViewById(R.id.submit_btn);

        registerBtn.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = new RegisterFragment();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        });

        guestBtn.setOnClickListener(v -> {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            Fragment fragment = new ChooseSizeInputFragment();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        });

        submitBtn.setOnClickListener(v -> {

//            FragmentManager fm = getActivity().getSupportFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//            //TODO:Login
//            //TODO:Check if logged in
//
//            //Continue if logged in
//            Fragment fragment = new Fragment();
//            transaction.replace(R.id.content, fragment);
//            transaction.commit();
        });
        return rootView;
    }
}
