package com.example.aerveaplay.Login.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aerveaplay.Login.viewmodel.AuthViewModel;
import com.example.aerveaplay.R;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {


    private AuthViewModel viewModel;
    private NavController navController;
    private EditText email_ET, pass_ET;
    private Button logIn_Btn;
    private TextView createAccount_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

        viewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    navController.navigate(R.id.action_sigInFragment_to_mainActivity);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email_ET = view.findViewById(R.id.email_IN_edit_text);
        pass_ET = view.findViewById(R.id.PASS_IN_edit_text);

        createAccount_tv = view.findViewById(R.id.createAccount);
        logIn_Btn = view.findViewById(R.id.login_btn);

        navController = Navigation.findNavController(view);

        createAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_sigInFragment_to_sigUpFragment);
            }
        });

        logIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                String pass = pass_ET.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(email) || !email.matches(emailPattern)) {
                    email_ET.setError(getString(R.string.InvalidEmail));
                } else if (TextUtils.isEmpty(pass) || !(pass.length() >= 8)) {
                    pass_ET.setError(getString(R.string.characters));
                } else {
                    viewModel.login(email, pass);
                }

            }
        });
    }

}