package com.example.onlineshopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onlineshopapp.Model.User;
import com.example.onlineshopapp.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private Button logoutBtn;
    private GoogleSignInClient client;
    private ImageView myAvatar;
    private TextView myName, myEmail;
    private User userApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mUser != null) {
            List<? extends UserInfo> providers = mUser.getProviderData();

            String name = mUser.getDisplayName();
            String email = mUser.getEmail();

            for (UserInfo profile : providers) {
                String strProviderId = profile.getProviderId();

                if (strProviderId.equals("google.com") || strProviderId.equals("facebook.com")) {
                    String photoURL = Objects.requireNonNull(mUser.getPhotoUrl()).toString();

                    Glide.with(this).load(photoURL).into(myAvatar);
                }
            }

            if (name.isEmpty()){
                myName.setVisibility(View.VISIBLE);
            }else{
                myName.setText(name);
            }

            if (email.isEmpty()){
                myEmail.setVisibility(View.VISIBLE);
            }else{
                myEmail.setText(email);
            }


            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (UserInfo profile : providers) {
                        String strProviderId = profile.getProviderId();

                        if (strProviderId.equals("google.com")) {
                            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build();

                            client = GoogleSignIn.getClient(ProfileActivity.this, options);
                            client.signOut();
                            FirebaseAuth.getInstance().signOut();
                        } else if (strProviderId.equals("facebook.com")) {
                            LoginManager.getInstance().logOut();
                            FirebaseAuth.getInstance().signOut();
                        } else if (strProviderId.equals("password")){
                            FirebaseAuth.getInstance().signOut();
                        }
                    }

                    Intent it = new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            });
        }else {
            userApi = (User) getIntent().getSerializableExtra("user");

            String name = userApi.getUsername();
            String email = userApi.getEmail();

            myEmail.setText(email);
            myName.setText(name);

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(it);
                    finish();
                }
            });
        }
    }

    private void initView() {
        myAvatar = findViewById(R.id.my_avata);
        myName = findViewById(R.id.my_name);
        myEmail = findViewById(R.id.my_email);
        logoutBtn = findViewById(R.id.logoutBtn);
    }
}

