package com.insta.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.config.JwtTokenGenerator;
import com.insta.instagram.dto.UserDto;
import com.insta.instagram.entities.User;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.repository.UserRepository;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	

	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageService imageService;
	@Override
	public User registerUser(User user) throws UserException {
		
		Optional<User> isEmailExist = this.userRepository.findByEmailId(user.getEmailId());
		
		if(isEmailExist.isPresent())
		{
			throw new UserException("Email is Already Exist");
		}
		
		 Optional<User> isUsernameExist = this.userRepository.findByUsername(user.getUsername());
		
		 if(isUsernameExist.isPresent()) {
			 throw new UserException("Username is Already Exist");
		 }
		 
		 if(user.getEmailId()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null)
		 {
			 throw new UserException("all fields are required");
		 }
		 
		 User newUser = new User();
		 newUser.setEmailId(user.getEmailId());
		 newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		 newUser.setUsername(user.getUsername());
		 newUser.setName(user.getName());
		 
		  return this.userRepository.save(newUser);
		 
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		 Optional<User> user = this.userRepository.findById(userId);
		 if(user.isPresent()) {
			 return user.get();
		 }
		 throw new UserException("user not exist with this id: "+userId);
		
	}

	@Override
	public User findUserProfile(String token) throws UserException {
		 
		
		 if (token.startsWith("Bearer ")) {
	            token = token.substring(7);
	        }

	        // Parse the token
	        Claims claims = jwtTokenGenerator.parseToken(token);

	        if (claims == null) {
	            throw new UserException("Invalid or expired token");
	        }

	        // Extract user information from claims
	        String username = claims.get("username", String.class);
	        System.out.println(username);
	        if (username == null) {
	            throw new UserException("Username not found in token");
	        }

	        // Fetch user profile from the database
	        return userRepository.findByEmailId(username)
	                .orElseThrow(() -> new UserException("User not found"));
	    	
		
    }

	@Override
	public User findUserByUsername(String username) throws UserException {
	
		Optional<User> user = this.userRepository.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("user not exist with this username: "+username);
	}

	@Override
	public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
	
		User reqUser = findUserById(reqUserId);
		User followUser = findUserById(followUserId);
		
		UserDto follower = new UserDto();
		
		follower.setEmail(reqUser.getEmailId());
		follower.setId(reqUser.getUserId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getImage());
		follower.setUsername(reqUser.getUsername());
		
		UserDto following = new UserDto();
		following.setEmail(followUser.getEmailId());
		following.setId(followUser.getUserId());
		following.setName(followUser.getName());
		following.setUserImage(followUser.getImage());
		following.setUsername(followUser.getUsername());
		
		
		reqUser.getFollowing().add(following);
		followUser.getFollower().add(follower);
		
		
		userRepository.save(followUser);
		userRepository.save(reqUser);
		
		return "you are following " +followUser.getUsername();
	}

	@Override
	public String unFollowUser(Integer reqUserId, Integer unfollowUserId) throws UserException {
		User reqUser = findUserById(reqUserId);
		User unfollowUser = findUserById(unfollowUserId);
		
		UserDto follower = new UserDto();
		
		follower.setEmail(reqUser.getEmailId());
		follower.setId(reqUser.getUserId());
		follower.setName(reqUser.getName());
		follower.setUserImage(reqUser.getImage());
		follower.setUsername(reqUser.getUsername());
		
		UserDto following = new UserDto();
		following.setEmail(unfollowUser.getEmailId());
		following.setId(unfollowUser.getUserId());
		following.setName(unfollowUser.getName());
		following.setUserImage(unfollowUser.getImage());
		following.setUsername(unfollowUser.getUsername());
		
		
		reqUser.getFollowing().remove(following);
		unfollowUser.getFollower().remove(follower);
		
		
		userRepository.save(unfollowUser);
		userRepository.save(reqUser);
		
		return "you are Unfollowing " +unfollowUser.getUsername();
		
	}

	@Override
	public List<User> findUserByIds(List<Integer> userIds) throws UserException {
		
		List<User> users = userRepository.findAllUsersByUserIds(userIds);
		
		
		return users;
	}

	@Override
	public List<User> searchUser(String query) throws UserException {
		 String quer = "%" + query.trim() + "%";
		
		List<User> users = userRepository.findByQuery(quer);
		
		if(users.size()==0) {
			throw new UserException("user not found");
		}
		return users;
	}

	@Override
	public User updateUserDetails(User updatedUser,MultipartFile file, User existingUser) throws UserException {

		if(updatedUser!=null) {
			
			
			if(updatedUser.getEmailId()!=null) {
				existingUser.setEmailId(updatedUser.getEmailId());
			}
			
			if(updatedUser.getBio()!=null) {
				existingUser.setBio(updatedUser.getBio());
			}
			if(updatedUser.getName()!=null) {
				existingUser.setName(updatedUser.getName());
			}
			if(updatedUser.getUsername()!=null) {
				
			     Optional<User> opt1 = this.userRepository.findByUsername(updatedUser.getUsername());
				  Optional<User> opt2 = this.userRepository.findByUsername(existingUser.getUsername());
			     if(opt1.isPresent()&& opt2.isPresent() && opt1.get()!=opt2.get()) {
			    	 
			    	 throw new UserException("this username is already Exist "+opt1.get().getUsername());
			     }
			     existingUser.setUsername(updatedUser.getUsername());
			}
			if(updatedUser.getGender()!=null) {
				existingUser.setGender(updatedUser.getGender());
			}
			if(updatedUser.getMobile()!=null) {
				existingUser.setMobile(updatedUser.getMobile());
			}
			if(updatedUser.getWebsite()!=null) {
				existingUser.setWebsite(updatedUser.getWebsite());
			}
		}
	
		if(file!=null) {
			
			 String oldImage = existingUser.getImage();
			 if(oldImage!=null&& !oldImage.isEmpty())
			 {
				 imageService.handleFileDelete(oldImage);
			 }
			System.out.println("====================");
			 String imageUrl = this.imageService.handleFileUpload(file);
			 
			existingUser.setImage(imageUrl);
		}
	
		
	
		if(existingUser.getUserId()!=null) {
			return userRepository.save(existingUser);
		
		}
		
		throw new UserException("you can't update this user");
	}

	@Override
	public List<User> findAllUsers() {
	 
		return this.userRepository.findAll();
	
	}

	@Override
	public List<User> findFollowUser() {
	
		
		return null;
	}
	
	
  
        
        
    }
    
    

	 

