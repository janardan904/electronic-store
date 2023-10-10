package com.nsdl.electronic.store.service;
import java.util.List;
import java.util.Optional;

import com.nsdl.electronic.store.dto.PageableResponse;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.model.User;


public interface UserService {
	
	public UserDto create(UserDto userDto);
	public UserDto updateUser(UserDto userDto,String userId);
	public void deleteUser(String userId);
	
	public PageableResponse<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir);
	public UserDto getUserById(String userId);
	
	public UserDto getUserByEmail(String email);
	public List<UserDto> userSearch(String keyword);
	
	public User findByUserEmailOptional(String email);
}
