package com.nsdl.electronic.store.controllers;

import java.io.IOException
;
import java.io.InputStream;
import java.util.List;

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

import com.nsdl.electronic.store.dto.ImageResponse;
import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.response.ApiResponse;
import com.nsdl.electronic.store.service.impl.FileServiceImpl;
import com.nsdl.electronic.store.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping("/users")
@Api(value="userController",description="Rest Api related to perform user operation!")
public class UserController {

	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	@PostMapping
	@ApiOperation(value="create new user!")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto users=userService.create(userDto);
		return new ResponseEntity<UserDto>(users,HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	@ApiOperation(value="get sing user by id!")
	public ResponseEntity<UserDto> getUser(@PathVariable("id")String userId){
		UserDto users= userService.getUserById(userId);
		 return new ResponseEntity<UserDto>(users,HttpStatus.OK);
		
	}
	
	@GetMapping
	@ApiOperation(value="get all users!")
	public ResponseEntity <PageableResponse<UserDto>> getAllusers(@RequestParam (value="pageNumber", defaultValue ="0",required = false)int pageNumber,
			                                          @RequestParam (value="pageSize", defaultValue ="10",required = false )int pageSize,
			                                          @RequestParam (value="sortBy", defaultValue ="name",required = false)String sortBy,
			                                          @RequestParam (value="sortDir", defaultValue ="asc",required = false )String sortDir){
		PageableResponse<UserDto>  users=userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
	
		return new ResponseEntity<PageableResponse<UserDto>>(users,HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	@ApiOperation(value=" get user by email!")
	
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email){
		
		UserDto userEmails=userService.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userEmails,HttpStatus.OK);
	}
	@GetMapping("/name/{name}")
	@ApiOperation(value="get user by name!")
	public ResponseEntity<List<UserDto>> getUserByName(@PathVariable("name")String name){
		
		List<UserDto> users=userService.userSearch(name);
		
		return new ResponseEntity<List<UserDto>>(users,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value="delete by user using userId!")
	public ResponseEntity<ApiResponse> delete(@PathVariable("userId") String userId) {

		userService.deleteUser(userId);
		ApiResponse message = ApiResponse.builder().message("user deleted successfully!").success(true).build();
		return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);
	}
	@PutMapping("/update/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value="update the user by using userId!")
	public ResponseEntity<ApiResponse> update(@RequestBody UserDto userDto,@PathVariable("userId") String userId){
		
		UserDto updatedUsers=userService.updateUser(userDto, userId);
		ApiResponse  Message=ApiResponse.builder()
		.message("user updated succesfully!")
		.success(true)
		.build();
		return new ResponseEntity<>(Message,HttpStatus.OK);
	}
	
	@PostMapping("/image/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value="upload image")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,
			                                               @PathVariable("userId")String userId) throws IOException{

		
	String imageName=fileService.uploadImage(image,imageUploadPath);
	UserDto user=userService.getUserById(userId);
	user.setImage(imageName);
	UserDto users= userService.updateUser(user, userId);
	ImageResponse response=ImageResponse.builder().ImageName(imageName).success(true).status(HttpStatus.CREATED).build();
	return new ResponseEntity<ImageResponse>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("image/{userId}")
	@ApiOperation(value="serve image using userId!")
	public void serveUserImage(@PathVariable("userId")String userId ,HttpServletResponse response) throws IOException {
		
	UserDto user=userService.getUserById(userId);
	
	InputStream resource=fileService.getResource(imageUploadPath, user.getImage());
	     response.setContentType(MediaType.IMAGE_JPEG_VALUE);	
	     
	     StreamUtils.copy(resource,response.getOutputStream());
	}
}
