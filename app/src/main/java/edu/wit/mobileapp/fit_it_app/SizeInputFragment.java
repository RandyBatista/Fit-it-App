package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SizeInputFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
// Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.specify_size_fragment, container, false);
        return rootView;
    }
}
