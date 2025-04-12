package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentLayoutId(); // cada activity te va a pasar su layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seteamos un layout genérico que tiene espacio para navbar + contenido dinámico
        setContentView(R.layout.activity_base);

        // Inflamos el contenido específico de la Activity hija
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(getContentLayoutId(), contentFrame, true);

        // Configuramos el BottomNavigationView
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inventory) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_in_progress) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        if (this.getClass().equals(MainActivity.class)) {
            nav.setSelectedItemId(R.id.nav_in_progress);
        } else if (this.getClass().equals(MainActivity.class)) {
            nav.setSelectedItemId(R.id.nav_inventory);
        } else if (this.getClass().equals(HistoryActivity.class)) {
            nav.setSelectedItemId(R.id.nav_history);
        } else if (this.getClass().equals(MainActivity.class)) {
            nav.setSelectedItemId(R.id.nav_profile);
        }
    }
}
