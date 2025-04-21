package com.example.deremate.data.repository.UserRepository;

import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.utils.RepositoryCallback;

import com.example.deremate.data.model.UserDTO;

public interface UserRepository {

    void getUser(RepositoryCallback<UserDTO> callback, String token);
}
