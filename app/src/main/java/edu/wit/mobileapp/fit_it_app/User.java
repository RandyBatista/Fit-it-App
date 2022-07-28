package edu.wit.mobileapp.fit_it_app;

import java.util.HashMap;

public class User {

    public static User loggedUser;
    private static HashMap<String, User> users = new HashMap<>();

    String password;
    HashMap<String,Profile> sizeProfiles;

    public User(String password){
        this(password, new HashMap<>());
    }

    public User(String password, HashMap<String,Profile> sizeProfiles){
        this.password = password;
        this.sizeProfiles = sizeProfiles;
    }

    public static User getLoggedUser(){
        return loggedUser;
    }

    public static boolean loginUser(String email, String password){
        if(users == null)
            return false;
        if (users.containsKey(email)){
            if(users.get(email).password.equals(password)) {
                loggedUser = users.get(email);
                return true;
            }
        }return false;
    }

    public static boolean addUser(String email, User u){
        if (users.containsKey(email)){
            return false;
        }else{
            users.put(email, u);
            return true;
        }
    }

}
