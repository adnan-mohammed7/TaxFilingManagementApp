package com.example.taxfilemanagementapp;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = "userName", unique = true)})
public class Customer implements Serializable{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String name;
    public String userName;
    public String password;
    public String email;
    public String phone;
    public String companyTitle;
    public String website;
    public String status;

    @Embedded
    public Address address;

    public Customer(String name, String userName, String password, String email, String phone, String companyTitle, String website, String status, Address address){
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.companyTitle = companyTitle;
        this.website = website;
        this.status = status;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
