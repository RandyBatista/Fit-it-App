package edu.wit.mobileapp.fit_it_app;

import org.json.JSONObject;
import android.util.Log;

public class Profile {

    String standard;
    double bust;
    double waist;
    double hip;

    public Profile(){
        this("Metric", 0,0,0);
    }

    public Profile(String standard,double bust, double waist, double hip){
        this.standard = standard;
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
        if(standard.equals("Imperial")){
            bust = size.bust.toInches().getAverage();
            waist = size.waist.toInches().getAverage();
            hip = size.waist.toInches().getAverage();
        }else{
            bust = size.bust.getAverage();
            waist = size.waist.getAverage();
            hip = size.waist.getAverage();
        }
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("standard", standard);
            obj.put("bust", bust);
            obj.put("waist", waist);
            obj.put("hip", hip);
        }catch(Exception e){
            Log.v(null, e.toString());
        }
        return obj;
    }

}
