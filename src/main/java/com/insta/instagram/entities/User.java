package com.insta.instagram.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.insta.instagram.dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	 @NotBlank(message = "Username cannot be blank")
	    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;
	 @NotBlank(message = "Name cannot be blank")
	    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
	 private String name;
	 @Email(message = "Email should be valid")
	    @NotBlank(message = "Email cannot be blank")
	private String emailId;
	 @Size(min = 10, max = 15, message = "Mobile number should be between 10 and 15 digits")
	private String mobile;
	 
	private String website;
	 @Size(max = 250, message = "Bio cannot be more than 250 characters")
	private String bio;
	private String gender;
	private String image;
	@NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String password;
	
	@Embedded
	@ElementCollection
	private Set<UserDto> follower = new HashSet<UserDto>();
	
	@Embedded
	@ElementCollection
	private Set<UserDto> following = new HashSet<UserDto>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Story> stories  = new ArrayList<>();
	
	@ManyToMany
	private List<Post> savedPost = new ArrayList<>();
}
