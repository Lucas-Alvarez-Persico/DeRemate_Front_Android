package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etContrasena;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.et_nombre);
        etEmail = findViewById(R.id.et_email);
        etContrasena = findViewById(R.id.et_contrasena);
        btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVolver= findViewById(R.id.btn_volver);

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String email = etEmail.getText().toString();
            String contrasena = etContrasena.getText().toString();

            if (!nombre.isEmpty() && !email.isEmpty() && !contrasena.isEmpty()) {
                // Aquí puedes enviar la información al servidor o guardarla en la base de datos local
                Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

                // Regresar a la pantalla de login
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
