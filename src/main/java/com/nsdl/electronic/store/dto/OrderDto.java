package com.nsdl.electronic.store.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 public class OrderDto {
	private String orderId;
	private String orderStatus="PENDING";
    private String paymentStatus="NOT PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate ;
    private Date deliveredDate;
    private UserDto user;
   List<OrderItemDto> orderItems=new ArrayList<>();
}
