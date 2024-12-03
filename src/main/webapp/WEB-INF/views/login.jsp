<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: #fff;
            padding: 20px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        .login-container h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .login-container table {
            width: 100%;
            margin-bottom: 15px;
        }
        .login-container table td {
            padding: 10px;
        }
        .login-container input[type="email"],
        .login-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .login-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .login-container input[type="submit"]:hover {
            background-color: #218838;
        }
        .login-container h6 {
            margin-top: 20px;
            color: #666;
        }
        .login-container h6 a {
            color: #007bff;
            text-decoration: none;
        }
        .login-container h6 a:hover {
            text-decoration: underline;
        }
        .login-container .error-message {
            color: red;
            margin-top: 15px;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <h2>Login</h2>
        <form action="./login" method="post">
            <table>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" required></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" required></td>
                </tr>
            </table>
            <input type="submit" value="Login">
        </form>
        <h6>
            New User? Sign up <a href="signup">here</a>
        </h6>
        <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
        %>
        <div class="error-message"><%= message %></div>
        <%
        }
        %>
    </div>

</body>
</html>
