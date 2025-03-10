package com.yaksha.assignment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.yaksha.assignment.models.User;
import com.yaksha.assignment.service.UserService;
import com.yaksha.assignment.util.DynamicContentHelper;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private DynamicContentHelper dynamicContentHelper;

	// Show the registration form
	@GetMapping("/")
	public String showRegistrationForm(Model model) {
		// Create a map to hold the form fields and their labels
		Map<String, String> formFields = new HashMap<>();
		formFields.put("name", "Name");
		formFields.put("email", "Email");
		formFields.put("password", "Password");

		// Use the helper to render the dynamic form
		String dynamicForm = dynamicContentHelper.renderForm(formFields);

		// Add dynamic form content to the model
		model.addAttribute("dynamicForm", dynamicForm);

		return "register";
	}

	// Handle form submission for user registration
	@PostMapping("/user/register")
	public String registerUser(User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register"; // If there are validation errors, show the form again
		}

		userService.registerUser(user); // Call the service to save the user
		return "redirect:/user/success"; // Redirect to success page after successful registration
	}

	// Show success message after successful registration
	@GetMapping("/user/success")
	public String showSuccessPage(Model model) {
		model.addAttribute("message", "Your registration was successful!");
		return "registration-success";
	}

	// Show a list of users in a dynamic table
	@GetMapping("/user/list")
	public String listUsers(Model model) {
		List<User> users = userService.getAllUsers();

		// Create dynamic content for users table
		List<Map<String, Object>> tableData = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		columns.add("ID");
		columns.add("Name");
		columns.add("Email");

		for (User user : users) {
			Map<String, Object> row = new HashMap<>();
			row.put("ID", user.getId());
			row.put("Name", user.getName());
			row.put("Email", user.getEmail());
			tableData.add(row);
		}

		// Use the helper to render the table HTML
		String tableHtml = dynamicContentHelper.renderTable(tableData, columns);
		model.addAttribute("usersTable", tableHtml);

		return "user-list";
	}
}
