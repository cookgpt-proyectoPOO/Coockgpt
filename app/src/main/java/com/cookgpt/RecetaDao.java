package com.cookgpt;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecetaDao {
    @Insert
    void insertar(Receta r);

    @Query("SELECT * FROM Receta")
    List<Receta> getAll();
}