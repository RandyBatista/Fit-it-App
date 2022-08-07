package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginFragment extends Fragment {

    // Firebase authentication object
    private FirebaseAuth mAuth;

    EditText emailET;
    EditText passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.log_in_fragment, container, false);

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //Get all interactables within layout
        emailET = rootView.findViewById(R.id.email_ET);
        passwordET = rootView.findViewById(R.id.password_ET);
        Button registerBtn = rootView.findViewById(R.id.register_btn);
        Button guestBtn = rootView.findViewById(R.id.guest_btn);
        Button submitBtn = rootView.findViewById(R.id.submit_btn);

        registerBtn.setOnClickListener(v -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = new RegisterFragment();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        });

        guestBtn.setOnClickListener(v -> {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            User.setLoggedUser(new User("None"));
            Fragment fragment = new ChooseSizeInputFragment();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
        });

        submitBtn.setOnClickListener(v -> {

            Context context = requireContext();
            String email = emailET.getText().toString();
            String password = emailET.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {
                Toast toast = Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            try{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), task -> {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String profiles = snapshot.child("sizeProfiles").getValue(String.class);
                            String uid = snapshot.child("UID").getValue(String.class);
                            User u;
                            try {
                                if (profiles == null) {
                                    u = new User(uid);
                                } else {
                                    u = new User(uid, new JSONObject(profiles));
                                }
                                User.setLoggedUser(u);
                            }catch(Exception e){
                                Log.v(null, e.toString());
                            }
                            try {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                Fragment fragment;

                                if(User.getLoggedUser().sizeProfiles.length() == 0){
                                    fragment = new ChooseSizeInputFragment();
                                }else{
                                    fragment = new BrandsViewFragment();
                                }

                                transaction.replace(R.id.content, fragment);
                                transaction.commit();
                            } catch (Exception e) {
                                Log.v(null, e.toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.v(null, "error");
                        }
                    });

                } else {
                    Toast toast = Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                    });
            } catch (Exception e) {
                Log.v(null, e.toString());
            }
        });

        return rootView;
    }
}
