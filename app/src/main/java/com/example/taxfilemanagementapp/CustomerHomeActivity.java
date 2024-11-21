package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CustomerHomeActivity extends AppCompatActivity {
    EditText nameField;
    EditText userNameField;
    EditText passwordField;
    EditText phoneField;
    EditText companyField;
    EditText websiteField;
    EditText suiteField;
    EditText streetField;
    EditText cityField;
    EditText provinceField;
    EditText postalField;
    TextView emailField;
    TextView statusView;

    String name, userName, password, phone, company, website, suite, street, city, province, postal, status;
    UserServices userServices;
    Customer loggedInCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        nameField = findViewById(R.id.customerHomeName);
        userNameField = findViewById(R.id.customerHomeUsername);
        passwordField = findViewById(R.id.customerHomePassword);
        emailField = findViewById(R.id.customerHomeEmail);
        phoneField = findViewById(R.id.customerHomePhone);
        companyField = findViewById(R.id.customerHomeCompanyName);
        websiteField = findViewById(R.id.customerHomeWebsite);
        suiteField = findViewById(R.id.customerHomeSuite);
        streetField = findViewById(R.id.customerHomeStreet);
        cityField = findViewById(R.id.customerHomeCity);
        provinceField = findViewById(R.id.customerHomesProvince);
        postalField = findViewById(R.id.customerHomePostal);
        statusView = findViewById(R.id.customerHomeStatus);

        userServices = UserServices.getInstance(this);
        Intent intent = getIntent();

        userServices.getCustomerByUsername(intent.getStringExtra("username"), customer -> runOnUiThread(()->{
            loggedInCustomer = customer;
            status = loggedInCustomer.status;
            preLoadField();
        }));
    }

    public void handleLogout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void updateCustomer(View view) {
        setValues();

        System.out.println("Old: " + loggedInCustomer.phone);
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
            loggedInCustomer.setName(name);
            loggedInCustomer.setUserName(userName);
            loggedInCustomer.setPassword(password);
            loggedInCustomer.setPhone(phone);
            loggedInCustomer.setCompanyTitle(company);
            loggedInCustomer.setWebsite(website);
            loggedInCustomer.setAddress(address);

            userServices.updateCustomer(loggedInCustomer, new UserServices.OperationCallback() {
                @Override
                public void onOperationCompleted() {
                    runOnUiThread(()-> Toast.makeText(CustomerHomeActivity.this, "Account Updated!", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(()->Toast.makeText(CustomerHomeActivity.this, "Account cannot be updated!", Toast.LENGTH_SHORT).show());
                    System.out.println(e.toString());
                }
            });
        }catch (Exception e){
            //noinspection UnnecessaryToStringCall
            System.out.println(e.toString());
        }
    }

    private void setValues(){
        name = nameField.getText().toString();
        userName = userNameField.getText().toString();
        password = passwordField.getText().toString();
        phone = phoneField.getText().toString();
        company = companyField.getText().toString();
        website = websiteField.getText().toString();
        suite = suiteField.getText().toString();
        street = streetField.getText().toString();
        city = cityField.getText().toString();
        province = provinceField.getText().toString();
        postal = postalField.getText().toString();
    }

    void preLoadField(){
        nameField.setText(loggedInCustomer.name);
        userNameField.setText(loggedInCustomer.userName);
        passwordField.setText(loggedInCustomer.password);
        emailField.setText(String.format("Email: %s", loggedInCustomer.email));
        phoneField.setText(loggedInCustomer.phone);
        companyField.setText(loggedInCustomer.companyTitle);
        websiteField.setText(loggedInCustomer.website);
        suiteField.setText(loggedInCustomer.address.suite);
        streetField.setText(loggedInCustomer.address.street);
        cityField.setText(loggedInCustomer.address.city);
        provinceField.setText(loggedInCustomer.address.province);
        postalField.setText(loggedInCustomer.address.zipcode);
        statusView.setText(String.format("Status: %s", loggedInCustomer.status));

        switch (loggedInCustomer.status) {
            case "AWAITED":
                statusView.setBackgroundColor(Color.parseColor("#FFFFE0"));
                break;
            case "FAILEDTOREACH":
                statusView.setBackgroundColor(Color.parseColor("#FFCCCB"));
                break;
            case "ONBOARDED":
                statusView.setBackgroundColor(Color.parseColor("#90EE90"));
                break;
            case "INPROCESS":
                statusView.setBackgroundColor(Color.parseColor("#32CD32"));
                break;
            case "COMPLETED":
                statusView.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case "DENIED":
                statusView.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
            default:
                statusView.setBackgroundColor(Color.WHITE);
                break;
        }
    }
}