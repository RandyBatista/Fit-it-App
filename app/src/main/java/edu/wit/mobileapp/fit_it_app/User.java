package edu.wit.mobileapp.fit_it_app;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class User {

    public static User loggedUser;

    public String email;
    public String password;
    //String password;
    HashMap<String,Profile> sizeProfiles;

    public User(){
    }

    public User(String email,String password){
        this(email, password, new HashMap<>());
    }

    public User(String email, String password, HashMap<String,Profile> sizeProfiles){
        this.email = email;
        this.password = password;
        this.sizeProfiles = sizeProfiles;
    }

    public static User getLoggedUser(){
        return loggedUser;
    }

    public static void setLoggedUser(FirebaseUser user){
        System.out.println(user);
    }

}
