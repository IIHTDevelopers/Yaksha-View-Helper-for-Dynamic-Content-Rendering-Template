package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yaksha.assignment.util.DynamicContentHelper;
import com.yaksha.assignment.utils.CustomParser;

public class ProductControllerTest {

	private DynamicContentHelper dynamicContentHelper;

	@BeforeEach
	public void setup() {
		// Initialize DynamicContentHelper before each test
		dynamicContentHelper = new DynamicContentHelper();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testRenderTable() throws IOException {
		// Create sample data for testing renderTable
		List<Map<String, Object>> data = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		columns.add("ID");
		columns.add("Name");
		columns.add("Email");

		// Sample row data
		Map<String, Object> row1 = new HashMap<>();
		row1.put("ID", 1);
		row1.put("Name", "John Doe");
		row1.put("Email", "john@example.com");
		data.add(row1);

		Map<String, Object> row2 = new HashMap<>();
		row2.put("ID", 2);
		row2.put("Name", "Jane Smith");
		row2.put("Email", "jane@example.com");
		data.add(row2);

		// Render the table using the helper method
		String tableHtml = dynamicContentHelper.renderTable(data, columns);

		// Check if the table HTML contains correct data
		boolean hasTable = tableHtml.contains("<table border='1'>");
		boolean hasHeaders = tableHtml.contains("<th>ID</th>") && tableHtml.contains("<th>Name</th>")
				&& tableHtml.contains("<th>Email</th>");
		boolean hasRowData = tableHtml.contains("<td>1</td>") && tableHtml.contains("<td>John Doe</td>");
		boolean hasRow2Data = tableHtml.contains("<td>2</td>") && tableHtml.contains("<td>Jane Smith</td>");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasTable && hasHeaders && hasRowData && hasRow2Data, businessTestFile);
	}

	@Test
	public void testRenderForm() throws IOException {
		// Create sample form fields for testing renderForm
		Map<String, String> formFields = new HashMap<>();
		formFields.put("name", "Name");
		formFields.put("email", "Email");
		formFields.put("password", "Password");

		// Render the form using the helper method
		String formHtml = dynamicContentHelper.renderForm(formFields);

		// Check if the form HTML contains correct form tags and input fields
		boolean hasFormAction = formHtml.contains("<form action='/user/register' method='POST'>");
		boolean hasNameField = formHtml.contains("<label for='name'>Name:</label>");
		boolean hasEmailField = formHtml.contains("<label for='email'>Email:</label>");
		boolean hasPasswordField = formHtml.contains("<label for='password'>Password:</label>");
		boolean hasSubmitButton = formHtml.contains("<button type='submit'>Submit</button>");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(),
				hasFormAction && hasNameField && hasEmailField && hasPasswordField && hasSubmitButton,
				businessTestFile);
	}

	@Test
	public void testUserControllerAnnotations() throws Exception {
		// Check for @Controller annotation on UserController class
		boolean hasControllerAnnotation = CustomParser.checkClassAnnotation(
				"src/main/java/com/yaksha/assignment/controller/UserController.java", "Controller");

		// Check for @GetMapping("/") on showRegistrationForm() method
		boolean hasGetMappingRegistrationForm = CustomParser.checkMethodAnnotationWithValueAgain(
				"src/main/java/com/yaksha/assignment/controller/UserController.java", "showRegistrationForm", "/");

		// Check for @GetMapping("/user/success") on showSuccessPage() method
		boolean hasGetMappingSuccessPage = CustomParser.checkMethodAnnotationWithValueAgain(
				"src/main/java/com/yaksha/assignment/controller/UserController.java", "showSuccessPage",
				"/user/success");

		// Check for @GetMapping("/user/list") on listUsers() method
		boolean hasGetMappingUserList = CustomParser.checkMethodAnnotationWithValueAgain(
				"src/main/java/com/yaksha/assignment/controller/UserController.java", "listUsers", "/user/list");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasControllerAnnotation && hasGetMappingRegistrationForm && hasGetMappingSuccessPage
				&& hasGetMappingUserList, businessTestFile);
	}

	@Test
	public void testRegistrationJspTagsAndFormAction() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/register.jsp";

		// Check for form action URL and required input fields
		boolean hasFormTag = CustomParser.checkJspTagPresence(filePath, "dynamicForm");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasFormTag, businessTestFile);
	}

	@Test
	public void testUserListPageDynamicContent() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/user-list.jsp";

		// Check if dynamic content like user attributes are being rendered
		boolean hasUserId = CustomParser.checkJspTagPresence(filePath, "usersTable");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasUserId, businessTestFile);
	}

	@Test
	public void testSuccessPageContent() throws Exception {
		String filePath = "src/main/webapp/WEB-INF/views/registration-success.jsp";

		// Check for success message and the back link
		boolean hasSuccessMessage = CustomParser.checkJspTagPresence(filePath, "<h1>");
		boolean hasBackLink = CustomParser.checkJspTagPresence(filePath, "<a href");

		// Run auto-grading using yakshaAssert
		yakshaAssert(currentTest(), hasSuccessMessage && hasBackLink, businessTestFile);
	}
}
