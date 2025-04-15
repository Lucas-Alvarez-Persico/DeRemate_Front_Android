package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deremate.data.repository.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends BaseActivity {

    @Inject
    TokenRepository tokenRepository;
    private TextView tvNombre, tvApellido, tvCorreo;
    private LinearLayout btnLogout;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvNombre = findViewById(R.id.tv_nombre);
        tvApellido = findViewById(R.id.tv_apellido);
        tvCorreo = findViewById(R.id.tv_correo);
        btnLogout = findViewById(R.id.btn_logout);

        // Cargar datos (simulado)
        tvNombre.setText("Adrián");
        tvApellido.setText("Martínez Serra");
        tvCorreo.setText("AdrianMSerra@gmail.com");

        btnLogout.setOnClickListener(v -> {

            tokenRepository.clearToken();
            Log.e("HOLAAAAAAA", "HOLAAAAAAA");
            // Verificamos en Logcat si el token se borró
            String token = tokenRepository.getToken();
            Log.d("TOKEN_LOGOUT", "Token después de logout: " + token); // Debería ser null

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redireccionamos al Login y limpiamos el backstack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
