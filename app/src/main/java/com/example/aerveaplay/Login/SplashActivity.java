package com.example.aerveaplay.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.aerveaplay.R;
import com.example.aerveaplay.databinding.ActivitySplashBinding;
import com.example.aerveaplay.onboarding.ui.OnboardingActivity;


public class SplashActivity extends AppCompatActivity {

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
                finish();

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        binding.logoSplashImg.startAnimation(anim);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}