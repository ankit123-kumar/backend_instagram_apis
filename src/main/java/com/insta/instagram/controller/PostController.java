package com.insta.instagram.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.insta.instagram.entities.Post;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.response.MessageResponse;
import com.insta.instagram.service.PostService;
import com.insta.instagram.service.UserService;

import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping("/api/insta/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")                                        
	public ResponseEntity<Post> createPostHandler(@RequestPart(value="post",required=false) String postJson, @RequestPart("file") List<MultipartFile> file , @RequestHeader("Authorization") String token){
	
		User user = null;
		 Post post= new Post();
		 System.out.println("Received postJson: " + postJson);
		 if (postJson != null && !postJson.trim().isEmpty()) {
		
		    ObjectMapper objectMapper = new ObjectMapper();
	   
         try {
		        post = objectMapper.readValue(postJson, Post.class);
		    } catch (IOException e) {
		    	System.out.println("fererer------------------");
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
 }
		   user =  userService.findUserProfile(token);
    	 Post createdPost = this.postService.createPost(post, file, user.getUserId());
		 return new ResponseEntity<Post>(createdPost,HttpStatus.OK);
	
}
	
	@GetMapping("/fetchAll/{userId}")
	public ResponseEntity<List<Post>> getAllPostHandler(@PathVariable int userId, @RequestHeader("Authorization") String token){
		  User user =  userService.findUserProfile(token);
		  
		  if(user == null) {
			  
			  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		  }
		 List<Post> posts= this.postService.findPostByUserId(userId);
	     return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);  
	
	}
	
	@GetMapping("/following/{userIds}")
	public ResponseEntity<List<Post>> getAllPostByUserIdsHandler(@PathVariable List<Integer> userIds)throws PostException{
		
		 List<Post> posts= this.postService.findAllPostByUserIds(userIds);
	     return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);  
	
	}
	@GetMapping("/{postid}")
	public ResponseEntity<Post> getAllPostByUserIdHandler(@PathVariable int postId,@RequestHeader("Authorization") String token)throws PostException{
		
        User user =  userService.findUserProfile(token);
		  
		  if(user == null) {
			  
			  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		  }
		 Post post = this.postService.findPostById(postId);
	     return new ResponseEntity<Post>(post,HttpStatus.OK);  
	
	}
	
	@PutMapping("/liked/{postId}")
	public ResponseEntity<Post> likePostHandler(@PathVariable int postId,@RequestHeader("Authorization") String token)throws PostException{
		   User user =  userService.findUserProfile(token);
		
		  Post post = this.postService.likedPost(postId,user.getUserId()); 
		  return new ResponseEntity<Post>(post,HttpStatus.OK);
	}
	
	@PutMapping("/unliked/{postId}")
	public ResponseEntity<Post> unlikePostHandler(@PathVariable int postId,@RequestHeader("Authorization") String token)throws PostException{
		  User user =  userService.findUserProfile(token);
		  Post post = this.postService.unlikedPost(postId,user.getUserId()); 
		  return new ResponseEntity<Post>(post,HttpStatus.OK);
	}
	
	@DeleteMapping("/delPost/{postId}")
	public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable int postId,@RequestHeader("Authorization") String token) throws PostException{
		 User user =  userService.findUserProfile(token);
		 String message = this.postService.deletePost(postId, user.getUserId());
		 MessageResponse response = new MessageResponse(message);
		 return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
	}
	
	@PutMapping("/savePost/{postId}")
	public ResponseEntity<MessageResponse> savedPostHandler(@PathVariable int postId,@RequestHeader("Authorization") String token) throws PostException,UserException{
		 
		 User user =  userService.findUserProfile(token);
	            String message = postService.savedPost(postId, user.getUserId());
	            MessageResponse response = new MessageResponse(message);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	      
	}
	@PutMapping("/unsavePost/{postId}")
	public ResponseEntity<MessageResponse> unsavedPostHandler(@PathVariable int postId,@RequestHeader("Authorization") String token) throws PostException{
		 User user =  userService.findUserProfile(token);
		String message = this.postService.unSavedPost(postId,user.getUserId());
		 MessageResponse response = new MessageResponse(message);
		 return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
		
	}
}
