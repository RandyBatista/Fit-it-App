package edu.wit.mobileapp.fit_it_app;

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
}
