package com.example.taxfilemanagementapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(Customer customer);

    @Insert
    void insertAll(Customer... customers);

    @Query("SELECT * FROM Customer")
    List<Customer> getAllCustomers();

    @Query("SELECT * FROM Customer WHERE uid = :userid")
    Customer getCustomerById(int userid);

    @Query("SELECT * FROM Customer WHERE userName = :name")
    Customer getCustomerByUsername(String name);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);
}
