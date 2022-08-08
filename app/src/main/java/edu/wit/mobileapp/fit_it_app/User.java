package edu.wit.mobileapp.fit_it_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;

public class User {

    private static User loggedUser;

    String UID;
    String selectedProfile;
    //String password;
    JSONObject sizeProfiles;

    public User(String UID){this(UID, "None", new JSONObject());
    }

    public User(String UID, String selectedProfile, JSONObject sizeProfiles){
        this.UID = UID;
        this.selectedProfile = selectedProfile;
        this.sizeProfiles = sizeProfiles;
    }


    public static User getLoggedUser(){
        return loggedUser;
    }
    public static void setLoggedUser(User u){loggedUser=u;}

}
