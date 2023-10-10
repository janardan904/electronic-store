
package com.nsdl.electronic.store.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nsdl.electronic.store.dto.CategoryDto;
import com.nsdl.electronic.store.dto.ImageResponse;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.ProductDto;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.model.Product;
import com.nsdl.electronic.store.response.ApiResponse;
import com.nsdl.electronic.store.service.CategoryService;
import com.nsdl.electronic.store.service.FileService;
import com.nsdl.electronic.store.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categories")
@Api(value="userController",description="Rest Api related to perform Categories operation!")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	@Autowired
	private FileService fileService;
	
	@Value("${category.profile.image.path}")
	private String uploadCoverImagePath;
	@Autowired
	private ProductService productService;
	//cretae
	@PostMapping
	@ApiOperation(value="create new category!")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
	     String categoryId= UUID.randomUUID().toString();
	     categoryDto.setCategoryId(categoryId);
		CategoryDto categoryDto1=service.create(categoryDto);
	return new ResponseEntity<CategoryDto>(categoryDto1,HttpStatus.CREATED);
	}
	
	
	//update
	@PutMapping("/{categoryId}")
	@ApiOperation(value="update category using categoryId!")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("categoryId")String categoryId){
		 CategoryDto updatedCategory=service.update(categoryDto, categoryId);
	return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);	
	}
	
	
	//delete
	@DeleteMapping("/{categoryId}")
	@ApiOperation(value="delete category using category id!")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
		service.delete(categoryId);
		ApiResponse response=ApiResponse.builder().message("category deleted sucessfully!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}

	//getall
	@GetMapping
	@ApiOperation(value="get all category!")
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
			@RequestParam(value="pageNumber",defaultValue ="0",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue ="10",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue ="title",required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue ="asc",required = false) String sortDir){
		PageableResponse<CategoryDto> response= service.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<CategoryDto>>(response,HttpStatus.OK);
		
	}
	
	//getsingle
	@GetMapping("/{categoryId}")
	@ApiOperation(value="get single category on category id!")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId")String categoryId){	
	        CategoryDto singCategory=service.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(singCategory,HttpStatus.OK);
		
	}
	@PostMapping("/coverImage/{categoryId}")
	@ApiOperation(value="upload cover image using category id!")
	public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("coverImage")MultipartFile coverImage,
			                                               @PathVariable("categoryId")String categoryId) throws IOException{

		
	String coverImageName=fileService.uploadImage(coverImage,uploadCoverImagePath);
	CategoryDto categoryDto=service.getCategoryById(categoryId);
	categoryDto.setCoverImage(coverImageName);
	CategoryDto category= service.update(categoryDto, categoryId);
	ImageResponse response=ImageResponse.builder().ImageName(coverImageName).success(true).status(HttpStatus.CREATED).build();
	return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("coverImage/{categoryId}")
	@ApiOperation(value="serve cover image using category id!")
	public void serveCoverImage(@PathVariable("categoryId")String categoryId ,HttpServletResponse response) throws IOException {
		
	CategoryDto category=service.getCategoryById(categoryId);
	
	InputStream resource=fileService.getResource(uploadCoverImagePath, category.getCoverImage());
	     response.setContentType(MediaType.IMAGE_JPEG_VALUE);	
	     
	     StreamUtils.copy(resource,response.getOutputStream());
	}

	//create product with category
	@PostMapping("/{categoryId}/products")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value="create new  products with category!")
	public ResponseEntity<ProductDto>createProductWithCategory(
			@PathVariable("categoryId")String categoryId,
			@RequestBody ProductDto dto)
	{
		ProductDto savedProducts=productService.createProductWithCategory(dto, categoryId);
		
		return new ResponseEntity<ProductDto>(savedProducts,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{categoryId}/products/{productId}")
	@ApiOperation(value="update category with existing products using cateid and productid!")
	public ResponseEntity<ProductDto> updateCategory(@PathVariable String categoryId,
			                                         @PathVariable String productId){
		
		ProductDto productDto=productService.updateCategory(productId, categoryId);												
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/{categoryId}/products")
	@ApiOperation(value="get products using category id!")
	public ResponseEntity<PageableResponse<ProductDto>> getCatogory(
			                @PathVariable String categoryId,
			                @RequestParam(value="pageNumber",defaultValue ="0",required = false) int pageNumber,
			    			@RequestParam(value="pageSize",defaultValue ="10",required = false) int pageSize,
			    			@RequestParam(value="sortBy",defaultValue ="title",required = false) String sortBy,
			    			@RequestParam(value="sortDir",defaultValue ="asc",required = false) String sortDir
			                
			){
		PageableResponse<ProductDto> product=productService.getAllCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PageableResponse<ProductDto>>(product,HttpStatus.OK);
		
		
	}

}
