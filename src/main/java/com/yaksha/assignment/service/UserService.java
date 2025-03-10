package com.yaksha.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yaksha.assignment.models.User;

@Service
public class UserService {

	private List<User> users = new ArrayList<>();

	// Register a new user
	public void registerUser(User user) {
		user.setId(users.size() + 1); // Simulating an auto-generated ID
		users.add(user);
	}

	// Get all users
	public List<User> getAllUsers() {
		return users;
	}
}
