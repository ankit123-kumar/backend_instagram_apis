package com.insta.instagram.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.insta.instagram.entities.Story;
import com.insta.instagram.exception.StoryException;
import com.insta.instagram.exception.UserException;

public interface StoryService {

	public Story createStory(Story story,MultipartFile file,Integer userId);
	
	public List<Story> findStoryByUserId(Integer userId) throws UserException,StoryException;
	
	public String deleteStory(Integer storyId, Integer userId) throws UserException, StoryException;
	
}
