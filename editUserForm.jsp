<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        form {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        button {
            padding: 10px 15px;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">Edit User</h1>
    <form action="UpdateUserServlet" method="post">
        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">
        <label for="name">Name</label>
        <input type="text" id="name" name="name" value="<%= request.getAttribute("name") %>" required>
        <label for="email">Email</label>
        <input type="email" id="email" name="email" value="<%= request.getAttribute("email") %>" required>
        <label for="phone">Password</label>
        <input type="text" id="password" name="password" value="<%= request.getAttribute("password") %>" required>
        <button type="submit">Update User</button>
    </form>
</body>
</html>