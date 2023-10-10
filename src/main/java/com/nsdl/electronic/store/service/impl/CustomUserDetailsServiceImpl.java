package com.nsdl.electronic.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nsdl.electronic.store.exceptions.ResourceNotFoundException;
import com.nsdl.electronic.store.repository.UserRepository;
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	 private UserRepository userRepository ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserDetails user=  userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("Username not found!!"));
		return user;
	
	
	}

}
