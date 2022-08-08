package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {

    private String brandName = "";
    private String url = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.webview_fragment, container, false);
        TextView recommendedTV = rootView.findViewById(R.id.recommendation_TV);
        try {
            //Get bundle information
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                url = bundle.get("URL").toString();
                brandName = bundle.get("NAME").toString();
            }

            //Open url for brand given in bundle
            WebView webview = rootView.findViewById(R.id.webView);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);

            FirebaseUser user = MainActivity.mAuth.getCurrentUser();
            if (user != null) {
                FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String profiles = snapshot.child("sizeProfiles").getValue(String.class);
                                String selected = snapshot.child("selectedProfile").getValue(String.class);
                                String email = snapshot.child("selectedProfile").getValue(String.class);
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                User u = new User("None");
                                try {
                                    if(selected == null){
                                        selected = "None";
                                    }
                                    if (profiles == null || profiles.equals("")) {
                                        u = new User(uid);
                                    } else {
                                        u = new User(uid, email, selected, new JSONObject(profiles));
                                    }

                                }catch(Exception e){
                                    Log.v(null, e.toString());
                                }
                                if(u.selectedProfile.equals("None")) {
                                    try {
                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        Fragment fragment;
                                        fragment = new BrandsViewFragment();
                                        transaction.replace(R.id.content, fragment);
                                        transaction.commit();
                                    } catch (Exception e) {
                                        Log.v(null, e.toString());
                                    }
                                }else{
                                    try {
                                        //Get Profile from user data
                                        JSONObject profileJSON = u.sizeProfiles.getJSONObject(u.selectedProfile);
                                        Profile profile = new Profile();
                                        profile.loadJson(profileJSON);

                                        recommendedTV.setText("Recommended " + getGroupText(profile.gender, profile.ageGroup) + " Sizes:");

                                        //Get Recommended Sizes for user
                                        ArrayList<RecommendationItem> list = new ArrayList<>();
                                        getShirtSizeRecommendations(list, brandName, profile);
                                        if (list.isEmpty()) {
                                            list.add(new RecommendationItem("All: ", "", "No Recommendations Found"));
                                        }
                                        // create ListItemAdapter
                                        RecommendationAdapter adapter = new RecommendationAdapter(getContext(), list);

                                        // assign ListItemAdapter to ListView
                                        ListView listView = (ListView) rootView.findViewById(R.id.recommendation_LV);
                                        listView.setAdapter(adapter);
                                    }catch (Exception e){
                                        Log.v(null, e.toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.v(null, "error");
                            }
                        });

            } else {
                User u = User.getGuestUser();
                if(u.selectedProfile.equals("None")) {
                    try {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        Fragment fragment;
                        fragment = new BrandsViewFragment();
                        transaction.replace(R.id.content, fragment);
                        transaction.commit();
                    } catch (Exception e) {
                        Log.v(null, e.toString());
                    }
                }else{
                    //Get Profile from user data
                    JSONObject profileJSON = u.sizeProfiles.getJSONObject(u.selectedProfile);
                    Profile profile = new Profile();
                    profile.loadJson(profileJSON);

                    recommendedTV.setText("Recommended "+getGroupText(profile.gender, profile.ageGroup) + " Sizes:");

                    //Get Recommended Sizes for user
                    ArrayList<RecommendationItem> list = new ArrayList<>();
                    getShirtSizeRecommendations(list, brandName, profile);
                    if(list.isEmpty()){
                        list.add(new RecommendationItem("All: ","","No Recommendations Found"));
                    }
                    // create ListItemAdapter
                    RecommendationAdapter adapter = new RecommendationAdapter(getContext(), list);

                    // assign ListItemAdapter to ListView
                    ListView listView = (ListView) rootView.findViewById(R.id.recommendation_LV);
                    listView.setAdapter(adapter);
                }
            }


        }catch (Exception e){
            Log.v(null, e.toString());
        }
        return rootView;
    }

    //Gets shirt recommendation
    public void getShirtSizeRecommendations(ArrayList<RecommendationItem> list, String brand, Profile p){

        ArrayList<RecommendationItem> recItems = ShirtSize.getRecommendations(brand, p);
        list.addAll(recItems);
    }

    //Sets recommended text label to user's associated clothing group
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
