package com.example.aerveaplay.Login.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aerveaplay.Login.viewmodel.AuthViewModel;
import com.example.aerveaplay.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class SignInFragment extends Fragment {


    private AuthViewModel viewModel;
    private NavController navController;
    private EditText email_ET, pass_ET;
    private Button logIn_Btn;
    private TextView createAccount_tv, restPassword;

    // Initialize variables GMAIL login
    private ImageView loginWithGmail;
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

        viewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Log.d(TAG, "checkUser: Already Logged In");
                if (firebaseUser != null) {
                    navController.navigate(R.id.action_sigInFragment_to_mainActivity);

                }
            }
        });

        //Configure the Google SignIn
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

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
        restPassword = view.findViewById(R.id.rest_password);


        createAccount_tv = view.findViewById(R.id.createAccount);
        logIn_Btn = view.findViewById(R.id.login_btn);

        navController = Navigation.findNavController(view);

        // Google SignIn Image Click
        loginWithGmail = view.findViewById(R.id.icon_gmail);
        loginWithGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: begin Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        createAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_sigInFragment_to_sigUpFragment);
            }
        });


        //Goto rest password fragment
        restPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new fragment and transaction
                navController.navigate(R.id.action_sigInFragment_to_restPaswordFragment);

            }
        });

        //Login Validation
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Result returned from lunching
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google SignIn Intent Result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // google sign in success, now auth with firebase
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            }catch (Exception e){
                Log.d(TAG, "onActivityResult: "+e.getMessage());
            }

        }

    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG,"firebaseAuthWithGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Logged In");

                        //get login in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        // get user info
                        String uid  = firebaseUser.getUid();
                        String email= firebaseUser.getEmail();

                        Log.d(TAG, "onSuccess: Email: "+email);
                        Log.d(TAG, "onSuccess: UID: "+uid);

                        //Check if user is new or existing
                        if (authResult.getAdditionalUserInfo().isNewUser()){
                            //user is new -- Account Created..
                            Log.d(TAG, "onSuccess: Account Created ...\n"+email);
                            Toast.makeText(getContext(), "Account Created..\n"+email, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onSuccess: Existing User ...\n"+email);
                            Toast.makeText(getContext(), "Existing User ...\n"+email, Toast.LENGTH_SHORT).show();
                        }

                        // Open Home Activity
                        navController.navigate(R.id.action_sigInFragment_to_mainActivity);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Login Failed"+e.getMessage());

                    }
                });
    }
}




















