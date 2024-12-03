<%@ page import="com.swaroop.springmvc2.dto.Role" %>
<%@ page import="com.swaroop.springmvc2.dto.UserDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .navbar-custom {
            background-color: navy;
        }
        .navbar-custom .nav-link {
            color: white !important;
            transition: color 0.3s ease-in-out;
        }
        .navbar-custom .nav-link:hover {
            color: lightgray !important;
        }
        .navbar-custom .navbar-brand {
            color: white !important;
        }
    </style>
</head>
<body>
    <%
    UserDTO user = (UserDTO) request.getAttribute("user");
    %>
    <nav class="navbar navbar-expand-lg navbar-custom">
        <a class="navbar-brand" href="#">Welcome, <%= user.getUserName() %></a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon" style="color: white;">&#9776;</span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="edit-user">Edit Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="delete-user">Delete Account</a>
                </li>
                <% if (user.getRole().equals(Role.USER)) { %>
                <li class="nav-item">
                    <a class="nav-link" href="add-blog-page">Add Blog</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="my-blogs">My Blogs</a>
                </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="blogs">All Blogs</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
        %>
        <div class="alert alert-info text-center">
            <h3><%= message %></h3>
        </div>
        <%
        }
        %>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
