package com.example.onlineshopapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopapp.Api.UserService;
import com.example.onlineshopapp.Model.User;
import com.example.onlineshopapp.R;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, pwEditText;
    private TextView registerTxt;
    private AppCompatButton loginBtn, ggBtn, fbBtn;
    public List<User> userList;
    public User mUser;
    private GoogleSignInClient client;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        callApiGetUsers();

        initListener();
    }

    private void initListener() {
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this,FacebookLoginActivity.class);
                startActivity(it);
            }
        });

        registerTxt.setOnClickListener(view -> {
            Intent it = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(it);
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = emailEditText.getText().toString().trim();
                String strPass = pwEditText.getText().toString().trim();

                if (strEmail == null || strEmail.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email is empty",
                            Toast.LENGTH_LONG).show();
                } else if (strPass == null || strPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password is empty",
                            Toast.LENGTH_LONG).show();
                }else {
                    boolean isHasUserAPI = false;
                    boolean isHasUserFireBase = false;

                    for (User user : userList){
                        if (strEmail.equals(user.getEmail()) && strPass.equals(user.getPassword())){
                            isHasUserAPI =true;
                            mUser = user;
                            break;
                        }
                    }

                    if (isHasUserAPI == false){
                        isHasUserFireBase = true;
                    }

                    if (isHasUserAPI){
                        Toast.makeText(LoginActivity.this,"Login successfull", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        it.putExtra("userName",String.valueOf(mUser.getName().getFirstname()));
                        it.putExtra("user", mUser);
                        startActivity(it);
                    } else if (isHasUserFireBase) {
                        auth.signInWithEmailAndPassword(strEmail, strPass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Đăng nhập thành công, chuyển đến MainActivity
                                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(it);
                                        } else {
                                            // Đăng nhập thất bại, hiển thị thông báo lỗi
                                            Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else{
                        Toast.makeText(LoginActivity.this, "Email or Password not true",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        ggBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = client.getSignInIntent();
                startActivityForResult(it,1234);
            }
        });
    }

    private void callApiGetUsers(){
        userList = new ArrayList<>();
        Call<List<User>> call = UserService.userService.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userList = response.body();
                    if (userList == null || userList.isEmpty())
                        return;
                } else {
                    Toast.makeText(LoginActivity.this, "Request failed with code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("id", user.getUid());
                                    map.put("name", user.getDisplayName());
                                    map.put("profile", user.getPhotoUrl().toString());

                                    database.getReference().child("googleUsers").child(user.getUid()).setValue(map);

                                    Toast.makeText(LoginActivity.this,"login successfull", Toast.LENGTH_SHORT).show();

                                    Intent it = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(it);
                                }else {
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("AUTHENTICATION", "Google sign-in failed: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null){
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
        }
    }

    public void initView(){
        emailEditText = findViewById(R.id.emailEditText);
        pwEditText = findViewById(R.id.pwEditText);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxt = findViewById(R.id.registerTxt);
        fbBtn = findViewById(R.id.fb_btn);
        ggBtn = findViewById(R.id.gg_btn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, options);
    }
}