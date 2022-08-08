package edu.wit.mobileapp.fit_it_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.webview_fragment, container, false);
        TextView recommendedTV = rootView.findViewById(R.id.recommendation_TV);
        try {
            //Get bundle information
            Bundle bundle = this.getArguments();
            String url = "";
            String name = "";
            if (bundle != null) {
                url = bundle.get("URL").toString();
                name = bundle.get("NAME").toString();
            }

            //Open url for brand given in bundle
            WebView webview = rootView.findViewById(R.id.webView);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);

            //Get current logged user
            User u = User.getLoggedUser();
            if(u.selectedProfile.equals("None")) {
                return rootView;
            }
            //Get Profile from user data
            JSONObject profileJSON = u.sizeProfiles.getJSONObject(u.selectedProfile);
            Profile profile = new Profile();
            profile.loadJson(profileJSON);

            recommendedTV.setText("Recommended "+getGroupText(profile.gender, profile.ageGroup) + " Sizes:");

            //Get Recommended Sizes for user
            ArrayList<RecommendationItem> list = new ArrayList<>();
            getShirtSizeRecommendations(list, name, profile);
            if(list.isEmpty()){
                list.add(new RecommendationItem("All: ","None","No Recommendations Found"));
            }
            // create ListItemAdapter
            RecommendationAdapter adapter = new RecommendationAdapter(getContext(), list);

            // assign ListItemAdapter to ListView
            ListView listView = (ListView) rootView.findViewById(R.id.recommendation_LV);
            listView.setAdapter(adapter);
        }catch (Exception e){
            Log.v(null, e.toString());
        }
        return rootView;
    }

    public void getShirtSizeRecommendations(ArrayList<RecommendationItem> list, String brand, Profile p){

        ArrayList<RecommendationItem> recItems = ShirtSize.getRecommendations(brand, p);
        list.addAll(recItems);
    }

    public String getGroupText(String gender, String ageGroup){
        switch (gender){
            case "Female":
                switch (ageGroup){
                    case "Child (7-15)":
                        return "Girl's";
                    default:
                        return "Women's";
                }
            default:
                switch (ageGroup){
                    case "Child (7-15)":
                        return "Boy's";
                    default:
                        return "Men's";
                }
        }
    }

}
