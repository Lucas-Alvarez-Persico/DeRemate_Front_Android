package com.example.deremate.data.repository.order;

import com.example.deremate.data.model.Order;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;


public interface OrderRepository {

    void getAllOrders(RepositoryCallback<List<Order>> callback, String token);
    //Order getOrderById();

}
