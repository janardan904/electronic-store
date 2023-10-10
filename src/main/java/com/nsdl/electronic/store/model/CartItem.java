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

import org.springframework.data.repository.query.parser.Part.Type;

import com.nsdl.electronic.store.dto.CartItemDto;
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
@Table(name="cart_items")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartItemId;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="product_id")
	private Product product;
	private int quentity;
	private int totalPrice;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="cart_id")
	private Cart cart;

	
	

}
