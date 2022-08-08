package edu.wit.mobileapp.fit_it_app;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;

public class User {

    private static User guestUser;

    String UID;
    String email;
    String selectedProfile;
    JSONObject sizeProfiles;

    public User(String UID){this(UID, "guest@guest.com");}
    public User(String UID, String email){
        this(UID, email,"None", new JSONObject());
    }
    public User(String UID, String email, String selectedProfile, JSONObject sizeProfiles){
        this.UID = UID;
        this.email = email;
        this.selectedProfile = selectedProfile;
        this.sizeProfiles = sizeProfiles;
    }

    public static User getGuestUser() {
        return guestUser;
    }

    public static void setGuestUser(User guestUser) {
        User.guestUser = guestUser;
    }

    public void saveToDatabase(){
        FirebaseDatabase.getInstance("https://fit-it-app-eb283-default-rtdb.firebaseio.com/").getReference("users").
                child(UID).child("selectedProfile").setValue(selectedProfile);
        FirebaseDatabase.getInstance("https://fit-it-app-eb283-default-rtdb.firebaseio.com/").getReference("users").
                child(UID).child("UID").setValue(UID);
        FirebaseDatabase.getInstance("https://fit-it-app-eb283-default-rtdb.firebaseio.com/").getReference("users").
                child(UID).child("email").setValue(email);
        FirebaseDatabase.getInstance("https://fit-it-app-eb283-default-rtdb.firebaseio.com/").getReference("users").
                child(UID).child("sizeProfiles").setValue(sizeProfiles.toString());
    }
}
