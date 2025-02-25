<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rent House - User Management</title>
    <link rel="stylesheet" href="UserManagementStyle.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>User Management</h1>
        </header>

        <div class="user-list">
            <table>
                <thead>
                    <tr>
                        <th>Sl No</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>Aishi Das</td>
                        <td>aishidas@gmail.com</td>
                        <td>Admin</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
              
                    <tr>
                        <td>2</td>
                        <td>Aaindrila Ghosh</td>
                        <td>aaindighosh@gmail.com</td>
                        <td>Admin</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Shalini Yashmin</td>
                        <td>shaliniyashmin@gmail.com</td>
                        <td>Admin</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>Aarav Roy</td>
                        <td>aroy@gmail.com</td>
                        <td>Owner</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>Bhavya Singh</td>
                        <td>bhavyasingh@gmail.com</td>
                        <td>Tenant</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>Tanveer Evan</td>
                        <td>tanveerevan@gmail.com</td>
                        <td>Owner</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>Ekta Sen</td>
                        <td>ektasen@gmail.com</td>
                        <td>Tenant</td>
                        <td>
                            <button class="edit-btn">Edit</button>
                            <button class="delete-btn">Delete</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="add-user">
            <h2>Add New User</h2><br>
            <form>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="role">Role:</label>
                <input type="text" id="role" name="role" required>

                <button type="submit" class="add-btn">Add User</button>
            </form>
        </div>
    </div>
</body>
</html>