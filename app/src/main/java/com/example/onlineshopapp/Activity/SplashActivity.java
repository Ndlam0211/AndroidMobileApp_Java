package com.example.onlineshopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.onlineshopapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    TextView appName;
    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.appName);
        lottie = findViewById(R.id.lottie);

        appName.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },5000);
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Intent it = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(it);
        }else {
            Intent it = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(it);
        }
    }

}
