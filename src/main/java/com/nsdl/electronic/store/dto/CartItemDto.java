package com.nsdl.electronic.store.dto;

import com.nsdl.electronic.store.model.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CartItemDto {
	
	private int cartItemId;
	private ProductDto product;
	private int quentity;
	private int totalPrice;
	
	

}
