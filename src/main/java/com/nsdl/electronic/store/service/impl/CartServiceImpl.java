package com.nsdl.electronic.store.service.impl;


import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.dto.AddItemToCartRequest;
import com.nsdl.electronic.store.dto.CartDto;
import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.model.Cart;
import com.nsdl.electronic.store.model.CartItem;
import com.nsdl.electronic.store.model.Product;
import com.nsdl.electronic.store.model.User;
import com.nsdl.electronic.store.repository.CartItemRepository;
import com.nsdl.electronic.store.repository.CartRepository;
import com.nsdl.electronic.store.repository.ProductRepository;
import com.nsdl.electronic.store.repository.UserRepository;
import com.nsdl.electronic.store.service.CartService;
@Service
public class CartServiceImpl implements CartService {

	Logger logger=LoggerFactory.getLogger(CartServiceImpl.class);
	@Autowired
	private ProductRepository ProductRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartItemRepository  cartItemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ModelMapper mapper;
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		
		int quentity=request.getQuentity();
		String productId=request.getProductId();
		Product product=ProductRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product id not found from database"));
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id not Found From Given Id"));
		logger.debug("user id from service :{}",user);
		Cart cart;
		try {
			cart=cartRepository.findByUser(user).get();
			
		}
		catch(NoSuchElementException e) {
			cart=new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
			
			
		}
		
		AtomicReference<Boolean> updated=new AtomicReference<>(true);
		List<CartItem>items=cart.getItems();
		System.out.println("cart items data:"+items);
		items =items.stream().map(item->{
			if(item.getProduct().getProductId().equals(productId)) {
				item.setQuentity(quentity);
				item.setProduct(product);
				item.setTotalPrice(quentity*product.getDiscountedPrice());
				updated.set(true);
				
			}
			return item;
		}).collect(Collectors.toList());
		
		//cart.setItems(updatedStreams);
		if(updated.get()) {
			CartItem cartitems=CartItem.builder()
					.quentity(quentity)
					.totalPrice(quentity*product.getDiscountedPrice())
					.cart(cart)
					.product(product)
					.build();
			 cart.getItems().add(cartitems);
			 logger.debug("cart items:",cartitems);
			 updated.set(true);
		}
		 cart.setUser(user);
		 Cart updatedcart=cartRepository.save(cart);
		 
		 return mapper.map(updatedcart,CartDto.class);
				
	}

	@Override
	public void removeFromCartItem(String userId, int cartItem) {
		CartItem cartItem1= cartItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("cart item id not found !!"));
		cartItemRepository.delete(cartItem1);
		

	}

	@Override
	public void clearCart(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Id not Found From Given Id"));	
       Cart   cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("user not founf for given if"));
	    cart.getItems().clear();
	    cartRepository.save(cart);
	}

	@Override
	public CartDto getCartByUser(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found for given user!"));
		Cart   cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("user not found for given id"));
		return mapper.map(cart,CartDto.class);
	}

}
