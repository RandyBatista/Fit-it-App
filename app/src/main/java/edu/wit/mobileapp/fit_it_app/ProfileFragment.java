package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileFragment extends Fragment {

    EditText usernameTV;
    EditText genderTV;
    EditText ageGroupTV;
    EditText bustTV;
    EditText waistTV;
    EditText hipsTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        ImageButton back_btn = rootView.findViewById(R.id.back_Btn);

        back_btn.setOnClickListener(v->{
            getActivity().onBackPressed();
        });

        FirebaseUser user = MainActivity.mAuth.getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference("users").child(user.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String profiles = snapshot.child("sizeProfiles").getValue(String.class);
                            String selected = snapshot.child("selectedProfile").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String uid = user.getUid();
                            User u;
                            try {

                                if (profiles == null || profiles.equals("{}")) {
                                    getActivity().onBackPressed();
                                } else {
                                    if (selected == null) {
                                        selected = "None";
                                    }
                                    if(selected.equals("None")){
                                        JSONObject profs = new JSONObject(profiles);
                                        if(profs.length() != 0){
                                            selected = profs.keys().next();
                                        }else{
                                            getActivity().onBackPressed();
                                        }
                                        u = new User(uid, email, selected, profs);
                                    }else{
                                        u = new User(uid, email, selected, new JSONObject(profiles));
                                    }
                                    setUpProfile(u, rootView);
                                }

                            } catch (Exception e) {
                                Log.v(null, e.toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }else{
            User u = User.getGuestUser();
            try{
                String selected = u.selectedProfile;
                if(selected.equals("None")){
                    if(u.sizeProfiles.length() != 0){
                        u.selectedProfile = u.sizeProfiles.keys().next();
                    }else{
                        getActivity().onBackPressed();
                    }
                }
                setUpProfile(u, rootView);
            }catch (Exception e){
                Log.v(null, e.toString());
            }
        }

        return rootView;
    }

    public void setUpProfile(User u, View rootview){
        Spinner profSpinner = rootview.findViewById(R.id.profilesSpinner);
        displayInformation(u, rootview);
        ArrayList<String> profiles = new ArrayList<>();
        Iterator<String> iterator = u.sizeProfiles.keys();
        while(iterator.hasNext()){
            profiles.add(iterator.next());
        }

        ArrayAdapter<String> profileAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, profiles);
        profileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profSpinner.setAdapter(profileAdapter);

        profSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedProfile = parent.getItemAtPosition(position).toString();
                u.selectedProfile = selectedProfile;
                displayInformation(u, rootview);
                if(!u.UID.equals("None")){
                    u.saveToDatabase();
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }

    public void displayInformation(User u, View rootView){
        usernameTV = rootView.findViewById(R.id.username_ET);
        genderTV = rootView.findViewById(R.id.gender_ET);
        ageGroupTV = rootView.findViewById(R.id.ageGroup_ET);
        bustTV = rootView.findViewById(R.id.bust_ET);
        waistTV = rootView.findViewById(R.id.waist_ET);
        hipsTV = rootView.findViewById(R.id.hips_ET);

        Button edit_btn = rootView.findViewById(R.id.edit_Btn);
        Button createProfile = rootView.findViewById(R.id.createProfile_btn);

        usernameTV.setText(u.email);
        try{
            JSONObject profileJSON = u.sizeProfiles.getJSONObject(u.selectedProfile);
            Profile p = new Profile();
            p.loadJson(profileJSON);

            bustTV.setText("" + p.bust);
            waistTV.setText("" + p.waist);
            hipsTV.setText("" + p.hip);
            genderTV.setText(p.gender);
            ageGroupTV.setText(p.ageGroup);
        }catch (Exception e){
            Log.v(null, e.toString());
        }

        createProfile.setOnClickListener(v->{
            u.selectedProfile = "None";
            if(!u.UID.equals("None")){
                u.saveToDatabase();
            }
            MainActivity.loadFragment(getActivity(), new ChooseSizeInputFragment(), "Profile");
        });

        edit_btn.setOnClickListener(v->{
            if(!u.UID.equals("None")){
                u.saveToDatabase();
            }
            MainActivity.loadFragment(getActivity(), new ChooseSizeInputFragment(), "Profile");
        });
    }

}