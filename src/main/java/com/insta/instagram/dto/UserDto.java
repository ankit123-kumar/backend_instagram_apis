package com.insta.instagram.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Integer id;
	private String username;
	private String name;
	private String email;
	private String userImage;
	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, userImage, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(userImage, other.userImage) && Objects.equals(username, other.username);
	}
	
	
	

}
