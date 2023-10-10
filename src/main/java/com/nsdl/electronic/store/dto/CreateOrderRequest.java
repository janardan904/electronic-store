package com.nsdl.electronic.store.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

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
public class CreateOrderRequest {
	
	@NotBlank(message = "cart id is required")
	private String cartId;
	@NotBlank(message = "user id is required")
	private String userId;

	private String orderStatus = "PENDING";
	private String paymentStatus = "NOT PAID";

	@NotBlank(message = "billing address is required")
	private String billingAddress;
	@NotBlank(message = "billing phone is required")
	private String billingPhone;
	@NotBlank(message = "billing name required")
	private String billingName;
	List<OrderItemDto> orderItems=new ArrayList<>();
}
