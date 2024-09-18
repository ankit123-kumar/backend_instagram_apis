package com.insta.instagram.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.exception.UserException;

@Service

public class ImageService {

	@Value("${project.image}")
	private String dir;
	 protected String handleFileUpload(MultipartFile file) throws UserException {
	        try {
	          
	        	File uploadDir = new File(dir); 
	        	if(!uploadDir.exists()) {
	        		
	        		uploadDir.mkdirs();
	        	}
	            String originalFilename = file.getOriginalFilename();
	            String uniqueFilename = UUID.randomUUID().toString() + "-" + originalFilename;
	            Path path = Paths.get(dir + uniqueFilename);

	            Files.write(path, file.getBytes());

	           
	            return uniqueFilename; 
	        } catch (IOException e) {
	            throw new UserException("Failed to upload file: ");
	        }
	  }
	 
	 protected List<String> handleMultipleFileUpload(List<MultipartFile> files) throws UserException {
		 
		 List<String> list = new ArrayList<>();
		 String uniqueFilename = null;
		 try {
	          
	        	File uploadDir = new File(dir); 
	        	if(!uploadDir.exists()) {
	        		
	        		uploadDir.mkdirs();
	        	}
	        	for( MultipartFile file: files) {
	        		
	        	
	            String originalFilename = file.getOriginalFilename();
	             uniqueFilename = UUID.randomUUID().toString() + "-" + originalFilename;
	            Path path = Paths.get(dir + uniqueFilename);

	            Files.write(path, file.getBytes());
	            
	            list.add(uniqueFilename);
	            
	        	}
	                    
	            return list; 
	        } catch (IOException e) {
	            throw new UserException("Failed to upload file: ");
	        }
	  }
	 protected boolean handleFileDelete(String fileName) {
		 
		 boolean f = false;
		 File filePath = new File(dir,fileName);
		 if(filePath.exists()) {
			 filePath.delete();
			 
			 f=true;
			 System.out.println("-----------"+f);
			
		 }
		 
		
		 return f; 
		 
		 
	 }
	 
 protected boolean handleMultipleFileDelete(List<String> fileName) {
	 boolean f = false;
	 for(String file : fileName) {
		 File filePath = new File(dir,file);
		 if(filePath.exists()) {
			 filePath.delete();
			 
			 f=true;
			 System.out.println("-----------"+f);
			
		 }
	 }
		
		
		 
		
		 return f; 
		 
		 
	 }
}
