package com.example.taxfilemanagementapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AdminDao {
    @Insert
    void insert(Admin admin);

    @Insert
    void insertAll(Admin... admin);

    @Query("SELECT * FROM Admin")
    List<Admin> getAllAdmins();

    @Query("SELECT * FROM Admin WHERE uid =:id")
    Admin getAdminById(int id);

    @Query("SELECT * FROM Admin WHERE username =:name")
    Admin getAdminByUsername(String name);

    @Update
    void update(Admin admin);

    @Delete
    void delete(Admin admin);
}
