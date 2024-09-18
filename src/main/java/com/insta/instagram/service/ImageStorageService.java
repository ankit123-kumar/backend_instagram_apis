package com.insta.instagram.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.entities.ImageStorage;
import com.insta.instagram.entities.Post;
import com.insta.instagram.repository.ImageRepository;

@Service
public class ImageStorageService {

	@Autowired
	private ImageRepository imageRepository;
	
//	@Autowired
//	private ImageStorage imageStorage;
//	
	@Autowired
	private ImageService imageService;
	public List<ImageStorage> savePostImages(List<MultipartFile> file,Post post){
		
		List<String> savedFileNames = imageService. handleMultipleFileUpload(file);
		
	    
        List<ImageStorage> imageStorages = new ArrayList<>();
        
        // Create an ImageStorage entity for each file name
        for (String fileName : savedFileNames) {
            ImageStorage imageStorage = new ImageStorage();
            imageStorage.setImageName(fileName);
            imageStorage.setPost(post);
            
            // Add to the list of ImageStorage entities
            imageStorages.add(imageStorage);
        }
        
        // Save all ImageStorage entities
        List<ImageStorage> savedImages = imageRepository.saveAll(imageStorages);
        
        return savedImages;

	} 
	
	
	public boolean deleteImage(Post post,List<String> files) {
		
		 
		  boolean f = imageService.handleMultipleFileDelete(files);
		  
		  if(f) {
			  
			  this.imageRepository.deleteById(post.getPostid());
		  }
		
		return f;
	}
}
