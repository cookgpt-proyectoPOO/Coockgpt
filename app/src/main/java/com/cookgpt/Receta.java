package com.cookgpt;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Receta {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String ingredientes;
    public String utensiliosNecesarios;
    public String pasos;
    public int tiempo;
}