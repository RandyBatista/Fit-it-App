package edu.wit.mobileapp.fit_it_app;

import org.json.JSONObject;
import android.util.Log;

public class Profile {

    String standard;
    String ageGroup;
    String gender;
    double bust;
    double waist;
    double hip;

    public Profile(){
        this("Metric", "N/A", "N/A", 0,0,0);
    }
    public Profile(String standard, String ageGroup, String gender){
        this(standard, ageGroup, gender, 0,0,0);
    }

    public Profile(String standard, String ageGroup, String gender, double bust, double waist, double hip){
        this.standard = standard;
        this.ageGroup = ageGroup;
        this.gender = gender;
        this.bust = bust;
        this.waist = waist;
        this.hip = hip;
    }

    public void setShirtSize(ShirtSize size){
        if (size == null){
            bust = 0.0;
            waist = 0.0;
            hip = 0.0;
            return;
        }
        if(standard.equals("Imperial")) {
            size = size.toInches();
        }
        bust = size.bust.getAverage();
        waist = size.waist.getAverage();
        hip = size.hip.getAverage();
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("standard", standard);
            obj.put("ageGroup", ageGroup);
            obj.put("gender", gender);
            obj.put("bust", bust);
            obj.put("waist", waist);
            obj.put("hip", hip);
        }catch(Exception e){
            Log.v(null, e.toString());
        }
        return obj;
    }

    public void loadJson(JSONObject obj){
        try {
            standard = obj.get("standard").toString();
            ageGroup = obj.get("ageGroup").toString();
            gender = obj.get("gender").toString();
            bust = obj.getDouble("bust");
            waist = obj.getDouble("waist");
            hip = obj.getDouble("hip");
        }catch(Exception e){
            Log.v(null, e.toString());
        }
    }

}
