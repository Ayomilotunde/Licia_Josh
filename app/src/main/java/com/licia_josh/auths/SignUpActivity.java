package com.licia_josh.auths;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.licia_josh.R;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText fullName, email, dateOfBirth, mobileNumber, password;
    private Button register;
    private TextView signIn;
    private String FullName, Email, DateOfBirth, MobileNumber, Password, CurrentUID;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference RootRef;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mProgress = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        RootRef = database.getReference();

        fullName = findViewById(R.id.edtFullName);
        email = findViewById(R.id.edtEmail);
        dateOfBirth = findViewById(R.id.edtBirtDate);
        mobileNumber = findViewById(R.id.edtMobileNumber);
        password = findViewById(R.id.edtPassword);
        register = findViewById(R.id.btnRegister);
        signIn = findViewById(R.id.txtSignIn);

        signIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });

        register.setOnClickListener(v->{
            Register();
        });
    }

    private void Register() {
        FullName = fullName.getText().toString();
        Email = email.getText().toString();
        DateOfBirth = dateOfBirth.getText().toString();
        MobileNumber = mobileNumber.getText().toString();
        Password = password.getText().toString();
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Email is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(FullName)) {
            Toast.makeText(this, "FullName is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(DateOfBirth)) {
            Toast.makeText(this, "Date Of Birth is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(MobileNumber)) {
            Toast.makeText(this, "Mobile Number is Required", Toast.LENGTH_SHORT).show();
        } else {

            mProgress.setTitle("Register");
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCanceledOnTouchOutside(false);

            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
                Toast.makeText(SignUpActivity.this, "Registration Successfully, Check Your Email To Verify Your Account...",
                        Toast.LENGTH_LONG).show();

                mAuth = FirebaseAuth.getInstance();
                CurrentUID = mAuth.getCurrentUser().getUid();
                String devicetoken = FirebaseInstanceId.getInstance().getToken();

                HashMap<String, String> chatNotificationMap = new HashMap<>();
                chatNotificationMap.put("device_token", devicetoken);
                chatNotificationMap.put("email", Email);
                chatNotificationMap.put("fullname", FullName);
                chatNotificationMap.put("dob", DateOfBirth);
                chatNotificationMap.put("mobilenumber", MobileNumber);
                chatNotificationMap.put("uid", CurrentUID);
                RootRef.child("Users").child(CurrentUID).setValue(chatNotificationMap).addOnCompleteListener(task1 -> {
                    SendEmailVerificationMessage();
                    SendUserToLoginActivity();
                    mAuth.signOut();
                    mProgress.dismiss();
                });
//                RootRef.child("Users").child(CurrentUID).child("device_token").setValue(devicetoken);
//                RootRef.child("Users").child(CurrentUID).child("email").setValue(Email);
//                RootRef.child("Users").child(CurrentUID).child("fullname").setValue(FullName);
//                RootRef.child("Users").child(CurrentUID).child("dob").setValue(DateOfBirth);
//                RootRef.child("Users").child(CurrentUID).child("mobilenumber").setValue(MobileNumber);
            });
        }
    }

    private void SendEmailVerificationMessage() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification();
        }
    }

    private void SendUserToLoginActivity() {
        Intent clickPostIntent = new Intent(this, SignInActivity.class);
        clickPostIntent.putExtra("EmailKey", email.getText().toString());
        startActivity(clickPostIntent);
        finish();
    }


}