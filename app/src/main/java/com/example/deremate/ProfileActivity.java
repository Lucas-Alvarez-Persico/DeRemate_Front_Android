package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deremate.data.model.UserDTO;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.UserRepository.UserRepository;
import com.example.deremate.utils.RepositoryCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends BaseActivity {

    @Inject
    TokenRepository tokenRepository;

    @Inject
    UserRepository userRepository;

    private TextView tvUserWelcome, tvNombre, tvCorreo;
    private LinearLayout btnLogout;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_profile; // El XML que compartiste
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvUserWelcome = findViewById(R.id.tv_user_welcome); // Obtener referencia del TextView de bienvenida
        tvNombre = findViewById(R.id.tv_nombre);
        tvCorreo = findViewById(R.id.tv_correo);
        btnLogout = findViewById(R.id.btn_logout);

        // Obtener el token
        String token = tokenRepository.getToken();

        if (token != null) {
            // Llamar al método getUser para obtener el perfil del usuario
            userRepository.getUser(new RepositoryCallback<UserDTO>() {
                @Override
                public void onSuccess(UserDTO userDTO) {
                    // Actualizamos la interfaz de usuario con los datos del perfil
                    tvUserWelcome.setText("Hola, " + userDTO.getName() + "!"); // Mostrar el nombre del usuario
                    tvNombre.setText(userDTO.getName());
                    tvCorreo.setText(userDTO.getUsername());  // Suponiendo que 'email' está en el DTO
                }

                @Override
                public void onError(String errorMessage) {
                    // Si hubo un error, mostramos un mensaje
                    Toast.makeText(ProfileActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }, token);
        } else {
            // Si no hay token, redirigir al login
            Toast.makeText(this, "Sesión no iniciada", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        }

        btnLogout.setOnClickListener(v -> {
            // Limpiar el token y redirigir al login
            tokenRepository.clearToken();
            String tokenAfterLogout = tokenRepository.getToken();  // Debería ser null

            Log.d("TOKEN_LOGOUT", "Token después de logout: " + tokenAfterLogout);

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        });
    }

    private void redirectToLogin() {
        // Redirigir al Login y limpiar el backstack
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

