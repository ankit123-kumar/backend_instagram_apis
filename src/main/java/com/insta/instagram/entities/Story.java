package com.insta.instagram.entities;

import java.time.LocalDateTime;

import com.insta.instagram.dto.UserDto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="stories")
public class Story {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer storyId;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="userId",column = @Column(name="user_id")),
		@AttributeOverride(name="emailId",column = @Column(name="user_emailId"))
	})
	private UserDto user;
	@NotNull
	private String image;
	private String caption;
	private LocalDateTime createdAt;
	
	
}
