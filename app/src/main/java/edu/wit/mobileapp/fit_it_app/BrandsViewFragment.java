package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BrandsViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.brands_fragment, container, false);
        ImageButton prof_btn = rootView.findViewById(R.id.prof_Btn);
        ImageButton back_btn = rootView.findViewById(R.id.back_Btn);

        prof_btn.setOnClickListener(v->{
            MainActivity.loadFragment(getActivity(), new ProfileFragment(), "Brands");
        });

        back_btn.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        GridView brandsGV = rootView.findViewById(R.id.brands_GV);
        BrandCardAdapter adapter = new BrandCardAdapter(getContext(), Brand.generateAllBrands(getContext()));
        brandsGV.setAdapter(adapter);
        brandsGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Brand brand = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("URL", brand.url);
                bundle.putString("NAME", brand.name);
                Fragment fragment = new ShoppingFragment();
                fragment.setArguments(bundle);
                MainActivity.loadFragment(getActivity(), fragment, "Brands");

            }
        });
        return rootView;
    }
}
