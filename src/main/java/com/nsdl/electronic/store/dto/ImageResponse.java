package com.nsdl.electronic.store.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ImageResponse {
	
	private String ImageName;
	private String message;
	private HttpStatus status;
	private boolean success;
	

}
