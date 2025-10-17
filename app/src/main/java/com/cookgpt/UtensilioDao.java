package com.cookgpt;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UtensilioDao {
    @Insert
    void insertar(Utensilio u);

    @Update
    void actualizar(Utensilio u);

    @Query("SELECT * FROM Utensilio")
    List<Utensilio> getAll();

    @Query("DELETE FROM Utensilio")
    void borrarTodo();

    @Query("SELECT * FROM Utensilio WHERE disponible = 1")
    List<Utensilio> getDisponibles();
}
