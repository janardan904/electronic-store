package com.nsdl.electronic.store.service;

import java.util.List;
import java.util.Set;

import com.nsdl.electronic.store.dto.CreateOrderRequest;
import com.nsdl.electronic.store.dto.OrderDto;
import com.nsdl.electronic.store.dto.PageableResponse;

public interface OrderService {

	
	//create order
	OrderDto createOrder(CreateOrderRequest orderDto);
	//remove order
	void removeOrder(String orderId);
	
	//get orders
	List<OrderDto> getOrdersOfUsers(String userId);
	//get orders of users
	PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
}
