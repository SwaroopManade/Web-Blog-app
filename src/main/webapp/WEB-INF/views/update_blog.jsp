<%@page import="com.swaroop.springmvc2.dto.WebBlogDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Blog</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- Added for responsiveness -->
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 20px;
    }

    .container {
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        padding: 30px;
        max-width: 600px;
        margin: 40px auto;
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .container:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    }

    table {
        width: 100%;
        margin: 20px 0;
    }

    td {
        padding: 15px 10px;
        text-align: left;
        vertical-align: middle;
    }

    input[type="text"], textarea {
        width: calc(100% - 20px);
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 6px;
        box-sizing: border-box;
        font-size: 16px;
    }

    textarea {
        resize: vertical; /* Allow vertical resizing */
        min-height: 120px;
    }

    input[type="submit"] {
        background-color: #007bff;
        color: white;
        padding: 12px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        width: 100%;
        font-size: 18px;
        transition: background-color 0.3s;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }

    h3 {
        color: #d9534f;
        text-align: center;
        margin-top: 15px;
        font-size: 18px;
    }

    a {
        display: block;
        text-align: center;
        margin-top: 20px;
        color: #007bff;
        font-size: 16px;
        text-decoration: none;
        transition: color 0.3s;
    }

    a:hover {
        color: #0056b3;
        text-decoration: underline;
    }

    /* Responsive Design */
    @media only screen and (max-width: 768px) {
        .container {
            padding: 20px;
            margin: 20px auto;
        }

        td {
            display: block;
            padding: 10px 0;
        }

        td input[type="text"], td textarea {
            width: 100%;
            margin-top: 5px;
        }
    }

    @media only screen and (max-width: 480px) {
        input[type="submit"] {
            padding: 10px;
            font-size: 16px;
        }

        h3 {
            font-size: 16px;
        }

        a {
            font-size: 14px;
        }
    }
</style>
</head>
<body>
    <% WebBlogDTO blog = (WebBlogDTO) request.getAttribute("blog"); %>
    <div class="container">
        <form action="./update-blog" method="post">
            <table>
                <tr>
                    <td>Id</td>
                    <td><input type="text" name="id" required="required" value="<%= blog.getId() %>" readonly="readonly"></td>
                </tr>
                <tr>
                    <td>Title</td>
                    <td><input type="text" name="title" required="required" value="<%= blog.getTitle() %>"></td>
                </tr>
                <tr>
                    <td>Content</td>
                    <td><textarea rows="12" name="content" maxlength="255"><%= blog.getContent() %></textarea></td>
                </tr>
                <tr>
                    <td>Author</td>
                    <td><input type="text" name="author" required="required" value="<%= blog.getAuthor() %>"></td>
                </tr>
            </table>
            <input type="submit" value="Update Blog">
        </form>
        <% String message = (String) request.getAttribute("message"); %>
        <% if (message != null) { %>
            <h3><%= message %></h3>
        <% } %>
        <a href="home">HOME</a>
    </div>
</body>
</html>
