package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class CustomerDetailActivity extends AppCompatActivity {
    TextView nameField;
    TextView userNameField;
    TextView passwordField;
    TextView phoneField;
    TextView companyField;
    TextView websiteField;
    TextView suiteField;
    TextView streetField;
    TextView cityField;
    TextView provinceField;
    TextView postalField;
    TextView emailField;
    Spinner statusView;
    Toolbar toolbar;

    UserServices userServices;
    Customer loggedInCustomer;
    String status;
    String[] statusOptions = {"AWAITED", "FAILEDTOREACH", "ONBOARDED", "INPROCESS", "COMPLETED","DENIED"};
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nameField = findViewById(R.id.customerDetailName);
        userNameField = findViewById(R.id.customerDetailUsername);
        passwordField = findViewById(R.id.customerDetailPassword);
        emailField = findViewById(R.id.customerDetailEmail);
        phoneField = findViewById(R.id.customerDetailPhone);
        companyField = findViewById(R.id.customerDetailCompanyName);
        websiteField = findViewById(R.id.customerDetailWebsite);
        suiteField = findViewById(R.id.customerDetailSuite);
        streetField = findViewById(R.id.customerDetailStreet);
        cityField = findViewById(R.id.customerDetailCity);
        provinceField = findViewById(R.id.customerDetailProvince);
        postalField = findViewById(R.id.customerDetailPostal);
        statusView = findViewById(R.id.customerDetailStatus);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusView.setAdapter(arrayAdapter);

        userServices = UserServices.getInstance(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        userServices.getCustomerByUsername(intent.getStringExtra("username"), new Consumer<Customer>() {
            @Override
            public void accept(Customer customer) {
                runOnUiThread(()->{
                    loggedInCustomer = customer;
                    preLoadField();
                });
            }
        });
    }

    public void updateCustomer(View view) {
        setValue();

        loggedInCustomer.setStatus(status);
        userServices.updateCustomer(loggedInCustomer, new UserServices.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                runOnUiThread(()-> Toast.makeText(CustomerDetailActivity.this, "Status Updated!", Toast.LENGTH_SHORT).show());
                Intent intent = new Intent();
                intent.putExtra("id", loggedInCustomer.uid);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(()-> Toast.makeText(CustomerDetailActivity.this, "Status cannot be Updated!", Toast.LENGTH_SHORT).show());
                System.out.println(e.toString());
            }
        });
    }

    public void handleLogout(View view) {
    }

    void preLoadField(){
        nameField.setText(loggedInCustomer.name);
        userNameField.setText(loggedInCustomer.userName);
        passwordField.setText(loggedInCustomer.password);
        emailField.setText(loggedInCustomer.email);
        phoneField.setText(loggedInCustomer.phone);
        companyField.setText(loggedInCustomer.companyTitle);
        websiteField.setText(loggedInCustomer.website);
        suiteField.setText(loggedInCustomer.address.suite);
        streetField.setText(loggedInCustomer.address.street);
        cityField.setText(loggedInCustomer.address.city);
        provinceField.setText(loggedInCustomer.address.province);
        postalField.setText(loggedInCustomer.address.zipcode);
        int pos = new ArrayList<String>(Arrays.asList(statusOptions)).indexOf(loggedInCustomer.status);
        statusView.setSelection(pos);
    }

    void setValue(){
        status = statusView.getSelectedItem().toString();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}