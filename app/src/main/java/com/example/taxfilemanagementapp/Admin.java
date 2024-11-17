package com.example.taxfilemanagementapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Admin {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String username;
    public String password;

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

}
