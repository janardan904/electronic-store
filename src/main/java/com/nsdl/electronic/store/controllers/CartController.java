package com.nsdl.electronic.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.electronic.store.dto.AddItemToCartRequest;
import com.nsdl.electronic.store.dto.CartDto;
import com.nsdl.electronic.store.model.Cart;
import com.nsdl.electronic.store.response.ApiResponse;
import com.nsdl.electronic.store.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cart")
@Api(value="userController",description="Rest Api related to perform Cart operation!")
public class CartController {
	
	@Autowired
	private CartService cartservice;
	
	
	@PostMapping("/{userId}")
	@ApiOperation(value="add item into cart using userid!")
	public ResponseEntity<CartDto> addIttemToCart(@RequestBody AddItemToCartRequest request,
			                                      @PathVariable String userId){
		CartDto cartDto=cartservice.addItemToCart(userId, request);											
		
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("{userId}/items/{itemId}")
	@ApiOperation(value="delete item from cart using user id and itemid!")
	public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable String userId,
			                                             @PathVariable int itemId){
		
		cartservice.removeFromCartItem(userId, itemId);
		
		ApiResponse response=ApiResponse.builder()
				.message("item is removed")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{userId}")
	@ApiOperation(value="clear cart using userid!")
	public ResponseEntity<ApiResponse>clearCart(@PathVariable String userId){
		
		cartservice.clearCart(userId);
		
		ApiResponse response=ApiResponse.builder()
				.message("item is removed")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		
	}
	@GetMapping("/{userId}")
	@ApiOperation(value="get cart using userid!")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId){
		
		CartDto cartDto=cartservice.getCartByUser(userId);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
		
	}

}
