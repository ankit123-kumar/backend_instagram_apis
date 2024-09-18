package com.insta.instagram.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobleException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue,WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(ue.getMessage(),request.getDescription(false),LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> MethodArgumentNotValidException(MethodArgumentNotValidException me){
		
		ErrorDetails errorDetails = new ErrorDetails(me.getBindingResult().getFieldError().getDefaultMessage(),"validation error",LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CommentException.class)
	public ResponseEntity<ErrorDetails> CommentExceptionHandler(CommentException ue,WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(ue.getMessage(),request.getDescription(false),LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StoryException.class)
	public ResponseEntity<ErrorDetails> StoryExceptionHandler(StoryException ue,WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(ue.getMessage(),request.getDescription(false),LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> otherExceptionHandler(UserException ue,WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(ue.getMessage(),request.getDescription(false),LocalDateTime.now());
		
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
	}
	
	
}
