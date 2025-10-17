package com.cookgpt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import androidx.room.Room;
import android.content.Intent;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    EditText input;
    Button btnGuardar, btnUtensilios, btnSugerencias;
    TextView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputIngrediente);
        btnGuardar = findViewById(R.id.btnGuardar);
        lista = findViewById(R.id.txtLista);
        btnUtensilios = findViewById(R.id.btnUtensilios);
        btnSugerencias = findViewById(R.id.btnSugerencias);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cookgpt-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        inicializarDatos();

        btnGuardar.setOnClickListener(v -> {
            String nombre = input.getText().toString().trim();
            if (!nombre.isEmpty()) {
                Ingrediente ing = new Ingrediente();
                ing.nombre = nombre;
                ing.disponible = true;
                db.ingredienteDao().insertar(ing);
                mostrarIngredientes();
                input.setText("");
                Toast.makeText(this, "Ingrediente guardado: " + nombre, Toast.LENGTH_SHORT).show();
            }
        });

        btnUtensilios.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, UtensiliosActivity.class);
            startActivity(i);
        });

        btnSugerencias.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SugerenciasActivity.class);
            startActivity(i);
        });

        mostrarIngredientes();
    }

    private void inicializarDatos() {
        List<Receta> recetasExistentes = db.recetaDao().getAll();
        if (recetasExistentes.isEmpty()) {
            agregarRecetasDeEjemplo();
        }

        List<Utensilio> utensiliosExistentes = db.utensilioDao().getAll();
        if (utensiliosExistentes.isEmpty()) {
            agregarUtensiliosBasicos();
        }
    }

    private void agregarRecetasDeEjemplo() {
        // Huevos con tocino
        Receta r1 = new Receta();
        r1.nombre = "Huevos con Tocino";
        r1.ingredientes = "huevos,tocino,sal,pimienta";
        r1.utensiliosNecesarios = "sartén";
        r1.pasos = "\n1. Calentar sartén por 2 min a fuego medio\n2. Freír tocino hasta dorar\n3. Agregar huevos\n4. Condimentar al gusto";
        r1.tiempo = 10;
        db.recetaDao().insertar(r1);

        // Cereales con leche
        Receta r2 = new Receta();
        r2.nombre = "Cereales con Leche";
        r2.ingredientes = "cereales,leche";
        r2.utensiliosNecesarios = "";
        r2.pasos = "\n1. Servir cereales en un tazón\n2. Agregar leche fría";
        r2.tiempo = 2;
        db.recetaDao().insertar(r2);

        // Café con tostadas
        Receta r3 = new Receta();
        r3.nombre = "Café con Tostadas";
        r3.ingredientes = "cafe,pan,mantequilla";
        r3.utensiliosNecesarios = "microondas";
        r3.pasos = "\n1. Preparar café\n2. Tostar pan en microondas\n3. Untar mantequilla";
        r3.tiempo = 5;
        db.recetaDao().insertar(r3);

        // Tortilla francesa
        Receta r4 = new Receta();
        r4.nombre = "Tortilla Francesa";
        r4.ingredientes = "huevos,sal,aceite";
        r4.utensiliosNecesarios = "sartén";
        r4.pasos = "\n1. Batir huevos con sal\n2. Calentar aceite en sartén\n3. Verter huevos y doblar";
        r4.tiempo = 8;
        db.recetaDao().insertar(r4);

        // Sopa instantánea
        Receta r5 = new Receta();
        r5.nombre = "Sopa Instantánea";
        r5.ingredientes = "sopa instantanea,agua";
        r5.utensiliosNecesarios = "olla,microondas";
        r5.pasos = "\n1. Hervir agua\n2. Agregar sopa instantánea\n3. Cocinar 3 minutos";
        r5.tiempo = 5;
        db.recetaDao().insertar(r5);
    }

    private void agregarUtensiliosBasicos() {
        String[] nombresUtensilios = {"Sartén", "Olla", "Microondas", "Batidora", "Cuchillo", "Tabla de cortar"};

        for (String nombre : nombresUtensilios) {
            Utensilio u = new Utensilio();
            u.nombre = nombre;
            u.disponible = false;
            db.utensilioDao().insertar(u);
        }
    }

    private void mostrarIngredientes() {
        List<Ingrediente> ingredientes = db.ingredienteDao().getAll();
        StringBuilder sb = new StringBuilder("Ingredientes disponibles:\n");
        if (ingredientes.isEmpty()) {
            sb.append("No hay ingredientes registrados");
        } else {
            for (Ingrediente i : ingredientes) {
                if (i.disponible) {
                    sb.append("✓ ").append(i.nombre).append("\n");
                }
            }
        }
        lista.setText(sb.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarIngredientes();
    }
}