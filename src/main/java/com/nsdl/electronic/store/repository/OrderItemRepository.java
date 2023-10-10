package com.nsdl.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {

}
