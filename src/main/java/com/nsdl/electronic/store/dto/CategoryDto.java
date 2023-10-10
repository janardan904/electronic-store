package com.nsdl.electronic.store.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

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
public class CategoryDto {
	
	private String categoryId;
	@NotBlank
	@Size(min=4, message="title must be minimum 4 charectres")
	private String title;
	@NotBlank(message="description required!!")
	private String description;
	@NotBlank(message="cover image is required!!")
	private String coverImage;
}
