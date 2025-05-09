package com.example.deremate.di;

import com.example.deremate.data.repository.UserRepository.UserRepository;
import com.example.deremate.data.repository.UserRepository.UserRepositoryImpl;
import com.example.deremate.data.repository.delivery.DeliveryRepository;
import com.example.deremate.data.repository.delivery.DeliveryRepositoryImpl;
import com.example.deremate.data.repository.order.OrderRepository;
import com.example.deremate.data.repository.order.OrderRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract OrderRepository provideOrderRepository(OrderRepositoryImpl implementation);

    @Binds
    @Singleton
    public abstract DeliveryRepository provideDeliveryRepository(DeliveryRepositoryImpl implementation);

    @Binds
    @Singleton
    public abstract UserRepository provideUserRepository(UserRepositoryImpl implementation);
}
