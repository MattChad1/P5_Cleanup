package com.cleanup.todoc.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Project {

    @PrimaryKey(autoGenerate = true)
    private final long id;
    @NonNull
    private final String name;
    @ColorInt
    private final Integer color; // The hex (ARGB) code of the color associated to the project

    public Project(long id, @NonNull String name, @ColorInt Integer color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public Integer getColor() {
        return color;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
