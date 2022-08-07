package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MeasurementsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.measurements_fragment, container, false);

        Button submitBtn = rootView.findViewById(R.id.button);

        submitBtn.setOnClickListener(v ->{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = new BrandsViewFragment();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        });

        // Code for brand buttons here

        return rootView;
    }
}