package com.insta.instagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insta.instagram.entities.User;
import com.insta.instagram.service.EmailService;

import jakarta.mail.Multipart;

@RestController
public class Email {

	@Autowired
	private EmailService emailService;
	@PostMapping("/send")
	public ResponseEntity<String> send(String subject,Multipart message,String to) {
		
		Boolean result = this.emailService.sendEmail(subject,message,to);
		
		 if(result) {
			  return new ResponseEntity<String>(HttpStatus.OK);
		 }
		  return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
