package com.insta.instagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.entities.Comment;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.CommentException;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.service.CommentService;
import com.insta.instagram.service.UserService;

@RestController
@RequestMapping("/api/insta/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment,@PathVariable int postId,@RequestHeader("Authorization") String token) throws PostException,UserException{
		
		  User user = userService.findUserProfile(token);
		 Comment createdComment = commentService.createComment(comment, postId, user.getUserId());
		 
		return new ResponseEntity<Comment>(createdComment,HttpStatus.CREATED);
	
	}
	
	@GetMapping("/{commentId}")
	public ResponseEntity<Comment> getCommentHandler(@PathVariable int commentId,@RequestHeader("Authorization") String token) throws CommentException{
		
		 User user = userService.findUserProfile(token);
		 
		 if(user == null) {
			 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 }
		 Comment comment = commentService.findCommentById(commentId);
		 
		 return new ResponseEntity<Comment>(comment,HttpStatus.OK);
	}
	
	@PutMapping("/like/{commentId}")
	public ResponseEntity<Comment> likeCommentHandler(@PathVariable int commentId,@RequestHeader("Authorization") String token)throws UserException,CommentException{
		 User user = userService.findUserProfile(token);
     Comment comment = commentService.likeComment(commentId,user.getUserId());
	 return new ResponseEntity<Comment>(comment,HttpStatus.OK); 
	}
	@PutMapping("/unlike/{commentId}")
	public ResponseEntity<Comment> unlikeCommentHandler(@PathVariable int commentId,@RequestHeader("Authorization") String token)throws UserException,CommentException{
		 User user = userService.findUserProfile(token);
     Comment comment = commentService.unlikeComment(commentId,user.getUserId());
	 return new ResponseEntity<Comment>(comment,HttpStatus.OK); 
	}

}
