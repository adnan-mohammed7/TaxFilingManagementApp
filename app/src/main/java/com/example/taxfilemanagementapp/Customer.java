package com.example.taxfilemanagementapp;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "userName", unique = true)})
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;
    public String userName;
    public String email;
    public String phone;
    public String companyTitle;
    public String website;

    @Embedded
    public Address address;

    public Customer(String name, String userName, String email, String phone, String companyTitle, String website, Address address){
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.companyTitle = companyTitle;
        this.website = website;
        this.address = address;
    }

}
