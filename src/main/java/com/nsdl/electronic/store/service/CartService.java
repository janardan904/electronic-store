package com.nsdl.electronic.store.service;

import com.nsdl.electronic.store.dto.AddItemToCartRequest;
import com.nsdl.electronic.store.dto.CartDto;

public interface CartService {
	
	public CartDto addItemToCart(String userId,AddItemToCartRequest request); 
	
	public void removeFromCartItem(String userId,int cartItem);
	
	public void clearCart(String userId);
	public CartDto getCartByUser(String userId);
	
	

}
