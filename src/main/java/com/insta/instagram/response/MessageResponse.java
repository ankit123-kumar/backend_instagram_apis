package com.insta.instagram.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MessageResponse {

     String message;

     public MessageResponse() {
		// TODO Auto-generated constructor stub
	}
	public MessageResponse(String message) {
		super();
		this.message = message;
	}
    
}
