package com.nsdl.electronic.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.dto.CategoryDto;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.ProductDto;
import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.helper.Helper;
import com.nsdl.electronic.store.model.Category;
import com.nsdl.electronic.store.model.Product;
import com.nsdl.electronic.store.repository.CategoryRepository;
import com.nsdl.electronic.store.repository.ProductRepository;
import com.nsdl.electronic.store.service.CategoryService;
import com.nsdl.electronic.store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
   
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	 private ModelMapper mapper;
	
	@Value("${product.profile.image.path}")
	private String imagePath;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public ProductDto create(ProductDto productDto) {
		
		Product product= mapper.map(productDto, Product.class);
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		
		product.setAddedDate(new Date());
		Product savedProduct= productRepository.save(product);
		return mapper.map(savedProduct,ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product is not found on given Id") );
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setPrice(productDto.getPrice());
		product.setLive(productDto.isLive());
		product.setQuentity(productDto.getQuentity());
		product.setRating(productDto.getRating());
		product.setStock(productDto.isStock());
		product.setProductImage(productDto.getProductImage());
		Product updatedProduct=productRepository.save(product);
		return mapper.map(updatedProduct,ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product is not found on given Id") );
		
		  String imageFullPath=imagePath+product.getProductImage();
			 Path path=Paths.get(imageFullPath);
			
			 try {
				Files.delete(path);
			 } catch (NoSuchFileException e) {
					
					e.printStackTrace();
				} 
			 catch (IOException e) {
				
				e.printStackTrace();
			}
			
		productRepository.delete(product);
	}

	@Override
	public ProductDto get(String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product is not found on given Id") );
		
		return mapper.map(product,ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getall(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		Page<Product> page=productRepository.findAll(pageable);
		
		return Helper.getPageableResponse(page,ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		Page<Product> page=productRepository.findByLiveTrue(pageable);
		return Helper.getPageableResponse(page,ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		Page<Product> page=productRepository.findByTitleContaining(subTitle, pageable);
		return Helper.getPageableResponse(page,ProductDto.class);
	}

	@Override
	public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category Id not found"));
		  
		Product product= mapper.map(productDto, Product.class);
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product savedProduct= productRepository.save(product);
		return mapper.map(savedProduct,ProductDto.class);
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		Product product= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product of given id not found!"));
		Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category of given id not found"));
		
		product.setCategory(category);
		Product savedProduct=productRepository.save(product);
		return mapper.map(savedProduct,ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category of given id not found"));
		Sort sort= (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page=productRepository.findByCategory(category, pageable);	
		return Helper.getPageableResponse(page,ProductDto.class);
	}

}
