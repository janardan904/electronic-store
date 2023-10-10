package com.nsdl.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderItemDto {
	private int orderItemId;
	private int quentity;
	private int totalPrice;
	private ProductDto product;
	//private OrderDto order;
	
}
