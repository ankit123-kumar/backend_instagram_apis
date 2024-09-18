package com.insta.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insta.instagram.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
