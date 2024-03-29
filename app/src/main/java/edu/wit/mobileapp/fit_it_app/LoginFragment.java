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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    EditText emailET;
    EditText passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.log_in_fragment, container, false);

        if(MainActivity.mAuth.getCurrentUser() != null){
            MainActivity.mAuth.signOut();
        }

        //Get all interactables within layout
        emailET = rootView.findViewById(R.id.email_ET);
        passwordET = rootView.findViewById(R.id.password_ET);
        Button registerBtn = rootView.findViewById(R.id.register_Btn);
        Button guestBtn = rootView.findViewById(R.id.guest_Btn);
        Button submitBtn = rootView.findViewById(R.id.submit_Btn);

        registerBtn.setOnClickListener(v -> {
            MainActivity.loadFragment(getActivity(), new RegisterFragment(), "Login");
        });

        guestBtn.setOnClickListener(v -> {

            User.setGuestUser(new User("None"));
            MainActivity.loadFragment(getActivity(), new ChooseSizeInputFragment(), "Login");
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
                MainActivity.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), task -> {
                FirebaseUser user = MainActivity.mAuth.getCurrentUser();
                if (user != null) {
                    FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).
                            addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String profiles = snapshot.child("sizeProfiles").getValue(String.class);
                            String selected = snapshot.child("selectedProfile").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User u = new User("None");
                            try {
                                if(selected == null){
                                    selected = "None";
                                }
                                if (profiles == null || profiles.equals("")) {
                                    u = new User(uid);
                                } else {
                                    u = new User(uid, email, selected, new JSONObject(profiles));
                                }

                            }catch(Exception e){
                                Log.v(null, e.toString());
                            }
                            try {


                                if(u.sizeProfiles.length() == 0){
                                    MainActivity.loadFragment(getActivity(), new ChooseSizeInputFragment(), "Login");
                                }else{
                                    MainActivity.loadFragment(getActivity(), new BrandsViewFragment(), "Login");
                                }
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
