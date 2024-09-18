package com.insta.instagram.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageStorage {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	
	private String imageName;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	@JsonBackReference
	private Post post;
}
