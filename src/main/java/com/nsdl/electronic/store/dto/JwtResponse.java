package com.nsdl.electronic.store.dto;

import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

	private String jwtToken;
	private UserDto user;
}
