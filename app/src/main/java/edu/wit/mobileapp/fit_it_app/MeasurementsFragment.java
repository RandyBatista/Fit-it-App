package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.measurements_fragment, container, false);
        EditText bust_ET = rootView.findViewById(R.id.bust_ET);
        EditText waist_ET = rootView.findViewById(R.id.waist_ET);
        EditText hip_ET = rootView.findViewById(R.id.hip_ET);
        Spinner measurement_spinner = rootView.findViewById(R.id.measurementSpinner);
        Spinner groupSpinner = rootView.findViewById(R.id.groupSpinner);
        Spinner genderSpinner = rootView.findViewById(R.id.genderSpinner);
        Button submitBtn = rootView.findViewById(R.id.measurement_Submit_btn);

        List<String> list = new ArrayList<>();
        list.add("Imperial");
        list.add("Metric");

        ArrayAdapter<String> measurementAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurement_spinner.setAdapter(measurementAdapter);

        // Creating adapter for spinner
        ArrayAdapter<String> genderAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, SizeInputFragment.generateGenders());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> groupAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, SizeInputFragment.generateGroups());
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);

        measurement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String measurement = parent.getItemAtPosition(position).toString();
                String label = "";
                if(measurement.equals("Imperial")){
                    label = "(in)";
                }else{
                    label = "(cm)";
                }
                bust_ET.setHint("Enter Bust Size " + label);
                waist_ET.setHint("Enter Waist Size " + label);
                hip_ET.setHint("Enter Hip Size " + label);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        if(!User.getLoggedUser().selectedProfile.equals("None")){
            try{
                JSONObject profileJSON = User.getLoggedUser().sizeProfiles.getJSONObject(User.getLoggedUser().selectedProfile);
                Profile p = new Profile();
                p.loadJson(profileJSON);
                bust_ET.setText("" + p.bust);
                waist_ET.setText("" + p.waist);
                hip_ET.setText("" + p.hip);
            }catch (Exception e){
                Log.v(null, e.toString());
            }
        }
        submitBtn.setOnClickListener(v ->{
            String standard = measurement_spinner.getSelectedItem().toString();
            String selectedGroup = groupSpinner.getSelectedItem().toString();
            String selectedGender = genderSpinner.getSelectedItem().toString();
            double bust = getETDouble(bust_ET);
            double waist = getETDouble(waist_ET);
            double hip = getETDouble(hip_ET);
            Profile newProf = new Profile(standard, selectedGroup, selectedGender, bust,waist,hip);
            User u = User.getLoggedUser();
            try {
                if (u.sizeProfiles.length() == 0) {
                    u.sizeProfiles.put("Profile 1", newProf.toJson());
                    u.selectedProfile = "Profile 1";
                } else {
                    String selected = u.selectedProfile;
                    if(selected.equals("None")){
                        u.sizeProfiles.put("Profile " + (User.getLoggedUser().sizeProfiles.length() + 1), newProf.toJson());
                        u.selectedProfile = "Profile " + (User.getLoggedUser().sizeProfiles.length() + 1);
                    }else{
                        u.sizeProfiles.remove(selected);
                        u.sizeProfiles.put(selected, newProf.toJson());
                    }
                }
            }catch(Exception e){
                Log.v(null, e.toString());
            }


            User.setLoggedUser(u);
            Log.v(null, u.sizeProfiles.toString());
            if(!u.UID.equals("None")){
                FirebaseDatabase.getInstance("https://fit-it-app-eb283-default-rtdb.firebaseio.com/").getReference("users").
                        child(u.UID).child("sizeProfiles").setValue(u.sizeProfiles.toString()).
                        addOnCompleteListener(task1 -> {
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
                        });
            }else{
                try {
                    Log.v(null, "No User Found");
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    Fragment fragment;
                    fragment = new BrandsViewFragment();
                    transaction.replace(R.id.content, fragment);
                    transaction.commit();
                } catch (Exception e) {
                    Log.v(null, e.toString());
                }
            }
        });
        return rootView;
    }
    public double getETDouble(EditText et){
        if(et.getText().toString().equals(""))
            return 0;
        return Double.parseDouble(et.getText().toString());
    }
}