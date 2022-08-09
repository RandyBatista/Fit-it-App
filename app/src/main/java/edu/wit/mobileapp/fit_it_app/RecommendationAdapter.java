package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecommendationAdapter extends ArrayAdapter<RecommendationItem> {

    public RecommendationAdapter (Context context, ArrayList<RecommendationItem> recArrayList) {
        super(context, 0, recArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.recommended_size_item_layout, parent, false);
        }
        RecommendationItem recItem = getItem(position);
        TextView sizeType = listitemView.findViewById(R.id.type_TV);
        TextView sizeLabel = listitemView.findViewById(R.id.size_TV);
        sizeType.setText(recItem.type);
        sizeLabel.setText(recItem.size + recItem.description);
        return listitemView;
    }

}
