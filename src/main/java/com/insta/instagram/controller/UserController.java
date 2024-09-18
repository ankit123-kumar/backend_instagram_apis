package com.insta.instagram.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.instagram.entities.User;
import com.insta.instagram.response.MessageResponse;
import com.insta.instagram.service.UserService;

import jakarta.servlet.annotation.MultipartConfig;

@RestController

@RequestMapping("/api/insta/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<User> createHandler(@RequestBody User user){
		
	    User createdUser = this.userService.registerUser(user);
		return new ResponseEntity<User>(createdUser,HttpStatus.OK);
	}
	
	 @GetMapping("/get/{userId}")
	public ResponseEntity<User> getSingleHandler(@PathVariable Integer userId,@RequestHeader("Authorization") String token){
		
		 User user1 =  userService.findUserProfile(token);
		 if(user1==null) {
			 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
       User user =  this.userService.findUserById(userId);
	   return ResponseEntity.status(HttpStatus.OK).body(user);
	 }
	 @GetMapping("/username/{username}")
		public ResponseEntity<User> getByUsernameHandler(@PathVariable String username,@RequestHeader("Authorization") String token){
			
		 User user1 =  userService.findUserProfile(token);
		 if(user1==null) {
			 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
	       User user =  this.userService.findUserByUsername(username);
	       System.out.println(user);
		   return new ResponseEntity<User>(user,HttpStatus.OK);
		 }
	 @GetMapping("/")
		public ResponseEntity<List<User>> getAll(@RequestHeader("Authorization") String token){
			
		 User user1 =  userService.findUserProfile(token);
		 if(user1==null) {
			 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
	       List<User> user =  this.userService.findAllUsers();
	       System.out.println(user);
		   return new ResponseEntity<List<User>>(user,HttpStatus.OK);
		 }
	 
	 @GetMapping("/search")
	 public ResponseEntity<List<User>> serarchUserHandler(@RequestParam("Q") String query,@RequestHeader("Authorization") String token ){
		 User user =  userService.findUserProfile(token);
		 if(user==null) {
			 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
		  List<User> users = this.userService.searchUser(query);
	     
		  return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	 }
	 
	 @PutMapping("follow/{followUserId}")
	 public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token){
		  User user = userService.findUserProfile(token);
		String message  =  userService.followUser(user.getUserId(), followUserId);
		MessageResponse response = new MessageResponse(message);
		return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
	 }
	 
	 @PutMapping("unfollow/{unfollowUserId}")
	 public ResponseEntity<MessageResponse> unFollowUserHandler(@PathVariable Integer unfollowUserId, @RequestHeader("Authorization") String token){
		 User user = userService.findUserProfile(token);
		String message  =  userService.unFollowUser(user.getUserId(), unfollowUserId);
		MessageResponse response = new MessageResponse(message);
		return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
	 }
	 
	 @PutMapping("account/edit")
	 public ResponseEntity<User> updateUserHandler(  @RequestPart(value="user",required=false) String userJson, @RequestPart(value="file", required=false) MultipartFile file, @RequestHeader("Authorization") String token){
		
		 System.out.println(file);
		 User user=null;
		 if (userJson != null && !userJson.trim().isEmpty()) {
		  // Deserialize the JSON string into a User object
		    ObjectMapper objectMapper = new ObjectMapper();
	   
   try {
		        user = objectMapper.readValue(userJson, User.class);
		    } catch (IOException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		 }
		 User user1 = userService.findUserProfile(token);
	    
		 
		 System.out.println("this is current user"+ user1);
		 User reqUser= userService.findUserById(user1.getUserId());
		  System.out.println(reqUser);
		User updatedUser  =  userService.updateUserDetails(user,file,reqUser);
		 
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
		 
		 }
	 	
	 
	 @GetMapping("/req")
	 public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token){
		 
		 User user =  userService.findUserProfile(token);
	return new ResponseEntity<User>(user,HttpStatus.OK);
	     
	 }
	 
		
}
