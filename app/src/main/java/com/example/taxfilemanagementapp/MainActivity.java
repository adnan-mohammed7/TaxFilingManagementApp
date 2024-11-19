package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwitchCompat userSwitch;
    private EditText userNameField;
    private  EditText passwordField;
    private UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userServices = UserServices.getInstance(this);

        ImageView imageView = findViewById(R.id.loginImageViewer);
        Button signupBtn = findViewById(R.id.signupBtn);

        Glide.with(this).load("https://www.gtawealth.ca/sites/default/files/styles/large/public/Tax-Planning_Tax-Returns_GTA-Wealth_9.jpg?itok=L1r5jPfE").into(imageView);

        userSwitch = findViewById(R.id.userSwitch);
        userNameField = findViewById(R.id.userNameField);
        passwordField = findViewById(R.id.passwordField);
//        printCustomer();
    }

    public void handleSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void handleLogin(View view) {
        String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();

        if(userSwitch.isChecked()){
            userServices.getAdminByUsername(username, admin ->{
                if(admin != null){
                    if(password.equals(admin.password)){
                        Intent intent = new Intent(this, AdminHomeActivity.class);
                        startActivity(intent);
                    }else{
                        runOnUiThread(()->Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show());
                    }
                }else{
                    runOnUiThread(()->Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show());
                }
            });
        }else{
            userServices.getCustomerByUsername(username, customer ->{
                if(customer != null){
                    if(password.equals(customer.password)){

                    }else{
                        runOnUiThread(()->Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show());
                    }
                }else{
                    runOnUiThread(()->Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show());
                }
            });
        }
    }

    private void createAdmins(){
        Admin admin = new Admin("Adnan", "Seneca123");
        userServices.insertAdmin(admin, new UserServices.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                System.out.println("New Admin Created");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("Admin cannot be created");
                System.out.println(e.toString());
            }
        });
    }

    private void printCustomer(){
        userServices.getAllCustomers(customers -> {
            for (Customer e : customers){
                System.out.println(e.name);
                System.out.println(e.userName);
                System.out.println(e.password);
                System.out.println(e.email);
                System.out.println(e.phone);
                System.out.println(e.companyTitle);
                System.out.println(e.website);
                System.out.println(e.status);
                System.out.println(e.address.suite);
                System.out.println(e.address.street);
                System.out.println(e.address.city);
                System.out.println(e.address.province);
                System.out.println(e.address.zipcode);
                System.out.println(e.address.geo.lat);
                System.out.println(e.address.geo.lon);
            }
        });
    }

    private void deleteCustomers(){
        userServices.getCustomerByUsername("meraj7", customer -> userServices.deleteCustomer(customer, new UserServices.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                System.out.println("User Deleted");
            }

            @Override
            public void onError(Exception e) {

            }
        }));
    }
}