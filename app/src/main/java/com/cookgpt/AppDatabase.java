package com.cookgpt;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Ingrediente.class, Utensilio.class, Receta.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IngredienteDao ingredienteDao();
    public abstract UtensilioDao utensilioDao();
    public abstract RecetaDao recetaDao();
}