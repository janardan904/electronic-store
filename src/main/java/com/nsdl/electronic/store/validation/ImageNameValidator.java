package com.nsdl.electronic.store.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidator implements ConstraintValidator<ImageNameValidate,String> {

	
	private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
	logger.info(" Message form isvalid",value);
		if(value.isBlank()) {
			return false;
		}
		else {
			return true;
		}
		
	}

	

}
