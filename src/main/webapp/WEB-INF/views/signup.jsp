<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Page</title>
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
        .signup-container {
            background-color: #fff;
            padding: 20px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        .signup-container h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .signup-container table {
            width: 100%;
            margin-bottom: 15px;
            border-collapse: collapse;
        }
        .signup-container table td {
            padding: 10px;
        }
        .signup-container input[type="text"],
        .signup-container input[type="email"],
        .signup-container input[type="password"],
        .signup-container select {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .signup-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .signup-container input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .signup-container select {
            cursor: pointer;
        }
        .signup-container .error-message {
            color: red;
            margin-top: 15px;
        }
    </style>
</head>
<body>

    <div class="signup-container">
        <h2>Create a New Account</h2>
        <form action="./add-user" method="post">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" required autofocus></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="email" name="email" required></td>
                </tr>
                <tr>
                    <td>Mobile</td>
                    <td><input type="text" name="mobile" required></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" required></td>
                </tr>
                <tr>
                    <td>Role</td>
                    <td>
                        <select name="role" required>
                            <option value="" disabled selected>SELECT</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="USER">USER</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="submit" value="Signup">
        </form>
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
