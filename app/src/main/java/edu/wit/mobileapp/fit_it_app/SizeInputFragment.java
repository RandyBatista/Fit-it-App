package edu.wit.mobileapp.fit_it_app;

import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SizeInputFragment extends Fragment{

    Spinner shirtSpinner;
    Spinner sweatShirtSpinner;
    Spinner shortsSpinner;
    Spinner pantsSpinner;
    Spinner shoesSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.specify_size_fragment, container, false);

        Spinner pickCountrySpinner = rootView.findViewById(R.id.pickCountrySpinner);
        Spinner genderSpinner = rootView.findViewById(R.id.genderSpinner);
        Spinner groupSpinner = rootView.findViewById(R.id.ageGroupSpinner);
        Spinner brandSpinner = rootView.findViewById(R.id.brandSpinner);
        shirtSpinner = rootView.findViewById(R.id.shirtSpinner);
        sweatShirtSpinner = rootView.findViewById(R.id.sweatShirtSpinner);
        shortsSpinner = rootView.findViewById(R.id.shortsSpinner);
        pantsSpinner = rootView.findViewById(R.id.pantsSpinner);
        shoesSpinner = rootView.findViewById(R.id.shoesSpinner);
        Button submit = rootView.findViewById(R.id.submitSizes_btn);

        // Creating adapter for spinner
        ArrayAdapter<String> countryAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateCountries());
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickCountrySpinner.setAdapter(countryAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> genderAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateGenders());
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> groupAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateGroups());
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateBrands());
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedGender = parent.getItemAtPosition(position).toString();
                String selectedGroup = groupSpinner.getSelectedItem().toString();
                String selectedBrand = brandSpinner.getSelectedItem().toString();
                refreshSizeSpinners(selectedBrand, selectedGroup, selectedGender);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedGender = genderSpinner.getSelectedItem().toString();
                String selectedGroup = parent.getItemAtPosition(position).toString();
                String selectedBrand = brandSpinner.getSelectedItem().toString();
                refreshSizeSpinners(selectedBrand, selectedGroup, selectedGender);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedGender = genderSpinner.getSelectedItem().toString();
                String selectedGroup = groupSpinner.getSelectedItem().toString();
                String selectedBrand = parent.getItemAtPosition(position).toString();
                refreshSizeSpinners(selectedBrand, selectedGroup, selectedGender);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        submit.setOnClickListener(v -> {
            String selectedCountry = pickCountrySpinner.getSelectedItem().toString();
            String selectedGender = genderSpinner.getSelectedItem().toString();
            String selectedGroup = groupSpinner.getSelectedItem().toString();
            String selectedBrand = brandSpinner.getSelectedItem().toString();
            String selectedShirtSize = shirtSpinner.getSelectedItem().toString();
            ShirtSize shirtSize = ShirtSize.getShirtSize(selectedBrand, selectedShirtSize, selectedGender, selectedGroup);
            String standard;
            if(selectedCountry.toLowerCase().contains("metric")){
                standard = "Metric";
            }else{
                standard = "Imperial";
            }
            Profile newProf = new Profile(standard, selectedGroup, selectedGender, 0,0,0);
            newProf.setShirtSize(shirtSize);
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

    private void refreshSizeSpinners(String brand, String group, String gender){
        // Creating adapter for spinner
        ArrayAdapter<String> shirtAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateShirts(brand, group, gender));
        shirtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shirtSpinner.setAdapter(shirtAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> sweatShirtAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateSweatShirts(brand, group, gender));
        sweatShirtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sweatShirtSpinner.setAdapter(sweatShirtAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> shortAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateShorts(brand, group, gender));
        shortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shortsSpinner.setAdapter(shortAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> pantsAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generatePants(brand, group, gender));
        pantsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pantsSpinner.setAdapter(pantsAdapter);
        // Creating adapter for spinner
        ArrayAdapter<String> shoeAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, generateShoes(brand, group, gender));
        shoeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shoesSpinner.setAdapter(shoeAdapter);
    }

    private List<String> generateCountries(){
        List<String> list = new ArrayList<>();
        list.add("United States (Imperial)");
        list.add("United Kingdom (Metric)");
        return list;
    }

    private List<String> generateBrands(){
        List<String> list = new ArrayList<>();
        list.add("Nike");
        return list;
    }

    public static List<String> generateGenders(){
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        list.add("Other");
        return list;
    }
    public static List<String> generateGroups(){
        List<String> list = new ArrayList<>();
        list.add("Adult (16+)");
        list.add("Child (7-15)");
        return list;
    }
    public List<String> generateShirts(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        switch (brand){
            case "Nike":
                switch(group){
                    case "Adult (16+)":
                        switch (gender){
                            case "Male":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getUniNike());break;
                            case "Female":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getWomenNike());break;
                            case "Other":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getUniNike());break;
                            default:
                                break;
                        }break;
                    case "Child (7-15)":
                        switch (gender){
                            case "Male":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getBoyNike());break;
                            case "Female":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getGirlNike());break;
                            case "Other":
                                list = ShirtSize.getShirtSizeKeySet(ShirtSize.getBoyNike());break;
                            default:
                                break;
                        }break;
                    default:
                        break;
                }break;
            default:
                break;
        }
        return list;
    }
    public List<String> generateSweatShirts(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        return list;
    }
    public List<String> generateShorts(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        return list;
    }
    public List<String> generatePants(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        return list;
    }
    public List<String> generateShoes(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        return list;
    }

}
