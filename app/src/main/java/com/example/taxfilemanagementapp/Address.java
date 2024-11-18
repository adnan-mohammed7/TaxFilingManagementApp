package com.example.taxfilemanagementapp;

import androidx.room.Embedded;


public class Address {
    String street;
    String suite;
    String city;
    String province;
    String zipcode;

    @Embedded
    Geo geo;

    public Address(String street, String suite, String city, String province, String zipcode, Geo geo){
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.geo = geo;
    }
}
