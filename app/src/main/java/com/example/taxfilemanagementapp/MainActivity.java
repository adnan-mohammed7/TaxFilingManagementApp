package com.example.taxfilemanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.loginImageViewer);
        Button signupBtn = findViewById(R.id.signupBtn);

        Glide.with(this).load("https://www.gtawealth.ca/sites/default/files/styles/large/public/Tax-Planning_Tax-Returns_GTA-Wealth_9.jpg?itok=L1r5jPfE").into(imageView);

    }

    public void handleSignup(View view) {
        System.out.println("Signup");
    }

    public void handleLogin(View view) {
        System.out.println("Login");
    }
}