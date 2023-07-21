package com.example.aerveaplay.Login.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.aerveaplay.Login.viewmodel.AuthViewModel;
import com.example.aerveaplay.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    private EditText email_ET, pass_ET, userName_ET, confirmPass_ET;
    private Button SignUp_Btn;
    private ImageView back_signIn_img;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())).get(AuthViewModel.class);

        viewModel.getUserData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                navController.navigate(R.id.action_sigUpFragment_to_mainActivity);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName_ET = view.findViewById(R.id.user_name_edit_text);
        email_ET = view.findViewById(R.id.email_edit_text);
        pass_ET = view.findViewById(R.id.password_edit_text);
        confirmPass_ET = view.findViewById(R.id.confirm_password_edit_text);

        back_signIn_img = view.findViewById(R.id.back_signIn);
        SignUp_Btn = view.findViewById(R.id.createAccount_btn);

        navController = Navigation.findNavController(view);

        back_signIn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_sigUpFragment_to_sigInFragment);
            }
        });

        SignUp_Btn.setOnClickListener(v -> {
            String email = email_ET.getText().toString();
            String pass = pass_ET.getText().toString();
            String name = userName_ET.getText().toString();
            String confirmPass = confirmPass_ET.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (TextUtils.isEmpty(name)) {
                userName_ET.setError(getString(R.string.UserName));
            } else if (TextUtils.isEmpty(email) || !email.matches(emailPattern)) {
                email_ET.setError(getString(R.string.InvalidEmail));
            } else if (TextUtils.isEmpty(pass) || !(pass.length() >= 8)) {
                pass_ET.setError(getString(R.string.characters));
            } else if (!confirmPass.equals(pass)) {
                confirmPass_ET.setError(getString(R.string.confirm));
            } else {
                viewModel.register(email, pass);
            }

        });
    }

}


















