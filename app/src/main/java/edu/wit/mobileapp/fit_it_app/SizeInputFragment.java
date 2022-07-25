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

public class SizeInputFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
// Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.log_in_fragment, container, false);
        Button registerBtn = rootView.findViewById(R.id.register_btn);
        Button guestBtn = rootView.findViewById(R.id.guest_btn);
        Button submitBtn = rootView.findViewById(R.id.submit_btn);
        return rootView;
    }
}
