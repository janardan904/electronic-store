package com.nsdl.electronic.store.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.electronic.store.dto.CreateOrderRequest;
import com.nsdl.electronic.store.dto.OrderDto;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.response.ApiResponse;
import com.nsdl.electronic.store.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/order")
@Api(value="userController",description="Rest Api related to perform Order operation!")
public class OrderController {
   @Autowired
	private OrderService orderService ;
   
   @PostMapping
   @ApiOperation(value="create new order!")
	public ResponseEntity<OrderDto>createOrder(@Valid @RequestBody CreateOrderRequest request){
		OrderDto order=orderService.createOrder(request);
		return new ResponseEntity<OrderDto>(order,HttpStatus.CREATED);
		
	}
   @DeleteMapping("/{orderId}")
   @ApiOperation(value="delete oder using orderid!")
   public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId){
	   orderService.removeOrder(orderId);
	   ApiResponse response= ApiResponse.builder()
	  .status(HttpStatus.OK)
	  .message("order deleted successfully!")
	  .success(true)
	  .build();
	   return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	   
   }
   @GetMapping("/users/{userId}")
   @ApiOperation(value="get order details using userid!")
   public ResponseEntity<List<OrderDto>>getOrders(@PathVariable String userId ){
	List<OrderDto> orders=orderService.getOrdersOfUsers(userId);
	   
	   return new ResponseEntity<>(orders,HttpStatus.OK);
	   
   }
   
   @GetMapping
   @ApiOperation(value="get all orders!")
 	public ResponseEntity <PageableResponse<OrderDto>> getOrders(
 			                                          
 			                                          @RequestParam (value="pageNumber", defaultValue ="0",required = false)int pageNumber,
 			                                          @RequestParam (value="pageSize", defaultValue ="10",required = false )int pageSize,
 			                                          @RequestParam (value="sortBy", defaultValue ="billingName",required = false)String sortBy,
 			                                          @RequestParam (value="sortDir", defaultValue ="asc",required = false )String sortDir){
 PageableResponse<OrderDto> orders=orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
 	
 return new ResponseEntity<PageableResponse<OrderDto>>(orders,HttpStatus.OK);
 	}
}
