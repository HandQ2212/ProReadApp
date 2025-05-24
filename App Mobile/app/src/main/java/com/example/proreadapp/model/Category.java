package com.example.proreadapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String name;
}

