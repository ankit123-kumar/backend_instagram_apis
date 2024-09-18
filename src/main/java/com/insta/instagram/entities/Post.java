package com.insta.instagram.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.insta.instagram.dto.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Postid;
	private String caption;
	private String location;
	private LocalDateTime createdAt;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="id",column = @Column(name="user_id")),
		@AttributeOverride(name="email",column = @Column(name="user_emailId"))
	})
	private UserDto user;
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
		private List <ImageStorage> images = new ArrayList<ImageStorage>();
		
	@OneToMany
	private List<Comment> comments = new ArrayList<Comment>();
	
	@Embedded
	@ElementCollection
	@JoinTable(name="likedByUsers",joinColumns = @JoinColumn(name="post_id"))
	
	private Set<UserDto> likedByUsers = new HashSet<>();
}
