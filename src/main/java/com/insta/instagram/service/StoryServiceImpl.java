package com.insta.instagram.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.dto.UserDto;

import com.insta.instagram.entities.Story;
import com.insta.instagram.entities.User;

import com.insta.instagram.exception.StoryException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.repository.StoryRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class StoryServiceImpl implements StoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ImageService imageService;
	@Override
	public Story createStory(Story story,MultipartFile file, Integer userId) {
	
		if(file!=null) {
			
			if(story==null || file!=null) {
				
				 String savedPostName = imageService.handleFileUpload(file);
				 story.setImage(savedPostName);
			}
			
		}
		 User user = this.userService.findUserById(userId);
		 
		 UserDto userDto  = new UserDto();
		 userDto.setId(user.getUserId());
		 userDto.setEmail(user.getEmailId());
		 userDto.setName(user.getName());
		 userDto.setUserImage(user.getImage());
		 userDto.setUsername(user.getUsername());
		 
		 story.setUser(userDto);
		 story.setCreatedAt(LocalDateTime.now());
		  user.getStories().add(story);
		  Story createdStory = storyRepository.save(story); 
		
		
		  return createdStory;
	}@Override
	public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
		
		 User user = userService.findUserById(userId);
		 
		 List<Story> stories = user.getStories();
		 if(stories.size()==0) {
			 
			 throw new StoryException("no stories are available for this user "+userId);
		 }
		return stories;
	}
	
	@Override
	@Transactional
	public String deleteStory(Integer storyId, Integer userId) throws UserException, StoryException {
		  
		Story story=null;
		  Optional<Story> opt = this.storyRepository.findById(storyId);
		 
		  if(opt.isPresent()) {
			  
			   story = opt.get();
		  }
		  
		  boolean f = imageService.handleFileDelete(story.getImage());
		  
		  System.out.println(f);
		  if(f) {
			  
			  User user = userService.findUserById(userId);
			  
			  if(story.getUser().getId().equals(user.getUserId())) {
				  storyRepository.deleteById(story.getStoryId());
				  return "Story Deleted Successfully";
			  }
			
		  }
		 
		throw new StoryException("you can't delele other user user's story");
	}
}
