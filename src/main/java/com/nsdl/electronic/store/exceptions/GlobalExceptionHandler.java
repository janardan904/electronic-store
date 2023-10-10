package com.nsdl.electronic.store.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsdl.electronic.store.response.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		ApiResponse response = ApiResponse.builder()
		.message(ex.getMessage())
		.success(false)
		.status(HttpStatus.NOT_FOUND)
		.build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
		
	}
	//MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>>handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List <ObjectError> allError=ex.getBindingResult().getAllErrors();   
		Map<String,Object> response=new HashMap();
		allError.stream().forEach(objectError->{
			String message=objectError.getDefaultMessage();
			String field=((FieldError)objectError).getField();
			
			response.put(field, message);
		});
		return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponse> BadApiRequestExceptionHandler(BadApiRequest ex){
		ApiResponse response = ApiResponse.builder()
		.message(ex.getMessage())
		.success(false)
		.status(HttpStatus.BAD_REQUEST)
		.build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
		
	}
	
}
