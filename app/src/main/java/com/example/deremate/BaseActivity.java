package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            Log.d("NAV_CLICKED", String.valueOf(id));

            Class<?> targetActivity = null;

            if (id == R.id.nav_inventory) {
                targetActivity = MainActivity.class;
            }
             else if (id == R.id.nav_in_progress) {
                 targetActivity = InProgressActivity.class;
             }
            else if (id == R.id.nav_history) {
                targetActivity = HistoryActivity.class;
            }
            // else if (id == R.id.nav_profile) {
            //     targetActivity = ProfileActivity.class;
            // }

            if (targetActivity != null && !this.getClass().equals(targetActivity)) {
                Intent intent = new Intent(this, targetActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return true;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        if (this instanceof MainActivity) {
            nav.setSelectedItemId(R.id.nav_inventory);
        }
        else if (this instanceof InProgressActivity) {
             nav.setSelectedItemId(R.id.nav_in_progress);
         }
        else if (this instanceof HistoryActivity) {
            nav.setSelectedItemId(R.id.nav_history);
        }
        // else if (this instanceof ProfileActivity) {
        //     nav.setSelectedItemId(R.id.nav_profile);
        // }
    }


}
