package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    EditText emailET;
    EditText passwordET;
    EditText confirmET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        emailET = rootView.findViewById(R.id.email_ET);
        passwordET = rootView.findViewById(R.id.password_ET);
        confirmET = rootView.findViewById(R.id.confirmPassword_ET);

        Button submitBtn = rootView.findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(v -> {
                Context context = requireContext();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if(email.isEmpty() || password.isEmpty()) {
                    Toast toast = Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(password.equals(confirmET.getText().toString())){
                            /* if user is registered correctly with their email and password,
                             data needs to beaded into the database
                             */
                    MainActivity.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), task -> {
                            if (task.isSuccessful()) {
                                // creates object of type user which will be initialized
                                // with the data of the new User
                                User user = new User(MainActivity.mAuth.getCurrentUser().getUid(), email);
                                // saves data into database
                                // ones data is saved into database onComplete is executed
                                user.saveToDatabase();
                                try {
                                    Toast toast = Toast.makeText(context, "Successfully registered, please log in.", Toast.LENGTH_LONG);
                                    toast.show();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    Fragment fragment = new LoginFragment();
                                    transaction.replace(R.id.content, fragment);
                                    transaction.commit();
                                } catch (Exception e) {
                                    Toast toast = Toast.makeText(context, "Login error has occurred.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } else {
                                Toast toast = Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG);
                                toast.show();
                            }});
                }else{
                    Toast toast = Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT);
                    toast.show();
                }});

        return rootView;
    }
}
