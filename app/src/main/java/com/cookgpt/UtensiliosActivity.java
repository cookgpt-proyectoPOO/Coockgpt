package com.cookgpt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import androidx.room.Room;
import java.util.List;

public class UtensiliosActivity extends AppCompatActivity {

    AppDatabase db;
    LinearLayout layoutUtensilios;
    Button btnGuardar, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utensilios);

        layoutUtensilios = findViewById(R.id.layoutUtensilios);
        btnGuardar = findViewById(R.id.btnGuardarUtensilios);
        btnVolver = findViewById(R.id.btnVolverIngredientes);

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "cookgpt-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        cargarUtensilios();

        btnGuardar.setOnClickListener(v -> {
            guardarUtensiliosSeleccionados();
            Toast.makeText(this, "Utensilios guardados correctamente", Toast.LENGTH_SHORT).show();
        });

        btnVolver.setOnClickListener(v -> {
            Intent i = new Intent(UtensiliosActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void cargarUtensilios() {
        List<Utensilio> utensilios = db.utensilioDao().getAll();

        for (Utensilio utensilio : utensilios) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(utensilio.nombre);
            checkBox.setChecked(utensilio.disponible);
            checkBox.setTag(utensilio.id);

            layoutUtensilios.addView(checkBox);
        }
    }

    private void guardarUtensiliosSeleccionados() {
        for (int i = 0; i < layoutUtensilios.getChildCount(); i++) {
            if (layoutUtensilios.getChildAt(i) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) layoutUtensilios.getChildAt(i);
                int utensilioId = (int) checkBox.getTag();

                List<Utensilio> utensilios = db.utensilioDao().getAll();
                for (Utensilio u : utensilios) {
                    if (u.id == utensilioId) {
                        u.disponible = checkBox.isChecked();
                        db.utensilioDao().actualizar(u);
                        break;
                    }
                }
            }
        }
    }
}