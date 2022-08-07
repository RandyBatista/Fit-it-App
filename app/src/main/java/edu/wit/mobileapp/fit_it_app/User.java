package edu.wit.mobileapp.fit_it_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class User {

    public static User loggedUser;

    public String UID;
    //String password;
    HashMap<String,Profile> sizeProfiles;
    final static HashMap<String, Profile> has = new HashMap<String, Profile>(){{put("Profile 1", new Profile());}};

    public User(){
        this(has);
    }

    public User(HashMap<String,Profile> sizeProfiles){
        this.sizeProfiles = sizeProfiles;
    }


    public static User getLoggedUser(){
        return loggedUser;
    }


}
