package com.nsdl.electronic.store.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductDto {

	
	private String productId;
	@NotBlank(message="tile is required!")
	@Size(min=4,message="title must be min 4 charecter!")
	private String title;
	@NotBlank(message="description is required!")
	private String description;
	private int price;
	private int quentity;
	private int discountedPrice; 
	private Date addedDate;
	private int rating;
	private boolean live;
	private boolean stock;
	private String productImage;
	private CategoryDto category;
	
}
