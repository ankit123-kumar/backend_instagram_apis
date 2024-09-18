
package com.insta.instagram.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.entities.ImageStorage;
import com.insta.instagram.entities.Post;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.repository.PostRepository;
import com.insta.instagram.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	@Override
	public Post createPost(Post post,List<MultipartFile> file,Integer userId) throws UserException {
		
	
		if(post==null || file!=null) {
			
		      List<ImageStorage> imageStorage = imageStorageService.savePostImages(file, post);
			   
		       post.setImages(imageStorage);		       
			
		}
		
		User user = this.userService.findUserById(userId);
		 
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmailId());
		userDto.setId(user.getUserId());
		userDto.setName(user.getName());
		userDto.setUserImage(user.getImage());
		userDto.setUsername(user.getUsername());
		
		post.setCreatedAt(LocalDateTime.now());
		post.setUser(userDto);
		Post createdPost  = postRepository.save(post);
		return createdPost;
	
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
		  
		  Post post = findPostById(postId);
		  List<ImageStorage> imageStorage = post.getImages();
		   List<String> imageNames = new ArrayList<>();
		   for(ImageStorage imageName : imageStorage){
			   
			   imageNames.addAll(imageNames);
			   
		   }
		  
		     boolean f = this.imageStorageService.deleteImage(post,imageNames);
		  if(f) {
			  
			  User user = userService.findUserById(userId);
			  
			  if(post.getUser().getId().equals(user.getUserId())) {
				  postRepository.deleteById(post.getPostid());
				  return "Post Deleted Successfully";
			  }
			
		  }
		 
		throw new PostException("you can't delele other user user's post");
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) throws UserException {
	
		List<Post> posts = postRepository.findByUserId(userId);
	    if(posts.size()==0) {
	    	throw new UserException("this user does not have any post");
	    }
		 return posts;
	}

	@Override
	public Post findPostById(Integer postId) throws PostException {
	      Optional<Post> post = postRepository.findById(postId);
	     if(post.isPresent()) {
	    	 return post.get();
	     }
		throw new PostException("Post not found with this id "+postId);
	}

	@Override
	public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
		List<Post> posts = postRepository.findAllPostsByUserIds(userIds);
		
		if(posts.size()==0) {
			throw new PostException("No Post available");
		}
		return posts;
	}

	@Override
	public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
		Post post  = findPostById(postId);
		 User user = userService.findUserById(userId);
		 
		 if(!user.getSavedPost().contains(post)) {
			 
			 user.getSavedPost().add(post);
			 userRepository.save(user);
		 }else {
				return "Post Already saved ";
		 }
		 
		return "Post saved successfully";
	}

	@Override
	public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
		 Post post = findPostById(postId);
		   User user = userService.findUserById(userId);
  
		   if(user.getSavedPost().contains(post)) {
			   user.getSavedPost().remove(post);
			   
			   userRepository.save(user);
		   }
		   return "Post unsaved successfully";
	}

	@Override
	public Post likedPost(Integer PostId, Integer userId) throws UserException, PostException {
	
		 Post post = findPostById(PostId);
		 User user = userService.findUserById(userId);
		 
		 UserDto userDto = new UserDto();
		 userDto.setId(user.getUserId());
		 userDto.setEmail(user.getEmailId());
		 userDto.setName(user.getName());
		 userDto.setUserImage(user.getImage());
		 userDto.setUsername(user.getUsername());
		 
	     post.getLikedByUsers().add(userDto);
	    
	   
		 return postRepository.save(post);
	
	}

	@Override
	public Post unlikedPost(Integer postId, Integer userId) {
		
		 Post post = null;
		try {
			post = findPostById(postId);
		} catch (PostException e) {
			
			e.printStackTrace();
		}
		 User user = userService.findUserById(userId);
		  
		 UserDto userDto = new UserDto();
		 userDto.setId(user.getUserId());
		 userDto.setEmail(user.getEmailId());
		 userDto.setName(user.getName());
		 userDto.setUserImage(user.getImage());
		 userDto.setUsername(user.getUsername());
		 
		 post.getLikedByUsers().remove(userDto);
		 
	      return postRepository.save(post);
	
	}

}
