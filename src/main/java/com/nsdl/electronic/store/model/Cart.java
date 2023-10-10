package com.nsdl.electronic.store.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@ToString
@Table(name="cart")
public class Cart {
    
	@Id
	private String cartId;
	private Date createdAt;
	
	@OneToOne
	private User user;
	@OneToMany(mappedBy= "cart", cascade=CascadeType.ALL ,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<CartItem> items=new ArrayList<>();
}
