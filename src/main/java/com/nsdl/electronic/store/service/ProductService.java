package com.nsdl.electronic.store.service;

import java.util.List;

import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.ProductDto;
import com.nsdl.electronic.store.model.Category;

public interface ProductService {

	//create
	ProductDto create(ProductDto productDto);
	//update
	ProductDto update(ProductDto productDto,String productId);
	//delete
	void delete(String productId);
	//get single
	ProductDto get(String productId);
	//get all
	PageableResponse<ProductDto> getall(int pageNumber,int pageSize,String sortBy,String sortDir);
	//get all: live
	
	PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//create product with category
	
	ProductDto createProductWithCategory(ProductDto productDto,String categoryId);
	
	ProductDto updateCategory(String productId,String categoryId);
	PageableResponse<ProductDto> getAllCategory(String categoryId ,int pageNumber,int pageSize,String sortBy,String sortDir);
	
}
