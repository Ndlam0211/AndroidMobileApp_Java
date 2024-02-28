package com.example.onlineshopapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtPassword;
    private EditText edtEmail;
    private TextView recoveryTxt;
    private Button btnSignUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();

        initListener();
    }

    private void initListener() {
        recoveryTxt.setOnClickListener(view -> {
            Intent it = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(it);
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }

    public void onClickSignUp(){
        String strName = edtName.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();

        if (strName == null || strName.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Name is empty",
                    Toast.LENGTH_LONG).show();
        } else if (strEmail == null || strEmail.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Email is empty",
                    Toast.LENGTH_LONG).show();
        } else if (strPassword == null || strPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Password is empty",
                    Toast.LENGTH_LONG).show();
        }else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUpActivity.this,"SignUp successfully", Toast.LENGTH_SHORT).show();

                                Intent it = new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(it);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "SignUp failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void initView(){
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        recoveryTxt = findViewById(R.id.recoveryTxt);
        btnSignUp = findViewById(R.id.signupBtn);
    }
}
