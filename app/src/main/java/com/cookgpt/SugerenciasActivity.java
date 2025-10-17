package com.cookgpt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import androidx.room.Room;
import java.util.List;
import java.util.ArrayList;

public class SugerenciasActivity extends AppCompatActivity {

    AppDatabase db;
    LinearLayout layoutRecetas;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencias);

        layoutRecetas = findViewById(R.id.layoutRecetas);
        btnVolver = findViewById(R.id.btnVolverIngredientes);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cookgpt-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        mostrarRecetasPosibles();

        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(SugerenciasActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void mostrarRecetasPosibles() {
        List<Ingrediente> ingredientesDisponibles = db.ingredienteDao().getDisponibles();
        List<Utensilio> utensiliosDisponibles = db.utensilioDao().getDisponibles();
        List<Receta> todasLasRecetas = db.recetaDao().getAll();

        List<String> nombresIngredientes = new ArrayList<>();
        for (Ingrediente ing : ingredientesDisponibles) {
            nombresIngredientes.add(ing.nombre.toLowerCase());
        }

        List<String> nombresUtensilios = new ArrayList<>();
        for (Utensilio ut : utensiliosDisponibles) {
            nombresUtensilios.add(ut.nombre.toLowerCase());
        }

        boolean hayRecetasPosibles = false;

        for (Receta receta : todasLasRecetas) {
            if (puedeHacerReceta(receta, nombresIngredientes, nombresUtensilios)) {
                hayRecetasPosibles = true;
                agregarRecetaAlLayout(receta);
            }
        }

        if (!hayRecetasPosibles) {
            TextView txtNoRecetas = new TextView(this);
            txtNoRecetas.setText("No hay recetas posibles con los ingredientes y utensilios disponibles.\n\nAgrega más ingredientes o marca los utensilios que tienes.");
            txtNoRecetas.setTextSize(16);
            txtNoRecetas.setPadding(0, 20, 0, 20);
            layoutRecetas.addView(txtNoRecetas);
        }
    }

    private boolean puedeHacerReceta(Receta receta, List<String> ingredientesDisponibles, List<String> utensiliosDisponibles) {
        String[] ingredientesNecesarios = receta.ingredientes.toLowerCase().split(",");
        for (String ingrediente : ingredientesNecesarios) {
            if (!ingredientesDisponibles.contains(ingrediente.trim())) {
                return false;
            }
        }

        if (receta.utensiliosNecesarios != null && !receta.utensiliosNecesarios.isEmpty()) {
            String[] utensiliosNecesarios = receta.utensiliosNecesarios.toLowerCase().split(",");
            for (String utensilio : utensiliosNecesarios) {
                if (!utensiliosDisponibles.contains(utensilio.trim())) {
                    return false;
                }
            }
        }

        return true;
    }

    private void agregarRecetaAlLayout(Receta receta) {
        Button btnReceta = new Button(this);
        btnReceta.setText(receta.nombre + " (" + receta.tiempo + " min)");
        btnReceta.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        btnReceta.setOnClickListener(v -> {
            Intent intent = new Intent(SugerenciasActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("nombre", receta.nombre);
            intent.putExtra("pasos", receta.pasos);
            intent.putExtra("tiempo", receta.tiempo);
            intent.putExtra("ingredientes", receta.ingredientes);
            startActivity(intent);
        });

        layoutRecetas.addView(btnReceta);
    }
}