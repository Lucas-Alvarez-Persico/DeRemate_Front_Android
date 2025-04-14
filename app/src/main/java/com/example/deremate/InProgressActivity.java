package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.delivery.DeliveryRepository;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InProgressActivity extends BaseActivity {

    @Inject
    DeliveryRepository deliveryRepository;

    @Inject
    TokenRepository tokenRepository;

    private TextView tvId, tvClient, tvAddress, tvStatus, tvStartTime, tvEndTime;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_in_progress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Referencias a los TextViews y el botón
        tvId = findViewById(R.id.tv_in_progress_id);
        tvClient = findViewById(R.id.tv_client);
        tvAddress = findViewById(R.id.tv_address);
        tvStatus = findViewById(R.id.tv_status);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);

        // Cargar datos
        String token = tokenRepository.getToken();
        if (token != null) {
            deliveryRepository.getCurrentDeliveriesByStatus("EN_CAMINO", new RepositoryCallback<List<DeliveryDTO>>() {
                @Override
                public void onSuccess(List<DeliveryDTO> deliveries) {
                    runOnUiThread(() -> {
                        if (!deliveries.isEmpty()) {
                            DeliveryDTO delivery = deliveries.get(0);

                            // Mostrar datos
                            tvId.setText("#" + delivery.getOrder().getId());
                            tvClient.setText(delivery.getOrder().getClient());
                            tvAddress.setText(delivery.getOrder().getAddress());
                            tvStatus.setText(delivery.getStatus().name());
                            tvStartTime.setText(delivery.getStartTime());

                        } else {
                            Toast.makeText(InProgressActivity.this, "No hay entregas activas.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> Toast.makeText(InProgressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                }
            }, token);
        } else {
            Toast.makeText(this, "Token no disponible", Toast.LENGTH_SHORT).show();
        }

    }
}
