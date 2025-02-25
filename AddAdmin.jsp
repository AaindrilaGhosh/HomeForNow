<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    String successMsg = (String) request.getAttribute("successMsg");
    String errorMsg = (String) request.getAttribute("errorMessage");
    
    System.out.println("successMsg" + successMsg);
    System.out.println("errorMsg" + errorMsg);%>
    
    <script type="text/javascript">
<% if (errorMsg != null && !errorMsg.isEmpty()) { %>
        alert('<%= errorMsg %>');
<% } %>

<% if (successMsg != null && !successMsg.isEmpty()) { %>
        alert('<%= successMsg %>');
<% } %>
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Admin</title>
    <link rel="stylesheet" href="AddAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Add Admin</h1>
        <form id="addAdminForm" action="AddAdminServlet" method="post">
            <label for="adminName">Admin Name</label>
            <input type="text" id="adminName" name="adminName" placeholder="Enter admin name" required>

            <label for="adminEmail">Admin Email</label>
            <input type="email" id="adminEmail" name="adminEmail" placeholder="Enter admin email" required>

            <label for="adminPassword">Password</label>
            <input type="password" id="adminPassword" name="adminPassword" placeholder="Enter password" required>

            <label for="adminConfirmPassword">Confirm Password</label>
            <input type="password" id="adminConfirmPassword" name="adminConfirmPassword" placeholder="Confirm password" required>

            <button type="submit">Add Admin</button>
        </form>
    </div>

    <script>
        const form = document.getElementById('addAdminForm');

        form.addEventListener('submit', (e) => {
            const adminPassword = document.getElementById('adminPassword').value;
            const adminConfirmPassword = document.getElementById('adminConfirmPassword').value;

            // Validate the form
            if (adminPassword !== adminConfirmPassword) {
                e.preventDefault();
                alert('Passwords do not match!');
                return;
            }
        });
    </script>
</body>
</html>
    