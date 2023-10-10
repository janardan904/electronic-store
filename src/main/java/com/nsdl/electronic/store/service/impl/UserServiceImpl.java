package com.nsdl.electronic.store.service.impl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.helper.Helper;
import com.nsdl.electronic.store.model.Role;
import com.nsdl.electronic.store.model.User;
import com.nsdl.electronic.store.repository.RoleRepository;
import com.nsdl.electronic.store.repository.UserRepository;
import com.nsdl.electronic.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
   @Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private RoleRepository  roleRepository;
   
   @Value("${user.profile.image.path}")
   private String imagePath;
   
     @Value("${roles.normal_role_id}")
	 private  String normalRoleId;
     
	 @Value("${roles.admin_role_id}")
		private String adminRoleId;
	
	@Override
	public UserDto create(UserDto userDto) {
		Role role=roleRepository.findById(adminRoleId).get();
		
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		//System.out.println(userId);
		User user = dtoToEntity(userDto);
		
		System.out.println("role Name: "+role);
		user.getRoles().add(role);
	    
		User saveduser = userRepository.save(user);
		System.out.println("Role Name:"+user.getAuthorities());
		UserDto newDto=entityToDto(saveduser);
		
		return newDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setAbout(userDto.getAbout());
		user.setImage(userDto.getImage());
		
		User updatedUser= userRepository.save(user);
		UserDto userdto= entityToDto(updatedUser);		
		return userdto;
		
	}

	@Override
	public void deleteUser(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user id not found"));
		userRepository.delete(user);
		
		String imageFullPath=imagePath+user.getImage();
		   Path path=Paths.get(imageFullPath);
		
		 try {
			Files.delete(path);
		 } catch (NoSuchFileException e) {
				
				e.printStackTrace();
			} 
		 catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
     
	}

	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir) {
		 
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		Page<User> page=userRepository.findAll(pageable);
		
		PageableResponse<UserDto>pageableResponse=Helper.getPageableResponse(page,UserDto.class);
	     return pageableResponse;
	}

	@Override
	public UserDto getUserById(String userId) {
       User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user id not found for given id"));
       
      
		return entityToDto(user);
	}
	
	@Override
	public UserDto getUserByEmail(String email)throws ResourceNotFoundException {
		User users=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("email id not found!"));
		
		return entityToDto(users);
	}

	@Override
	public List<UserDto> userSearch(String keyword) {
		List<User> users=userRepository.findByNameContaining(keyword);		 
		List<UserDto> dtoList= users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}
	
	private User dtoToEntity(UserDto userDto) {
		/*User user= User.builder().
		userId(userDto.getUserId())
		.name(userDto.getName())
		.email(userDto.getEmail())
		.password(userDto.getPassword())
		.gender(userDto.getGender())
		.about(userDto.getAbout())
		.image(userDto.getImage()).build();
		return user;*/
		 return mapper.map(userDto,User.class);
		
	}
	public UserDto entityToDto(User users) {
	/*	UserDto userDto= UserDto.builder()
				.userId(users.getUserId())
				.name(users.getName())
				.email(users.getEmail())
				.password(users.getPassword())
				.gender(users.getGender())
				.about(users.getAbout())
				.image(users.getImage()).build();
		return userDto;*/
		
		 return mapper.map(users,UserDto.class);
	
	}

	@Override
	public User findByUserEmailOptional(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("email id not found !!"));
	} 

}
