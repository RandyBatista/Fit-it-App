package edu.wit.mobileapp.fit_it_app;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShirtSize {

    Size bust;
    Size waist;
    Size hip;

    public ShirtSize(Size bust, Size waist, Size hip){
        this.bust = bust;
        this.waist = waist;
        this.hip = hip;
    }

    public ShirtSize toInches(){
        ShirtSize size = this;
        size.bust = bust.toInches();
        size.waist = waist.toInches();
        size.hip = hip.toInches();
        return size;
    }

    public static ShirtSize getShirtSize(String brand, String sizeLabel, String gender, String group){
        switch (brand){
            case "Nike":
                switch(group){
                    case "Adult (16+)":
                        switch (gender){
                            case "Male":
                                return ShirtSize.getUniNike().get(sizeLabel);
                            case "Female":
                                return ShirtSize.getWomenNike().get(sizeLabel);
                            default:
                                return ShirtSize.getUniNike().get(sizeLabel);
                        }
                    case "Child (7-15)":
                        switch (gender){
                            case "Male":
                                return ShirtSize.getBoyNike().get(sizeLabel);
                            case "Female":
                                return ShirtSize.getGirlNike().get(sizeLabel);
                            default:
                                return ShirtSize.getBoyNike().get(sizeLabel);
                        }
                    default:
                        break;
                }
        }
        return null;
    }

    public static HashMap<String, ShirtSize> getShirtSizes(String brand, String gender, String group){
        switch (brand){
            case "Nike":
                switch(group){
                    case "Adult (16+)":
                        switch (gender){
                            case "Male":
                                return ShirtSize.getUniNike();
                            case "Female":
                                return ShirtSize.getWomenNike();
                            default:
                                return ShirtSize.getUniNike();
                        }
                    case "Child (7-15)":
                        switch (gender){
                            case "Male":
                                return ShirtSize.getBoyNike();
                            case "Female":
                                return ShirtSize.getGirlNike();
                            default:
                                return ShirtSize.getBoyNike();
                        }
                    default:
                        break;
                }
        }
        return null;
    }

    public static HashMap<String, ShirtSize> getUniNike(){
        HashMap<String, ShirtSize> sizes = new LinkedHashMap<>();
        sizes.put("XXS", new ShirtSize(new Size(72, 80), new Size(57, 65), new Size(72, 80)));
        sizes.put("XS", new ShirtSize(new Size(80, 88), new Size(65, 73), new Size(80, 88)));
        sizes.put("S", new ShirtSize(new Size(88, 96), new Size(73, 81), new Size(88, 96)));
        sizes.put("M", new ShirtSize(new Size(96, 104), new Size(81, 89), new Size(96, 104)));
        sizes.put("L", new ShirtSize(new Size(104, 112), new Size(89, 97), new Size(104, 112)));
        sizes.put("XL", new ShirtSize(new Size(112, 124), new Size(97, 109), new Size(112, 120)));
        sizes.put("2XL", new ShirtSize(new Size(124, 136), new Size(109, 121), new Size(120, 128)));
        sizes.put("3XL", new ShirtSize(new Size(136, 148), new Size(121, 133), new Size(128, 136)));
        sizes.put("4XL", new ShirtSize(new Size(148, 160), new Size(133, 145), new Size(136, 148)));
        return sizes;
    }
    public static HashMap<String, ShirtSize> getWomenNike(){
        HashMap<String, ShirtSize> sizes = new LinkedHashMap<>();
        sizes.put("XXS", new ShirtSize(new Size(70, 76), new Size(54, 60), new Size(78, 84)));
        sizes.put("XS", new ShirtSize(new Size(76, 83), new Size(60, 67), new Size(84, 91)));
        sizes.put("S", new ShirtSize(new Size(83, 90), new Size(67, 74), new Size(91, 98)));
        sizes.put("M", new ShirtSize(new Size(90, 97), new Size(74, 81), new Size(98, 105)));
        sizes.put("L", new ShirtSize(new Size(97, 104), new Size(81, 88), new Size(105, 112)));
        sizes.put("XL", new ShirtSize(new Size(104, 114), new Size(88, 98), new Size(112, 120)));
        sizes.put("2XL", new ShirtSize(new Size(114, 124), new Size(98, 108), new Size(120, 128)));
        sizes.put("1X", new ShirtSize(new Size(114, 124), new Size(104, 114), new Size(118, 128)));
        sizes.put("2X", new ShirtSize(new Size(124, 134), new Size(114, 124), new Size(128, 138)));
        sizes.put("3X", new ShirtSize(new Size(134, 144), new Size(124, 134), new Size(138, 148)));
        return sizes;
    }
    public static HashMap<String, ShirtSize> getBoyNike(){
        HashMap<String, ShirtSize> sizes = new LinkedHashMap<>();
        sizes.put("XS", new ShirtSize(new Size(64.5, 66), new Size(59.5, 61.5), new Size(68.5, 71)));
        sizes.put("S", new ShirtSize(new Size(66, 69), new Size(61.5, 65), new Size(71, 74.5)));
        sizes.put("S+", new ShirtSize(new Size(70.5, 76.5), new Size(65.5, 70.5), new Size(75.5, 81)));
        sizes.put("M", new ShirtSize(new Size(69, 75), new Size(65, 69), new Size(74.5, 79.5)));
        sizes.put("M+", new ShirtSize(new Size(76.5, 83.5), new Size(70.5, 76), new Size(81, 87)));
        sizes.put("L", new ShirtSize(new Size(75, 81.5), new Size(69, 72.5), new Size(79.5, 84.5)));
        sizes.put("L+", new ShirtSize(new Size(83.5, 91), new Size(76, 82), new Size(87, 93.5)));
        sizes.put("XL", new ShirtSize(new Size(81.5, 88.5), new Size(72.5, 75.5), new Size(84.5, 89.5)));
        sizes.put("XL+", new ShirtSize(new Size(91, 98), new Size(82, 88), new Size(93.5, 100)));
        return sizes;
    }
    public static HashMap<String, ShirtSize> getGirlNike(){
        HashMap<String, ShirtSize> sizes = new LinkedHashMap<>();
        sizes.put("XS", new ShirtSize(new Size(64.5, 68), new Size(59.5, 61), new Size(68.5, 73)));
        sizes.put("S", new ShirtSize(new Size(68, 73), new Size(61, 64), new Size(73, 78.5)));
        sizes.put("S+", new ShirtSize(new Size(73.5, 80.5), new Size(67.5, 74.5), new Size(79.5, 85.5)));
        sizes.put("M", new ShirtSize(new Size(73, 79), new Size(64, 68), new Size(78.5, 83.5)));
        sizes.put("M+", new ShirtSize(new Size(80.5, 87.5), new Size(74.5, 82), new Size(85.5, 92)));
        sizes.put("L", new ShirtSize(new Size(79, 85.5), new Size(68, 71.5), new Size(83.5, 88.5)));
        sizes.put("L+", new ShirtSize(new Size(87.5, 96), new Size(82, 90), new Size(92, 99)));
        sizes.put("XL", new ShirtSize(new Size(85.5, 92.5), new Size(71.5, 74.5), new Size(88.5, 93.5)));
        sizes.put("XL+", new ShirtSize(new Size(96, 105), new Size(90, 99), new Size(99, 107)));
        return sizes;
    }

    public static List<String> getShirtSizeKeySet(HashMap<String, ShirtSize> hm){
        List<String> list = new ArrayList<>();
        list.add("N/A");
        for (Map.Entry<String, ShirtSize> entry: hm.entrySet()){
            list.add(entry.getKey());
        }
        return list;
    }

    public static ArrayList<RecommendationItem> getRecommendations(String brand, Profile p){
        ArrayList<RecommendationItem> recItems = new ArrayList<>();
        HashMap<String, ShirtSize> sizes = getShirtSizes(brand, p.gender, p.ageGroup);
        if(p.standard.toLowerCase().equals("imperial")){
            for (Map.Entry<String, ShirtSize> entry: sizes.entrySet()){
                sizes.replace(entry.getKey(), entry.getValue().toInches());
            }
        }
        String recSizes = "";
        for (Map.Entry<String, ShirtSize> entry: sizes.entrySet()){
            ShirtSize size = entry.getValue();
            Log.v(null, entry.getKey());
            boolean bustWithin = size.bust.isWithin(p.bust);
            Log.v(null, ""+bustWithin);
            boolean waistWithin = size.waist.isWithin(p.waist);
            Log.v(null, ""+waistWithin);
            boolean hipWithin = size.hip.isWithin(p.hip);
            Log.v(null, ""+hipWithin);
            if(bustWithin || waistWithin || hipWithin){
                Log.v(null,"Adding: "+entry.getKey());
                if(!recSizes.equals("")){
                    recSizes += ", ";
                }
                recSizes += "(" + entry.getKey() + ")";
            }
        }
        if(!recSizes.equals("")){
            recItems.add(new RecommendationItem("Shirt: ",recSizes,""));
        }else{
            recItems.add( new RecommendationItem("Shirt: ","","No Match"));
        }
        return recItems;
    }

}
