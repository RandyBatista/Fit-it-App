package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WebViewAdapter extends ArrayAdapter<ShoppingFragment.ListItem> {
    private LayoutInflater mInflater;

    public WebViewAdapter(Context context, int rid, List<ShoppingFragment.ListItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingFragment.ListItem item = (ShoppingFragment.ListItem) getItem(position);

        View view = mInflater.inflate(R.layout.web_view_adapter, null);

        TextView shirt = (TextView) view.findViewById(R.id.shirtSize);
        shirt.setText(item.shirt);

//        TextView sweatshirt = (TextView) view.findViewById(R.id.sweatshirtSize);
//        sweatshirt.setText(item.sweatshirt);
//
//        TextView shorts = (TextView) view.findViewById(R.id.shortsSize);
//        shorts.setText(item.shorts);
//
//        TextView pants = (TextView) view.findViewById(R.id.pantsSize);
//        pants.setText(item.pants);
//
//        TextView shoes = (TextView) view.findViewById(R.id.shoesSize);
//        shoes.setText(item.shoes);

        return view;
    }
}