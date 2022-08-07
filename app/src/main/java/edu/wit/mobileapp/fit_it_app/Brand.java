package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.common.internal.ResourceUtils;

import java.util.ArrayList;

public class Brand {
    String name;
    String url;
    Drawable logo;
    public Brand(String name, String url, Drawable logo){
        this.name = name;
        this.url = url;
        this.logo = logo;
    }
    public static ArrayList<Brand> generateAllBrands(Context context){
        ArrayList<Brand> brands = new ArrayList<>();
        brands.add(new Brand("Nike", "https://www.nike.com/", ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_nike_4_logo_svg_vector, null)));
        return brands;
    }
}