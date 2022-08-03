package edu.wit.mobileapp.fit_it_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

//import io.realm.Realm;
//import io.realm.mongodb.App;
//import io.realm.mongodb.AppConfiguration;

public class MainActivity extends AppCompatActivity {

    String Appid = "fit_it-bmthw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Realm.init(this); // context, usually an Activity or Application

        // Creates an instance of the Realm application in Android Studio
        // which is linked to online realm created
       // App app = new App(new AppConfiguration.Builder(Appid).build());

        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if(User.loggedUser == null){
                Fragment fragment = new LoginFragment();
                transaction.replace(R.id.content, fragment);
            }
            transaction.commit();
        }
    }
}