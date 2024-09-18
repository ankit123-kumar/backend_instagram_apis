package com.insta.instagram.service;

import com.insta.instagram.entities.Comment;
import com.insta.instagram.exception.CommentException;
import com.insta.instagram.exception.PostException;
import com.insta.instagram.exception.UserException;

public interface CommentService {

	public Comment createComment(Comment comment,Integer postId,Integer userId) throws UserException,PostException;
    
	public Comment findCommentById(Integer commentId) throws CommentException;
	
//	public Comment deleteCommentById();
	
	public Comment likeComment(Integer commentId,Integer userId) throws CommentException,UserException;

	public Comment unlikeComment(Integer commentId,Integer userId) throws CommentException,UserException;

}
