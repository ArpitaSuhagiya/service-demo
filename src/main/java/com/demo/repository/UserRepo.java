package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {

	public User findByEmailId(String emailId);
	
}
