package com.example.deremate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText etUsuario = findViewById(R.id.et_usuario);
        EditText etContrasena = findViewById(R.id.et_contrasena);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnOlvidar = findViewById(R.id.btn_olvidar);
        Button btnRegistro = findViewById(R.id.btn_registro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String contrasena = etContrasena.getText().toString();

                if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Iniciando sesión.....", Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar lógica de autenticación con Firebase o API
                } else {
                    Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOlvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Redirigiendo a recuperación de contraseña...", Toast.LENGTH_SHORT).show();
                // Agregar intent para abrir la pantalla de recuperación
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Redirigiendo a registro...", Toast.LENGTH_SHORT).show();
                // Agregar intent para abrir la pantalla de registro
            }
        });
    }
}
