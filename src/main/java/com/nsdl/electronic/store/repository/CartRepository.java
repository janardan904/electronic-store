package com.nsdl.electronic.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.Cart;
import com.nsdl.electronic.store.model.User;

public interface CartRepository extends JpaRepository<Cart,String> {
	
	Optional<Cart> findByUser(User user);

}
