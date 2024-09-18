package com.insta.instagram.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.instagram.entities.Post;
import com.insta.instagram.entities.Story;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.StoryException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.response.MessageResponse;
import com.insta.instagram.service.StoryService;
import com.insta.instagram.service.UserService;

@RestController
@RequestMapping("/api/insta/story")
public class StoryController {

	@Autowired
	private StoryService storyService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Story> createStory(@RequestPart(value="story",required=false) String storyJson, @RequestPart(name="file") MultipartFile file , @RequestHeader("Authorization") String token) throws UserException{
		
		User user = null;
		 Story story= new Story();
		 System.out.println("Received postJson: " + storyJson);
		 if (storyJson!= null && !storyJson.trim().isEmpty()) {
		
		    ObjectMapper objectMapper = new ObjectMapper();
	   
        try {
		        story = objectMapper.readValue(storyJson, Story.class);
		    } catch (IOException e) {
		    	System.out.println("fererer------------------");
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
}
		
		   user = userService.findUserProfile(token);
		 Story createdStory = this.storyService.createStory(story,file, user.getUserId());
		return new ResponseEntity<Story>(createdStory,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Story>> getAllStories(@RequestHeader("Authorization") String token) throws StoryException{
		
		  User user = userService.findUserProfile(token);
		 List<Story> stories = this.storyService.findStoryByUserId(user.getUserId());
	     return new ResponseEntity<List<Story>>(stories,HttpStatus.OK);
	     
	   
	}
	@DeleteMapping("/delStory/{storyId}")
	public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable int storyId,@RequestHeader("Authorization") String token) throws StoryException{
		 User user =  userService.findUserProfile(token);
		 String message = this.storyService.deleteStory(storyId, user.getUserId());
		 MessageResponse response = new MessageResponse(message);
		 return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
	}
}
