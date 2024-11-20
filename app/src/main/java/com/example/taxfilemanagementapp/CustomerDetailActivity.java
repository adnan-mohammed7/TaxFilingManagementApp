package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerDetailActivity extends AppCompatActivity {
    TextView nameField;
    TextView userNameField;
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, statusOptions);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        statusView.setAdapter(arrayAdapter);

        userServices = UserServices.getInstance(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        userServices.getCustomerByUsername(intent.getStringExtra("username"), customer -> runOnUiThread(()->{
            loggedInCustomer = customer;
            preLoadField();
        }));
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
        nameField.setText(String.format("Name: %s", loggedInCustomer.name));
        userNameField.setText(String.format("Username: %s", loggedInCustomer.userName));
        emailField.setText(String.format("Email: %s", loggedInCustomer.email));
        phoneField.setText(String.format("Phone: %s", loggedInCustomer.phone));
        companyField.setText(String.format("Company: %s", loggedInCustomer.companyTitle));
        websiteField.setText(String.format("Website: %s", loggedInCustomer.website));
        suiteField.setText(String.format("Suite: %s", loggedInCustomer.address.suite));
        streetField.setText(String.format("Street: %s", loggedInCustomer.address.street));
        cityField.setText(String.format("City: %s", loggedInCustomer.address.city));
        provinceField.setText(String.format("Province: %s", loggedInCustomer.address.province));
        postalField.setText(String.format("Postal Code: %s", loggedInCustomer.address.zipcode));
        int pos = new ArrayList<>(Arrays.asList(statusOptions)).indexOf(loggedInCustomer.status);
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