package com.nsdl.electronic.store.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nsdl.electronic.store.model.Role;
import com.nsdl.electronic.store.validation.ImageNameValidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDto {

	private String userId;
	@Size(min=4,max=20,message="invalid name")
	private String name;
	
	@Pattern(regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message="invalid email id")
	private String email;
	@NotBlank(message="required password")
	private String password;
	@Size(min=4 ,max=6,message="invalid gender")
	private String gender;
	private String about;
	@ImageNameValidate
	private String image;
	private Set<RoleDto> roles=new HashSet<>();
}
