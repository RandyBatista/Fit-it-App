package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BrandsViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.brands_fragment, container, false);
        GridView brandsGV = rootView.findViewById(R.id.brands_GV);
        BrandCardAdapter adapter = new BrandCardAdapter(getContext(), Brand.generateAllBrands(getContext()));
        brandsGV.setAdapter(adapter);
        brandsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Brand brand = adapter.getItem(position);
                try {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    Fragment fragment;
                    Bundle bundle = new Bundle();
                    bundle.putString("URL", brand.url);
                    bundle.putString("NAME", brand.name);
                    fragment = new ShoppingFragment();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.content, fragment);
                    transaction.addToBackStack("Brands").commit();
                } catch (Exception e) {
                    Log.v(null, e.toString());
                }

            }
        });
        return rootView;
    }
}
