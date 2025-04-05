package com.example.deremate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.R;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView tvOrderId = findViewById(R.id.tv_order_id);
        TextView tvOrderAddress = findViewById(R.id.tv_order_address);
        TextView tvOrderState = findViewById(R.id.tv_order_state);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Obtener datos del intent
        long orderId = getIntent().getLongExtra("order_id", -1);
        String orderAddress = getIntent().getStringExtra("order_address");
        boolean orderState = getIntent().getBooleanExtra("order_state", false);

        tvOrderId.setText("ID: " + orderId);
        tvOrderAddress.setText("Dirección: " + orderAddress);
        tvOrderState.setText("Estado: " + orderState);

        btnVolver.setOnClickListener(v -> finish());
    }
}
