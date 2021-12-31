package com.licia_josh.auths;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.licia_josh.MainActivity;
import com.licia_josh.R;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText  email, password;
    private Button login;
    private TextView register, forgetPassword;
    private String Email, Password, CurrentUID;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private Boolean emailAddress, isVisible;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mProgress = new ProgressDialog(this);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.txtRegister);
        forgetPassword = findViewById(R.id.txtForgotPassword);

        register.setOnClickListener(v->{
            startActivity(new Intent(this, SignUpActivity.class));
        });

        login.setOnClickListener(v->{
            Login();
        });
    }

    private void Login() {
         Email = email.getText().toString();
         Password = password.getText().toString();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
        } else {
            mProgress.setTitle("Login... in");
            mProgress.setMessage("Loading....");
            mProgress.show();
            mProgress.setCanceledOnTouchOutside(false);
            mAuth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            VerifyEmailAddress();
                            mProgress.dismiss();
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(SignInActivity.this, "Error Verifying" + message, Toast.LENGTH_SHORT).show();
                            mProgress.dismiss();
                        }
                    });
        }
    }

    private void VerifyEmailAddress() {
        FirebaseUser user = mAuth.getCurrentUser();
        emailAddress = user.isEmailVerified();

        if (emailAddress) {
            String currentUserID = mAuth.getCurrentUser().getUid();
            String devicetoken = FirebaseInstanceId.getInstance().getToken();

            userRef.child(currentUserID).child("device_token").setValue(devicetoken).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // After successful login it goes to mainactivity.
                    SendUserToMain();
//                        Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(SignInActivity.this, "Verify Your Email Account", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    private void SendUserToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}