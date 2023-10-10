package com.nsdl.electronic.store.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.nsdl.electronic.store.dto.JwtRequest;
import com.nsdl.electronic.store.dto.JwtResponse;
import com.nsdl.electronic.store.dto.UserDto;
import com.nsdl.electronic.store.exceptions.BadApiRequest;
import com.nsdl.electronic.store.model.User;
import com.nsdl.electronic.store.security.JwtHelper;
import com.nsdl.electronic.store.service.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth")
@Api(value="userController",description="Rest Api related to perform Authentication operation!")
public class AuthController {
   @Autowired
	private UserDetailsService userDetailsService;
   @Autowired
   private ModelMapper mapper;
   @Autowired
   private AuthenticationManager manager;
   @Autowired
   private UserService userService;
   @Autowired
   private JwtHelper helper;
   Logger logger= LoggerFactory.getLogger(AuthController.class);
   @Value("{google.auth.google_client_id}")
   private String googleClientId;
   @Value("{google.auth.newpassword}")
   private String newPassword;
   @PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
		
		this.doAuthenticate(request.getEmail(),request.getPassword());
	      UserDetails userDetails= userDetailsService.loadUserByUsername(request.getEmail());	
		  String token=this.helper.generateToken(userDetails);
		  
		UserDto userDto = mapper.map(userDetails,UserDto.class);
		
		JwtResponse response= JwtResponse.builder().jwtToken(token).user(userDto).build();
				
	      
	      return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
   
   
   
   
   private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,password);
		
		try {
			manager.authenticate(authentication);
		}
		catch(BadCredentialsException e) {
			throw new BadApiRequest(" incorrect username or passowrd !");
		}
	}





@GetMapping("/current")
	public ResponseEntity<UserDto> getCurrentUser(Principal principal ){
		
		String name=principal.getName();
		
		
		return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name),UserDto.class),HttpStatus.OK);
		
	}
	//login with google
	/*
	 * @PostMapping("/google") ResponseEntity<JwtResponse>
	 * loginwithGoogle(@RequestBody Map<String,Object> data) throws IOException{
	 * 
	 * String idToken= data.get("idToken").toString(); NetHttpTransport
	 * netTransport=new NetHttpTransport();
	 * 
	 * 
	 * JacksonFactory defaultInstance =JacksonFactory.getDefaultInstance();
	 * GoogleIdTokenVerifier.Builder verifier=new
	 * GoogleIdTokenVerifier.Builder(netTransport,
	 * defaultInstance).setAudience(Collections.singleton(googleClientId));
	 * 
	 * GoogleIdToken googleIdToken =
	 * GoogleIdToken.parse(verifier.getJsonFactory(),idToken); GoogleIdToken.Payload
	 * payload= googleIdToken.getPayload(); logger.info("payload:{}" +payload);
	 * 
	 * String email=payload.getEmail();
	 * 
	 * User user=null; user=userService.findByUserEmailOptional(email);
	 * if(user==null) { User users=
	 * this.saveUser(email,data.get("name").toString(),data.get("photoUrl").toString
	 * ()); } ResponseEntity<JwtResponse>response=
	 * this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).
	 * build()); return response;
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private User saveUser(String email, String name, String photoUrl) {
	 * 
	 * UserDto newUser=UserDto.builder() .name(name) .email(email)
	 * .password(newPassword) .image(photoUrl) .roles(new HashSet<>()) .build();
	 * UserDto user=userService.create(newUser);
	 * 
	 * return mapper.map(user,User.class); }
	 */
}
