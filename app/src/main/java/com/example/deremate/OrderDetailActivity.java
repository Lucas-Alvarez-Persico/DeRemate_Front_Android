package com.example.deremate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.R;
import com.example.deremate.data.model.DeliveryStatus;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView tvDeliveryId = findViewById(R.id.tv_delivery_id);
        TextView tvOrderAddress = findViewById(R.id.tv_order_address);
        TextView tvDeliveryState = findViewById(R.id.tv_delivery_state);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Obtener datos del intent
        long orderId = getIntent().getLongExtra("delivery_id", -1);
        String orderAddress = getIntent().getStringExtra("delivery_address");
        String deliveryStatus = getIntent().getStringExtra("delivery_status");

        tvDeliveryId.setText("ID: " + orderId);
        tvOrderAddress.setText("Dirección: " + orderAddress);
        tvDeliveryState.setText("Estado: " + deliveryStatus);


        btnVolver.setOnClickListener(v -> finish());
    }
}
