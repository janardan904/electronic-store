package com.nsdl.electronic.store.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nsdl.electronic.store.exceptions.BadApiRequest;
import com.nsdl.electronic.store.service.FileService;
@Service
public class FileServiceImpl implements FileService {
	
	Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

	//user images Api
	@Override
	public String uploadImage(MultipartFile file, String path) throws IOException  {
		 String originalFileName= file.getOriginalFilename();
		 logger.info("get origin file name:",originalFileName);
		 
		 String fileName=UUID.randomUUID().toString();
		 String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
		 
		 String fileNameWithExtension = fileName+extension;
		 String fullPathWithFileName= path+fileNameWithExtension;
		 logger.info("file with full name:{}",fullPathWithFileName);
		 
		 if(extension.equalsIgnoreCase(".png")|| extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")) {
			 
			 
			 File folder=new File(path);
			 if(!folder.exists()) {
				 folder.mkdirs();
			 }
		 Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
		 return fileNameWithExtension;
		 }
		 else {
			 throw new BadApiRequest("Bad Request with"+extension+" is not allowed");
		 }
		 
		
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		
		String fullPath= path+File.separator+name;
		
		InputStream inputStream=new FileInputStream(fullPath);
		
		return inputStream;
	}

	

}
