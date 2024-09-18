package com.insta.instagram.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.entities.Post;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;

@Service
public interface PostService {

	public Post createPost(Post post,List<MultipartFile> file,Integer userId)throws UserException;
	
	public String deletePost(Integer postId,Integer userId) throws UserException,PostException;
	
	public List<Post> findPostByUserId(Integer userId)throws UserException;
	
	public Post findPostById(Integer postId)throws PostException;
	
	public List<Post> findAllPostByUserIds(List<Integer> userIds)throws PostException,UserException;
    
	public String savedPost(Integer postId,Integer userId)throws PostException,UserException;
	
	public String unSavedPost(Integer postId,Integer userId)throws PostException,UserException;
    
	public Post likedPost(Integer PostId,Integer userId) throws UserException,PostException;
	
	public Post unlikedPost(Integer PostId,Integer userId);
	}
