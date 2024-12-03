<%@ page import="java.util.List"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="com.swaroop.springmvc2.dto.Role"%>
<%@ page import="com.swaroop.springmvc2.dto.WebBlogDTO"%>
<%@ page import="com.swaroop.springmvc2.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Blog Posts</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
/* Custom Styling */
.card-title {
	font-weight: bold;
	color: #343a40;
}

.card-text {
	max-height: 100px; /* Limit content height */
	overflow: hidden;
	text-overflow: ellipsis;
}

.card:hover {
	transform: scale(1.02);
	transition: 0.3s;
}

.sort-select, .search-input, .btn-sort, .btn-search {
	margin-bottom: 10px;
	min-width: 150px;
}

.custom-message {
	margin: 40px 0;
	text-align: center;
	font-size: 1.5em;
	color: #6c757d;
}
</style>
</head>
<body>
	<%
	UserDTO user = (UserDTO) request.getAttribute("user");
	if (user == null) {
		user = (UserDTO) session.getAttribute("user");
	}
	request.setAttribute("user", user);
	%>
	<jsp:include page="/WEB-INF/views/navbar.jsp" />

	<div class="container mt-5">
		<!-- Page Header -->
		<div class="row">
			<div class="col">
				<h1 class="text-center mb-4">Blog Posts</h1>
			</div>
		</div>

		<!-- Sort and Search Form -->
		<div class="row justify-content-center mb-4">
			<div class="col-md-6 col-sm-12">
				<form method="get" class="form-inline justify-content-center">
					<label for="sort" class="mr-2">Sort By:</label> <select name="sort"
						id="sort" class="form-control sort-select mr-2">
						<option value="newest_first"
							<%="newest_first".equals(request.getParameter("sort")) ? "selected" : ""%>>Newest
							First</option>
						<option value="oldest_first"
							<%="oldest_first".equals(request.getParameter("sort")) ? "selected" : ""%>>Oldest
							First</option>
						<option value="author"
							<%="author".equals(request.getParameter("sort")) ? "selected" : ""%>>Author</option>
					</select>
					<button type="submit" class="btn btn-primary btn-sort">Sort</button>
				</form>
			</div>
			<div class="col-md-6 col-sm-12">
				<form action="./search" method="get"
					class="form-inline justify-content-center">
					<input type="text" name="query"
						class="form-control search-input mr-2" placeholder="Search Blogs"
						value="<%=request.getParameter("query")%>">
					<button type="submit" class="btn btn-success btn-search">Search</button>
				</form>
			</div>
		</div>

		<div class="row">
			<%
			@SuppressWarnings("unchecked")
			List<WebBlogDTO> blogs = (List<WebBlogDTO>) request.getAttribute("blogs");
			Role role = (Role) request.getAttribute("role");
			String sortParam = request.getParameter("sort");

			if (blogs != null && !blogs.isEmpty()) {
				// Perform sorting based on the selected parameter
				if ("author".equals(sortParam)) {
					Collections.sort(blogs, Comparator.comparing(WebBlogDTO::getAuthor));
				} else if ("newest_first".equals(sortParam)) {
					Collections.sort(blogs, Comparator.comparing(WebBlogDTO::getDate).reversed());
				} else if ("oldest_first".equals(sortParam)) {
					Collections.sort(blogs, Comparator.comparing(WebBlogDTO::getDate));
				}
			%>

			<!-- Display the sorted blogs dynamically -->
			<%
			for (WebBlogDTO blog : blogs) {
			%>
			<div class="col-lg-4 col-md-6 col-sm-12 mb-4">
				<div class="card h-100">
					<div class="card-body">
						<h5 class="card-title"><%=blog.getTitle()%></h5>
						<p class="card-text"><%=blog.getContent()%></p>
						<p class="text-muted">
							<small>Date: <%=blog.getDate()%></small><br> <small>Author:
								<%=blog.getAuthor()%></small>
						</p>
					</div>
					<div class="card-footer text-center">
						<form action="/like-blog" method="post" style="display: inline;">
							<!-- <button type="submit" > -->
							<a href="like-blog?blog-id=<%=blog.getId()%>&user-id=<%=blog.getUserId()%>"
								class="btn btn-success btn-sm"> Like (<%=blog.getLikes()%>)
							</a>
							<!-- </button> -->
						</form>
						<form action="/dislike-blog" method="post"
							style="display: inline;">
							<a href="dislike-blog?blog-id=<%=blog.getId()%>&user-id=<%=blog.getUserId()%>"
								class="btn btn-danger btn-sm"> Dislike (<%=blog.getDislikes()%>)
							</a>
						</form>
						<a href="view-blog?blog-id=<%=blog.getId()%>"
							class="btn btn-info btn-sm">View</a>
						<%
						if (role != null && role.equals(Role.ADMIN)) {
						%>
						<a
							href="delete-blog?blog-id=<%=blog.getId()%>&user-id=<%=blog.getUserId()%>"
							class="btn btn-danger btn-sm">Delete</a>
						<%
						}
						%>
					</div>
				</div>
			</div>
			<%
			}
			%>

			<%
			} else {
			%>
			<!-- Message when no blogs are available -->
			<div class="col-12">
				<div class="custom-message">
					<h3>No blogs available.</h3>
				</div>
			</div>
			<%
			}
			%>
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
