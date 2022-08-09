package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

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

        ImageButton back_btn = rootView.findViewById(R.id.back_Btn);

        back_btn.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        measureBtn.setOnClickListener(v ->{
            MainActivity.loadFragment(getActivity(), new MeasurementsFragment(), "ChooseSize");
        });

        sizeBtn.setOnClickListener(v ->{
            MainActivity.loadFragment(getActivity(), new SizeInputFragment(), "ChooseSize");
        });

        return rootView;
    }
}
