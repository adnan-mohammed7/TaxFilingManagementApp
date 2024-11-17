package com.example.taxfilemanagementapp;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;
    public String userName;
    public String email;

    @Embedded
    public Address address;

    public String phone;
    public String website;

    @Embedded
    public Company company;
}
