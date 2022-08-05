package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

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
        Spinner groupSpinner = rootView.findViewById(R.id.groupSpinner);
        Spinner brandSpinner = rootView.findViewById(R.id.brandSpinner);
        shirtSpinner = rootView.findViewById(R.id.shirtSpinner);
        sweatShirtSpinner = rootView.findViewById(R.id.sweatShirtSpinner);
        shortsSpinner = rootView.findViewById(R.id.shortsSpinner);
        pantsSpinner = rootView.findViewById(R.id.pantsSpinner);
        shoesSpinner = rootView.findViewById(R.id.shoesSpinner);
        Button submit = rootView.findViewById(R.id.submitCountryStandard_btn);

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
            User.loggedUser.sizeProfiles.get("Profile 1").setShirtSize(shirtSize);
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

    public List<String> generateGenders(){
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        list.add("Other");
        return list;
    }
    public List<String> generateGroups(){
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
                                for(String s:ShirtSize.getUniNike().keySet()){
                                    list.add(s);
                                }
                            case "Female":
                                for(String s:ShirtSize.getWomenNike().keySet()){
                                    list.add(s);
                                }
                            case "Other":
                                for(String s:ShirtSize.getUniNike().keySet()){
                                    list.add(s);
                                }
                            default:
                                break;
                        }
                    case "Child (7-15)":
                        switch (gender){
                            case "Male":
                                for(String s:ShirtSize.getBoyNike().keySet()){
                                    list.add(s);
                                }
                            case "Female":
                                for(String s:ShirtSize.getGirlNike().keySet()){
                                    list.add(s);
                                }
                            case "Other":
                                for(String s:ShirtSize.getBoyNike().keySet()){
                                    list.add(s);
                                }
                            default:
                                break;
                        }
                    default:
                        break;
                }
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
    public List<String> generateShoes(String brand, String group, String gender){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        return list;
    }

}
