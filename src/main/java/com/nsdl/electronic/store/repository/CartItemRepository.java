package com.nsdl.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>{

}
