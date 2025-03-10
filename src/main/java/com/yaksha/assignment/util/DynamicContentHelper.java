package com.yaksha.assignment.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DynamicContentHelper {

	// Render a dynamic table based on the data passed
	public String renderTable(List<Map<String, Object>> data, List<String> columnNames) {
		StringBuilder tableHtml = new StringBuilder();
		tableHtml.append("<table border='1'>");

		// Render column headers
		tableHtml.append("<tr>");
		for (String column : columnNames) {
			tableHtml.append("<th>").append(column).append("</th>");
		}
		tableHtml.append("</tr>");

		// Render table rows based on the data
		for (Map<String, Object> row : data) {
			tableHtml.append("<tr>");
			for (String column : columnNames) {
				tableHtml.append("<td>").append(row.get(column)).append("</td>");
			}
			tableHtml.append("</tr>");
		}

		tableHtml.append("</table>");
		return tableHtml.toString();
	}

	// Render a dynamic form with fields for user input
	public String renderForm(Map<String, String> formFields) {
		StringBuilder formHtml = new StringBuilder();
		formHtml.append("<form action='/user/register' method='POST'>");

		// Dynamically generate form fields based on the provided map
		for (Map.Entry<String, String> entry : formFields.entrySet()) {
			formHtml.append("<label for='").append(entry.getKey()).append("'>").append(entry.getValue())
					.append(":</label>").append("<input type='text' name='").append(entry.getKey()).append("' id='")
					.append(entry.getKey()).append("' required/><br><br>");
		}

		formHtml.append("<button type='submit'>Submit</button>");
		formHtml.append("</form>");

		return formHtml.toString();
	}

	// Render pagination controls (optional if you have pagination)
	public String renderPagination(int currentPage, int totalPages) {
		StringBuilder paginationHtml = new StringBuilder();
		paginationHtml.append("<div class='pagination'>");

		if (currentPage > 1) {
			paginationHtml.append("<a href='/user/dashboard?page=").append(currentPage - 1).append("'>Previous</a>");
		}

		paginationHtml.append(" Page ").append(currentPage).append(" of ").append(totalPages);

		if (currentPage < totalPages) {
			paginationHtml.append("<a href='/user/dashboard?page=").append(currentPage + 1).append("'>Next</a>");
		}

		paginationHtml.append("</div>");
		return paginationHtml.toString();
	}
}
