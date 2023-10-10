package com.nsdl.electronic.store.service;

import com.nsdl.electronic.store.dto.CategoryDto;
import com.nsdl.electronic.store.dto.PageableResponse;

public interface CategoryService {

	// create category
	public CategoryDto create(CategoryDto categoryDto);
	
	//update category
	public CategoryDto update(CategoryDto categoryDto,String categoryId);
	//delete category
	public void delete(String categoryId);
	//get all category
	PageableResponse <CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	//get single category
	CategoryDto getCategoryById(String categoryId);
}
