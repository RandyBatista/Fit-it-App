package edu.wit.mobileapp.fit_it_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class RegisterFragment extends Fragment {

    EditText emailET;
    EditText passwordET;
    EditText confirmET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        emailET = rootView.findViewById(R.id.Email_ET);
        passwordET = rootView.findViewById(R.id.password_ET);
        confirmET = rootView.findViewById(R.id.confirmPassword_ET);

        Button submitBtn = rootView.findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(v -> {
            Context context = requireContext();
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if(password.equals(confirmET.getText().toString())){
                User u = new User(password);
                boolean logged = User.addUser(email, u);
                if(logged){
                    try{
                        Toast toast = Toast.makeText(context, "Successfully registered, please log in.", Toast.LENGTH_LONG);
                        toast.show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        Fragment fragment = new LoginFragment();
                        transaction.replace(R.id.content, fragment);
                        transaction.commit();
                    }catch (Exception e){
                        Toast toast = Toast.makeText(context, "Login error has occurred.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    Toast toast = Toast.makeText(context, "Email already in use.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return rootView;
    }
}
