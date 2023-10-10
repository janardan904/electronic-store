package com.nsdl.electronic.store.service.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.dto.CreateOrderRequest;
import com.nsdl.electronic.store.dto.OrderDto;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.exceptions.BadApiRequest;
import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.helper.Helper;
import com.nsdl.electronic.store.model.Cart;
import com.nsdl.electronic.store.model.CartItem;
import com.nsdl.electronic.store.model.Order;
import com.nsdl.electronic.store.model.OrderItem;
import com.nsdl.electronic.store.model.User;
import com.nsdl.electronic.store.repository.CartRepository;
import com.nsdl.electronic.store.repository.OrderRepository;
import com.nsdl.electronic.store.repository.UserRepository;
import com.nsdl.electronic.store.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

	Logger logger=LoggerFactory.getLogger(CartServiceImpl.class);
	@Autowired
	private  CartRepository cartRepository ;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private OrderRepository  orderRepository;
	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		    String userId = orderDto.getUserId();
	        String cartId = orderDto.getCartId();
	        //fetch user
	        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
	        //fetch cart
	        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not found on server !!"));
             System.out.println("Cart id:"+cart.getCartId());
             System.out.println("cart data:"+cart.getItems());
             List<CartItem> cartItems=cart.getItems();
             if (cartItems.size()<0) {
 	            throw new BadApiRequest("Invalid number of items in cart !!");

 	        }

 	        //other checks

 	        Order order = Order.builder()
 	                .billingName(orderDto.getBillingName())
 	                .billingPhone(orderDto.getBillingPhone())
 	                .billingAddress(orderDto.getBillingAddress())
 	                .orderDate(new Date())
 	                .deliveredDate(null)
 	                .paymentStatus(orderDto.getPaymentStatus())
 	                .orderStatus(orderDto.getOrderStatus())
 	                .orderId(UUID.randomUUID().toString())
 	                .user(user)
 	               
 	                .build();
 	        
             //List<CartItem> cartItems=cart.getItems();
             System.out.println("cartItems:"+cartItems.toString());
             AtomicReference<Integer> orderAmount = new AtomicReference<Integer>(0);
             List<OrderItem> orderItems=cartItems.stream().map(cartItem->{
            	 OrderItem orderItem=OrderItem.builder()
            			            .quentity(cartItem.getQuentity())
            			            .product(cartItem.getProduct())
            			            .totalPrice(cartItem.getQuentity()*cartItem.getProduct().getDiscountedPrice())
            			            
            			            .build();
            	 orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            	 System.out.println("Order item data:"+orderItem.toString());
 	            return orderItem;
            			            
             }).collect(Collectors.toList());
             
            System.out.println("orderitems list of data:"+orderItems); 
            order.setOrderItems(orderItems);
            order.setOrderAmount(orderAmount.get());
	        
            cart.getItems().clear();
	        cartRepository.save(cart);
	        Order savedOrder = orderRepository.save(order);
	        
	        return mapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order id not found of given id!"));
               orderRepository.delete(order);
	}

	@Override
	public List<OrderDto> getOrdersOfUsers(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found given id!"));
		List<Order> orders=orderRepository.findByUser(user);
		
		List<OrderDto>orderDto=  orders.stream().map(order-> mapper.map(order,OrderDto.class)).collect(Collectors.toList());
		return orderDto;
	}

	@Override
	public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Order> page=orderRepository.findAll(pageable);
		return Helper.getPageableResponse(page,OrderDto.class);
	}

}
