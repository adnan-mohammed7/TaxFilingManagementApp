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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
