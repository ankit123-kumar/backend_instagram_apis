package com.insta.instagram.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.entities.User;
import com.insta.instagram.exception.UserException;

public interface UserService {

	public User registerUser(User user) throws UserException;
	public User findUserById(Integer userId) throws UserException;
	public User findUserProfile(String token) throws UserException;
	public User findUserByUsername(String username) throws UserException;
	public String followUser(Integer reqUserId,Integer followUserId) throws UserException;
	public String unFollowUser(Integer reqUserId,Integer followUserId) throws UserException;
	
	public List<User> findUserByIds(List<Integer> userIds) throws UserException;
	
	public List<User> searchUser(String query) throws UserException;
	
	public User updateUserDetails(User updatedUser,MultipartFile file, User existingUser) throws UserException;
	
	public List<User> findAllUsers();
	
	public List<User> findFollowUser();
}
