package com.nsdl.electronic.store.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="products")
public class Product {
	@Id
	private String productId;
	private String title;
	@Column(length=10000)
	private String description;
	private int price;
	private int quentity;
	private int discountedPrice; 
	private Date addedDate;
	private int rating;
	private boolean live;
	private boolean stock;
	private String productImage;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id")
	private Category category;
	

}
