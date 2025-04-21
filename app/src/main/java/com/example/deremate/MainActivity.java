package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deremate.adapter.DeliveryAdapter;
import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.delivery.DeliveryRepository;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    @Inject
    DeliveryRepository deliveryRepository;
    @Inject
    TokenRepository tokenRepository;

    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;
    private TextView textDefault;

    @Override
    protected void onStart() {
        super.onStart();
            if (tokenRepository.getToken() == null) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        else {
            getDeliveries(tokenRepository.getToken());
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        tokenRepository.clearToken();
        Log.d("MainActivity", "Token eliminado al finalizar.");
    }*/

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        recyclerView = findViewById(R.id.rv_orders); // Podrías cambiar el ID a rv_deliveries si querés que refleje lo nuevo
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textDefault = findViewById(R.id.text_default);

        String token = tokenRepository.getToken();
        if (token != null) {
            getDeliveries(token);
        }
    }

    private void getDeliveries(String token){
        deliveryRepository.getCurrentDeliveriesByStatus("pendiente", new RepositoryCallback<List<DeliveryDTO>>() {
            @Override
            public void onSuccess(List<DeliveryDTO> deliveries) {
                runOnUiThread(() -> {
                    if(!deliveries.isEmpty()){
                        textDefault.setVisibility(View.GONE);
                        deliveryAdapter.updateDeliveries(deliveries);
                    }else{
                        textDefault.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        }, token);

        deliveryAdapter = new DeliveryAdapter(List.of(), delivery -> {
            Intent intent = new Intent(MainActivity.this, DeliveryDetailActivity.class);
            intent.putExtra("mode", "orden");
            intent.putExtra("delivery_id", delivery.getId());
            intent.putExtra("delivery_status", delivery.getStatus().name());
            intent.putExtra("delivery_address", delivery.getOrder().getAddress());
            intent.putExtra("delivery_package", delivery.getOrder().getPackageLocation());
            intent.putExtra("delivery_client", delivery.getOrder().getClient());
            startActivity(intent);
        });
        recyclerView.setAdapter(deliveryAdapter);
    }

}

