package com.cookgpt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleRecetaActivity extends AppCompatActivity {
    TextView txtNombre, txtPasos, txtTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        txtNombre = findViewById(R.id.txtNombre);
        txtPasos = findViewById(R.id.txtPasos);
        txtTiempo = findViewById(R.id.txtTiempo);

        String nombre = getIntent().getStringExtra("nombre");
        String pasos = getIntent().getStringExtra("pasos");
        int tiempo = getIntent().getIntExtra("tiempo", 0);

        txtNombre.setText("Receta: " + nombre);
        txtPasos.setText("Pasos: " + pasos);
        txtTiempo.setText("Tiempo: " + tiempo + " min");
    }
}