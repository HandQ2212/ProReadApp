package com.example.proreadapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    public Category(@NonNull String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
