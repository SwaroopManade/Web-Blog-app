<%@page import="com.swaroop.springmvc2.dto.UserDTO"%>
<%@page import="com.swaroop.springmvc2.dto.Role"%>
<%@ page import="com.swaroop.springmvc2.dto.WebBlogDTO" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Posts</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Custom Styling */
        .card {
            border: 1px solid #ddd;
            border-radius: 10px;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .card:hover {
            transform: scale(1.03);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .card-title {
            font-weight: 600;
        }
        .card-text {
            height: 70px;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .text-muted small {
            display: block;
        }
        .btn-sm {
            margin: 0 5px;
        }
        .message {
            margin: 20px 0;
            text-align: center;
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
    <div class="row">
        <div class="col">
            <h1 class="text-center mb-4">Blog Posts</h1>
        </div>
    </div>

    <div class="row">
        <%
            @SuppressWarnings("unchecked")
            List<WebBlogDTO> blogs = (List<WebBlogDTO>) request.getAttribute("blogs");
            
            if (blogs != null && !blogs.isEmpty()) {
                for (WebBlogDTO blog : blogs) {
        %>
            <div class="col-lg-4 col-md-6 col-sm-12 mb-4 d-flex align-items-stretch">
                <div class="card w-100">
                    <div class="card-body">
                        <h5 class="card-title"><%= blog.getTitle() %></h5>
                        <p class="card-text"><%= blog.getContent() %></p>
                        <p class="text-muted">
                            <small>Date: <%= blog.getDate() %></small>
                            <small>Author: <%= blog.getAuthor() %></small>
                        </p>
                    </div>
                    <div class="card-footer text-center">
                        
                        <% if (user != null && user.getRole().equals(Role.USER)) { %>
                            <a href="edit-blog?blog-id=<%= blog.getId() %>" class="btn btn-primary btn-sm">Update</a>
                            <a href="delete-blog?blog-id=<%= blog.getId() %>&user-id=<%= user.getId() %>" class="btn btn-danger btn-sm">Delete</a>
                        <% } %>
                    </div>
                </div>
            </div>
        <%
                }
            } else {
        %>
            <div class="col-12">
                <h3 class="text-center text-secondary">No blogs available.</h3>
            </div>
        <%
            }
        %>
    </div>

    <!-- Display feedback message if available -->
    <div class="message">
        <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
        %>
        <div class="alert alert-info"><%= message %></div>
        <%
        }
        %>
        <a href="home" class="btn btn-secondary">HOME</a>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
