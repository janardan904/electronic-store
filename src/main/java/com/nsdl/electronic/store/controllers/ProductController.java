package com.nsdl.electronic.store.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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

import com.nsdl.electronic.store.dto.ImageResponse;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.ProductDto;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.response.ApiResponse;
import com.nsdl.electronic.store.service.FileService;
import com.nsdl.electronic.store.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
@Api(value="userController",description="Rest Api related to perform Product operation!")
public class ProductController {

    @Autowired	
	private ProductService productService;
	//craete
    @Autowired
    private FileService fileService;
    @Value("${product.profile.image.path}")
    private String productImagePath;
    @PostMapping
   @ApiOperation(value="create new product!")
	public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto prductDto){
    	System.out.println("entering secure method::");
		ProductDto savedproduct=productService.create(prductDto);
		
		return new ResponseEntity<>(savedproduct,HttpStatus.CREATED);
		
		
	}
	//update
    @PutMapping("/{productId}")
    @ApiOperation(value="update product using product id!")
    public ResponseEntity<ProductDto>updateProduct(@RequestBody ProductDto prductDto,@PathVariable String productId){
		ProductDto updatedproduct=productService.update(prductDto,productId);
		
		return new ResponseEntity<>(updatedproduct,HttpStatus.OK);
		
		
	}
	//delete
    @DeleteMapping("/{productId}")
    @ApiOperation(value="delete product using product id!")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId){
    	productService.delete(productId);
		ApiResponse response =ApiResponse.builder().message("product deleted successfully!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
    
	//getsingle
	
    @GetMapping("/{productId}")
    @ApiOperation(value="get product using product id!")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
		 ProductDto product=productService.get(productId);
    	return new ResponseEntity<ProductDto>(product,HttpStatus.OK);
    	
    }
	//getall
    @GetMapping
    @ApiOperation(value="get all product!")
	public ResponseEntity <PageableResponse<ProductDto>> getAllProducts(
			                                          @RequestParam (value="pageNumber", defaultValue ="0",required = false)int pageNumber,
			                                          @RequestParam (value="pageSize", defaultValue ="10",required = false )int pageSize,
			                                          @RequestParam (value="sortBy", defaultValue ="title",required = false)String sortBy,
			                                          @RequestParam (value="sortDir", defaultValue ="asc",required = false )String sortDir){
		PageableResponse<ProductDto> product=productService.getall(pageNumber, pageSize, sortBy, sortDir);
	
		return new ResponseEntity<PageableResponse<ProductDto>>(product,HttpStatus.OK);
	}
    
	//search
    @GetMapping("/live")
    @ApiOperation(value="get live product !")
  	public ResponseEntity <PageableResponse<ProductDto>> getAllProductOnLive(
  			                                          @RequestParam (value="pageNumber", defaultValue ="0",required = false)int pageNumber,
  			                                          @RequestParam (value="pageSize", defaultValue ="10",required = false )int pageSize,
  			                                          @RequestParam (value="sortBy", defaultValue ="title",required = false)String sortBy,
  			                                          @RequestParam (value="sortDir", defaultValue ="asc",required = false )String sortDir){
  		PageableResponse<ProductDto> product=productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
  	
  		return new ResponseEntity<PageableResponse<ProductDto>>(product,HttpStatus.OK);
  	}
    
    
    @GetMapping("/search/{query}")
    @ApiOperation(value="get  product using product title!")
  	public ResponseEntity <PageableResponse<ProductDto>> getAllProductOnTitle(
  			                                          @PathVariable String query,
  			                                          @RequestParam (value="pageNumber", defaultValue ="0",required = false)int pageNumber,
  			                                          @RequestParam (value="pageSize", defaultValue ="10",required = false )int pageSize,
  			                                          @RequestParam (value="sortBy", defaultValue ="title",required = false)String sortBy,
  			                                          @RequestParam (value="sortDir", defaultValue ="asc",required = false )String sortDir){
  		PageableResponse<ProductDto> product=productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
  		return new ResponseEntity<PageableResponse<ProductDto>>(product,HttpStatus.OK);
  	}
    
    
    @PostMapping("/image/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value="upload product image!")
	public ResponseEntity<ImageResponse> uploadImage(@RequestParam("productImage")MultipartFile image,
			                                               @PathVariable("productId")String productId) throws IOException{

		
	String imageName=fileService.uploadImage(image,productImagePath);
	ProductDto  product=productService.get(productId);
	product.setProductImage(imageName);
	ProductDto products= productService.update(product, productId);
	ImageResponse response=ImageResponse.builder().message("Image uploaded sucessfully").ImageName(imageName).success(true).status(HttpStatus.CREATED).build();
	return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("image/{productId}")
	@ApiOperation(value="serve product image using productId!")
	public void serveProductImage(@PathVariable("productId")String productId ,HttpServletResponse response) throws IOException {
		
	ProductDto products=productService.get(productId);
	
	InputStream resource=fileService.getResource(productImagePath, products.getProductImage());
	     response.setContentType(MediaType.IMAGE_JPEG_VALUE);	
	     
	     StreamUtils.copy(resource,response.getOutputStream());
	}
}
