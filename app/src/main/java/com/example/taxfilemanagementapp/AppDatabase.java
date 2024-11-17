package com.example.taxfilemanagementapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Admin.class, Customer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
}