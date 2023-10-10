package com.nsdl.electronic.store.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.Order;
import com.nsdl.electronic.store.model.User;

public interface OrderRepository extends JpaRepository<Order,String> {

	
	List<Order> findByUser(User user);
}
