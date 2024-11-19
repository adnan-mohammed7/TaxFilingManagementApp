package com.example.taxfilemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminHomeActivity extends AppCompatActivity {

    FloatingActionButton logoutBtn;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    UserServices userServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        userServices = UserServices.getInstance(AdminHomeActivity.this);

        recyclerView = findViewById(R.id.homeAllCustomerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminHomeActivity.this));

        logoutBtn = findViewById(R.id.adminlogoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        userServices.getAllCustomers(customers -> {
            runOnUiThread(()->{
                adapter = new RecyclerAdapter(customers);
                recyclerView.setAdapter(adapter);
            });
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Customer removeCustomer = adapter.getCustomerAtPosition(pos);

                new AlertDialog.Builder(AdminHomeActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this customer")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            userServices.deleteCustomer(removeCustomer, new UserServices.OperationCallback() {
                                @Override
                                public void onOperationCompleted() {
                                    runOnUiThread(()-> {
                                        Toast.makeText(AdminHomeActivity.this, "Customer Deleted!", Toast.LENGTH_SHORT).show();
                                        adapter.removeCustomerAtPosition(pos);
                                        adapter.notifyItemRemoved(pos);
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    System.out.println(e.toString());
                                }
                            });}))
                        .setNegativeButton("No", ((dialog, which) -> {
                            adapter.notifyItemChanged(pos);
                        }))
                        .show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}