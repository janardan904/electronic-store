package com.nsdl.electronic.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.dto.CategoryDto;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.helper.Helper;
import com.nsdl.electronic.store.model.Category;
import com.nsdl.electronic.store.repository.CategoryRepository;
import com.nsdl.electronic.store.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${category.profile.image.path}")
	private String imagePath;
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		 Category category=mapper.map(categoryDto,Category.class);
		Category savedCategory=categoryRepository.save(category);
		return mapper.map(savedCategory,CategoryDto.class);
		
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
	Category category=	categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category id not found!"));
	category.setTitle(categoryDto.getTitle());
	category.setDescription(categoryDto.getDescription());
	category.setCoverImage(categoryDto.getCoverImage());
	Category Updatedcategory=categoryRepository.save(category);
	return mapper.map(Updatedcategory,CategoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		Category category=	categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category id not found!"));
		 String imageFullPath=imagePath+category.getCoverImage();
		 Path path=Paths.get(imageFullPath);
		
		 try {
			Files.delete(path);
		 } catch (NoSuchFileException e) {
				
				e.printStackTrace();
			} 
		 catch (IOException e) {
			
			e.printStackTrace();
		}
		
		categoryRepository.delete(category);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		 Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
	     Page<Category> page = categoryRepository.findAll(pageable);
		 PageableResponse<CategoryDto> pageableResponse= Helper.getPageableResponse(page,CategoryDto.class);
		return pageableResponse;
	}

	@Override
	public CategoryDto getCategoryById(String categoryId) {
		Category category=	categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category id not found!"));
		return mapper.map(category,CategoryDto.class);
	}

}
