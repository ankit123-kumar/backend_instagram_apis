package com.insta.instagram.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.entities.Comment;
import com.insta.instagram.entities.Post;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.CommentException;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.repository.CommentRepository;
import com.insta.instagram.repository.PostRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	@Override
    public Comment createComment(Comment comment,Integer postId,Integer userId) throws UserException, PostException {
 
		 Post post = this.postService.findPostById(postId);
		 User user = this.userService.findUserById(userId);
         UserDto userDto = new UserDto();
         userDto.setId(user.getUserId());
         userDto.setEmail(user.getEmailId());
         userDto.setName(user.getName());
         userDto.setUserImage(user.getImage());
         userDto.setUsername(user.getUsername());
		 
         comment.setUser(userDto);
         comment.setCreateAt(LocalDateTime.now());
         
         Comment createdComment = commentRepository.save(comment);
		 
         post.getComments().add(createdComment);
          
         this.postRepository.save(post);
		 return createdComment;
     }
    @Override
	public Comment findCommentById(Integer commentId) throws CommentException {
	
    	 Optional<Comment> comment = this.commentRepository.findById(commentId);
		 
    	 if(comment.isPresent()) {
    		 return comment.get();
    	 }
    		 throw new CommentException("Comment not found with this id "+commentId);
    	  
    	}
    
        @Override
		public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
		
        	 User user = this.userService.findUserById(userId);
        	  Comment comment = findCommentById(commentId);
        	  
        	  UserDto userDto  = new UserDto();
        	  userDto.setId(user.getUserId());
        	  userDto.setEmail(user.getEmailId());
        	  userDto.setName(user.getName());
        	  userDto.setUserImage(user.getImage());
        	  userDto.setUsername(user.getUsername());
        	  
        	  comment.getLikedByUsers().add(userDto);
        	  
        	  return commentRepository.save(comment);
        	  
        	  
			
		}@Override
			public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
			 User user = this.userService.findUserById(userId);
       	  Comment comment = findCommentById(commentId);
       	  
       	  UserDto userDto  = new UserDto();
       	  userDto.setId(user.getUserId());
       	  userDto.setEmail(user.getEmailId());
       	  userDto.setName(user.getName());
       	  userDto.setUserImage(user.getImage());
       	  userDto.setUsername(user.getUsername());
       	  
       	  comment.getLikedByUsers().remove(userDto);
       	  
       	  return commentRepository.save(comment);
       	  
       	  
				
			}
}
