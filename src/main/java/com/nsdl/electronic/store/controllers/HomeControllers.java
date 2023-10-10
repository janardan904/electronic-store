package com.nsdl.electronic.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v2")
@Api(value="userController",description="Rest Api related to perform controller test operation!")
public class HomeControllers {
	
	@GetMapping("/test")
	public String testing() {
		return "welcome to electronic store";
	}

}
