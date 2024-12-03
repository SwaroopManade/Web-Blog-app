<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.swaroop.springmvc2.dto.Role"%>
<%@ page import="com.swaroop.springmvc2.dto.WebBlogDTO"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Blog</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>

body {
	background-color: #f8f9fa;
}

.container {
	margin-top: 50px;
	max-width: 600px;
}

.message {
	margin-top: 20px;
	color: #007bff; /* Bootstrap primary color */
}
</style>
</head>

<body>
	<div class="container">
		<h2 class="text-center">Add a New Blog</h2>
		<form action="./add-blog" method="post">
			<div class="form-group">
				<label for="title">Title</label> <input type="text"
					class="form-control" id="title" name="title" required autofocus>
			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea class="form-control" rows="12" id="content" name="content"
					placeholder="Write here.." required></textarea>
			</div>
			<div class="form-group">
				<label for="author">Author</label> <input type="text"
					class="form-control" id="author" name="author" required>
			</div>
			<button type="submit" class="btn btn-primary btn-block">Add
				Blog</button>
		</form>

		<%
		String message = (String) request.getAttribute("message");
		if (message != null) {
		%>
		<div class="alert alert-info message text-center">
			<h5><%=message%></h5>
		</div>
		<%
		}
		%>

		<div class="text-center">
			<br><a href="home" class="btn btn-secondary">Back to Home</a>
		</div>
	</div>
	

	<!-- Bootstrap JS and dependencies -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		
</body>

</html>

