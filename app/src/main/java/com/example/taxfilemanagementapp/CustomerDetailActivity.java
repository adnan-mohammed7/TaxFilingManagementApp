package com.example.taxfilemanagementapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
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
    Customer selectedCustomer;
    String status;
    String[] statusOptions = {"AWAITED", "FAILEDTOREACH", "ONBOARDED", "INPROCESS", "COMPLETED","DENIED"};
    int position = -1;

    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationClient;
    private LatLng currentLocation;
    boolean markerAdded = false;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (fineLocationGranted != null && fineLocationGranted || coarseLocationGranted != null && coarseLocationGranted) {
                    fetchLastLocation();
                }else{
                    Toast.makeText(this, "Location Access Denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestPermissionLauncher.launch(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
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
            selectedCustomer = customer;
            preLoadField();
            setCustomerLocation();
        }));
    }

    public void updateCustomer(View view) {
        setValue();

        selectedCustomer.setStatus(status);
        userServices.updateCustomer(selectedCustomer, new UserServices.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                runOnUiThread(()-> Toast.makeText(CustomerDetailActivity.this, "Status Updated!", Toast.LENGTH_SHORT).show());
                Intent intent = new Intent();
                intent.putExtra("id", selectedCustomer.uid);
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
        nameField.setText(String.format("Name: %s", selectedCustomer.name));
        userNameField.setText(String.format("Username: %s", selectedCustomer.userName));
        emailField.setText(String.format("Email: %s", selectedCustomer.email));
        phoneField.setText(String.format("Phone: %s", selectedCustomer.phone));
        companyField.setText(String.format("Company: %s", selectedCustomer.companyTitle));
        websiteField.setText(String.format("Website: %s", selectedCustomer.website));
        suiteField.setText(String.format("Suite: %s", selectedCustomer.address.suite));
        streetField.setText(String.format("Street: %s", selectedCustomer.address.street));
        cityField.setText(String.format("City: %s", selectedCustomer.address.city));
        provinceField.setText(String.format("Province: %s", selectedCustomer.address.province));
        postalField.setText(String.format("Postal Code: %s", selectedCustomer.address.zipcode));
        int pos = new ArrayList<>(Arrays.asList(statusOptions)).indexOf(selectedCustomer.status);
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

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    // Move the camera to the current location
                    if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        setCustomerLocation();
    }

    private void setCustomerLocation(){
        if(mMap != null && selectedCustomer != null){
            if(!markerAdded){
                LatLng customerLocation = new LatLng(selectedCustomer.address.geo.lat, selectedCustomer.address.geo.lon);
                mMap.addMarker(new MarkerOptions().position(customerLocation).title(selectedCustomer.name));
            }
            markerAdded = true;
        }
    }
}