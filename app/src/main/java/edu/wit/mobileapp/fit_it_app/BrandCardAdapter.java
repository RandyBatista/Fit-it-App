package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BrandCardAdapter extends ArrayAdapter<Brand> {
    public BrandCardAdapter (Context context, ArrayList<Brand> brandArrayList) {
        super(context, 0, brandArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.brand_card_layout, parent, false);
        }
        Brand brand = getItem(position);
        TextView brandName = listitemView.findViewById(R.id.brandName_TV);
        ImageView logoIV = listitemView.findViewById(R.id.brandLogo_IV);
        brandName.setText(brand.name);
        logoIV.setImageDrawable(brand.logo);
        return listitemView;
    }

}
