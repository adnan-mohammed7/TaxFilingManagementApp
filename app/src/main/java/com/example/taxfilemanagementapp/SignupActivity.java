package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Geocoder;
import android.widget.Toast;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText nameField;
    EditText userNameField;
    EditText passwordField;
    EditText emailField;
    EditText phoneField;
    EditText companyField;
    EditText websiteField;
    EditText suiteField;
    EditText streetField;
    EditText cityField;
    EditText provinceField;
    EditText postalField;
    Button registerBtn;

    String name, userName, password, email, phone, company, website, suite, street, city, province, postal;
    UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameField = findViewById(R.id.signupName);
        userNameField = findViewById(R.id.signupUsername);
        passwordField = findViewById(R.id.signupPassword);
        emailField = findViewById(R.id.signupEmail);
        phoneField = findViewById(R.id.signupPhone);
        companyField = findViewById(R.id.signupCompanyName);
        websiteField = findViewById(R.id.signupWebsite);
        suiteField = findViewById(R.id.signupSuite);
        streetField = findViewById(R.id.signupStreet);
        cityField = findViewById(R.id.signupCity);
        provinceField = findViewById(R.id.signupProvince);
        postalField = findViewById(R.id.signupPostal);

        registerBtn = findViewById(R.id.registerBtn);
        userServices = UserServices.getInstance(this);
    }

    public void createCustomer(View view) {
        if(checkAllFields()){
            setValues();

            Geocoder geocoder = new Geocoder(this);

            String fullAddress = street + ", " + city + ", " + "Canada";
            Geo geo;
            try {
                List<android.location.Address> addresses = geocoder.getFromLocationName(fullAddress, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    android.location.Address address = addresses.get(0);
                    geo = new Geo(address.getLatitude(), address.getLongitude());
                } else {
                    geo = null;
                }

                Address address = new Address(street, suite, city, province, postal, geo);

                Customer customer = new Customer(name, userName, password, email, phone, company, website,"AWAITED", address);
                userServices.insertCustomer(customer, new UserServices.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        runOnUiThread(()->Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(()->Toast.makeText(SignupActivity.this, "Account cannot be created!", Toast.LENGTH_SHORT).show());
                        System.out.println(e.toString());
                    }
                });
            }catch (Exception e){
                System.out.println(e.toString());
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Fill in all the Fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void setValues(){
        name = nameField.getText().toString();
        userName = userNameField.getText().toString();
        password = passwordField.getText().toString();
        email = emailField.getText().toString();
        phone = phoneField.getText().toString();
        company = companyField.getText().toString();
        website = websiteField.getText().toString();
        suite = suiteField.getText().toString();
        street = streetField.getText().toString();
        city = cityField.getText().toString();
        province = provinceField.getText().toString();
        postal = postalField.getText().toString();
    }

    private boolean checkAllFields(){
        if(nameField.getText().toString().isEmpty() ||
        userNameField.getText().toString().isEmpty()||
        passwordField.getText().toString().isEmpty()||
        emailField.getText().toString().isEmpty()||
        phoneField.getText().toString().isEmpty()||
        companyField.getText().toString().isEmpty()||
        websiteField.getText().toString().isEmpty()||
        suiteField.getText().toString().isEmpty()||
        streetField.getText().toString().isEmpty()||
        cityField.getText().toString().isEmpty()||
        provinceField.getText().toString().isEmpty()||
        postalField.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}