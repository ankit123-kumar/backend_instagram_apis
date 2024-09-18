package com.insta.instagram.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.insta.instagram.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByEmailId(String emailId);
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.id IN :userIds")
	public List<User> findAllUsersByUserIds(@Param("userIds") List<Integer> userIds);

	
	@Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:query% OR u.emailId LIKE %:query%")
	public List<User> findByQuery(@Param("query") String query);

	
}
