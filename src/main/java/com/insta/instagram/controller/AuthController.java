package com.insta.instagram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.entities.User;
import com.insta.instagram.exception.UserException;
import com.insta.instagram.repository.UserRepository;
import com.insta.instagram.service.EmailService;
import com.insta.instagram.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	@PostMapping("/signup")
	public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException{
		
		User createdUser = userService.registerUser(user);
        
		 //Boolean result = this.emailService.sendEmail("Welcome to "+user.getName()+" Visit Our Blog ","your login password : "+user.getPassword(),user.getEmailId());
			
		 
		return new ResponseEntity<User>(createdUser,HttpStatus.OK);
	
	}
	
	@GetMapping("/signin")
	public ResponseEntity<com.insta.instagram.entities.User> signinHandler(Authentication auth)throws BadCredentialsException{
		
		
			Optional<com.insta.instagram.entities.User> user = userRepository.findByEmailId(auth.getName());
			
			if(user.isPresent()) {
				
				return new ResponseEntity<com.insta.instagram.entities.User>(user.get(),HttpStatus.ACCEPTED);
				
					
			}
			
			throw new BadCredentialsException("Invalid Username and password");
	
			
		}
	
}
