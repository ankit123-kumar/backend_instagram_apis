package com.insta.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insta.instagram.entities.ImageStorage;


public interface ImageRepository extends JpaRepository<ImageStorage, Integer> {

}
