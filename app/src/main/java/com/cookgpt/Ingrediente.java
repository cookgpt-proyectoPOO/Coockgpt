package com.cookgpt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingrediente {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public boolean disponible;
}