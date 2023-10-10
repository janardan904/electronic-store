package com.nsdl.electronic.store.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nsdl.electronic.store.dto.OrderDto;
import com.nsdl.electronic.store.dto.OrderItemDto;
import com.nsdl.electronic.store.dto.ProductDto;

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
@Entity
@Table(name="orderItems")
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int orderItemId;
	private int quentity;
	private int totalPrice;
	@OneToOne
	@JoinColumn(name="product_id")
	private Product product;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Order order;
	

}
