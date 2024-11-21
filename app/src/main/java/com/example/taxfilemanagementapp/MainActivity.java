package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private SwitchCompat userSwitch;
    private EditText userNameField;
    private EditText passwordField;
    private UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userServices = UserServices.getInstance(this);

        ImageView imageView = findViewById(R.id.loginImageViewer);

        Glide.with(this).load("https://www.gtawealth.ca/sites/default/files/styles/large/public/Tax-Planning_Tax-Returns_GTA-Wealth_9.jpg?itok=L1r5jPfE").into(imageView);

        userSwitch = findViewById(R.id.userSwitch);
        userNameField = findViewById(R.id.userNameField);
        passwordField = findViewById(R.id.passwordField);
        userServices.getAllAdmins(admins -> {
            if(admins.isEmpty())
                addAdmin();
        });
    }

    public void handleSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void handleLogin(View view) {
        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();

        if (userSwitch.isChecked()) {
            userServices.getAdminByUsername(username, admin -> {
                if (admin != null) {
                    if (password.equals(admin.password)) {
                        Intent intent = new Intent(this, AdminHomeActivity.class);
                        startActivity(intent);
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show());
                }
            });
        } else {
            userServices.getCustomerByUsername(username, customer -> {
                if (customer != null) {
                    if (password.equals(customer.password)) {
                        Intent intent = new Intent(this, CustomerHomeActivity.class);
                        intent.putExtra("username", customer.userName);
                        startActivity(intent);
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show());
                }
            });
        }
    }

    void addAdmin(){
        Admin newAdmin = new Admin("Adnan", "Seneca123");
        userServices.insertAdmin(newAdmin, new UserServices.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                //
            }

            @Override
            public void onError(Exception e) {
                //
            }
        });
    }
}