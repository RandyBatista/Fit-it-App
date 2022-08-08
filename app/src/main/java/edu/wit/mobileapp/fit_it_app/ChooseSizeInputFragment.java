package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ChooseSizeInputFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.get_measures_frag, container, false);

        Button measureBtn = rootView.findViewById(R.id.measurements_Btn);
        Button sizeBtn = rootView.findViewById(R.id.size_Btn);

        measureBtn.setOnClickListener(v ->{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = new MeasurementsFragment();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack("ChooseSize").commit();
        });

        sizeBtn.setOnClickListener(v ->{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = new SizeInputFragment();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack("ChooseSize").commit();
        });

        return rootView;
    }
}
