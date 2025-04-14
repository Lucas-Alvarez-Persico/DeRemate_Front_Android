package com.example.deremate;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvDeliveryId = findViewById(R.id.tv_delivery_id);
        TextView tvOrderAddress = findViewById(R.id.tv_order_address);
        TextView tvDeliveryState = findViewById(R.id.tv_delivery_state);
        TextView tvClient = findViewById(R.id.tv_order_client);
        TextView tvPackageLocation = findViewById(R.id.tv_package_location);
        TextView tvStartTime = findViewById(R.id.tv_start_time);
        TextView tvEndTime = findViewById(R.id.tv_end_time);
        TextView tvTimeDifference = findViewById(R.id.tv_time_difference);
        Button btnVolver = findViewById(R.id.btn_volver);

        // Obtener datos del intent
        String mode = getIntent().getStringExtra("mode"); // "orden" o "entrega"
        long orderId = getIntent().getLongExtra("delivery_id", -1);
        String orderAddress = getIntent().getStringExtra("delivery_address");
        String deliveryStatus = getIntent().getStringExtra("delivery_status");
        String client = getIntent().getStringExtra("delivery_client");

        tvDeliveryId.setText("ID: " + orderId);
        tvOrderAddress.setText("Dirección: " + orderAddress);
        String estadoTexto = "Estado: ";
        SpannableString spannable = new SpannableString(estadoTexto + deliveryStatus);
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.violeta1)),
                estadoTexto.length(), // inicio del color (justo después de "Estado: ")
                spannable.length(),   // hasta el final
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvDeliveryState.setText(spannable);

        tvClient.setText("Cliente: " + client);

        if ("orden".equals(mode)) {
            tvTitle.setText("Detalle de la Orden");
            String packageLocation = getIntent().getStringExtra("delivery_package");
            tvPackageLocation.setText("Ubicación del paquete: " + packageLocation);
            tvClient.setVisibility(View.VISIBLE);
            tvPackageLocation.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText("Detalle de la Entrega");

            String startTime = getIntent().getStringExtra("delivery_start_time");
            String endTime = getIntent().getStringExtra("delivery_end_time");
            String timeDifference = getIntent().getStringExtra("delivery_time_difference");
            tvTimeDifference.setText("Tiempo de entrega: " + timeDifference);
            tvTimeDifference.setVisibility(View.VISIBLE);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

            if (startTime != null && !startTime.isEmpty()) {
                try {
                    String cleanedStart = startTime.replace("T", " ");
                    if (cleanedStart.contains(".")) {
                        cleanedStart = cleanedStart.substring(0, cleanedStart.indexOf("."));
                    }

                    Date date = inputFormat.parse(cleanedStart);
                    String formatted = outputFormat.format(date);
                    tvStartTime.setText("Hora de Inicio: " + formatted);
                } catch (Exception e) {
                    tvStartTime.setText("Hora de Inicio: " + startTime);
                }
                tvStartTime.setVisibility(View.VISIBLE);
            }

            if (endTime != null && !endTime.isEmpty()) {
                try {
                    String cleanedEnd = endTime.replace("T", " ");
                    if (cleanedEnd.contains(".")) {
                        cleanedEnd = cleanedEnd.substring(0, cleanedEnd.indexOf("."));
                    }

                    Date date = inputFormat.parse(cleanedEnd);
                    String formatted = outputFormat.format(date);
                    tvEndTime.setText("Hora de Fin: " + formatted);
                } catch (Exception e) {
                    tvEndTime.setText("Hora de Fin: " + endTime);
                }
                tvEndTime.setVisibility(View.VISIBLE);
            }
        }

        btnVolver.setOnClickListener(v -> finish());
    }
}
