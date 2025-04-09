package com.example.deremate.data.repository.delivery;

import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

public interface DeliveryRepository {

    public void getCurrentDeliveriesByStatus(String status, RepositoryCallback<List<DeliveryDTO>> callback, String token);



}
