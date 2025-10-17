package com.cookgpt;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface IngredienteDao {
    @Insert
    void insertar(Ingrediente ing);

    @Query("SELECT * FROM Ingrediente")
    List<Ingrediente> getAll();

    @Query("SELECT * FROM Ingrediente WHERE disponible = 1")
    List<Ingrediente> getDisponibles();
}