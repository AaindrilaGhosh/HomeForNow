<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%
    String successMessage = (String) request.getAttribute("success");
    String errorMessage = (String) request.getAttribute("error");
   %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="ResetPswdStyle.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script>
        function handleMessages() {
            <% if (successMessage != null) { %>
                alert('<%= successMessage.replace("'", "\\'").replace("\"", "\\\"") %>');
                window.location.href = 'login.jsp'; // Redirect after success
            <% } else if (errorMessage != null) { %>
                alert('<%= errorMessage.replace("'", "\\'").replace("\"", "\\\"") %>');
            <% } %>
        }
    </script>
</head>
<body>
<body onload="handleMessages()">
<div class="main-content">
     <div>
        <h1>Reset Password</h1>
    </div>
    
    <form action="ResetPswdController" method="POST">
       <input type="hidden" name="email" value="${param.email}" />  <!-- The email comes from the previous page -->
       <label for="new-pass" class="new">New Password</label><br>
       <div class="new-input-container">
           <input type="password" id="new-pass" class="new-box" name="new-pswd" required>
           <span id="toggleNewPass"><i class="fa-solid fa-eye"></i></span>
           <span id="toggleNewPassSlash"><i class="fa-solid fa-eye-slash"></i></span>
       </div>
       
       <!-- Confirm New Password -->
       <label for="conf-pass" class="conf-pass">Confirm New Password</label><br>
       <div class="conf-input-container">
           <input type="password" id="conf-pass" class="conf-box" name="conf-pswd" required>
           <span id="toggleConfPass"><i class="fa-solid fa-eye"></i></span>
           <span id="toggleConfPassSlash"><i class="fa-solid fa-eye-slash"></i></span>
       </div>
       
       <button type="submit" class="btn1">Save</button>
       <button type="submit" class="btn2" onclick="window.location.href='ForgotPswd.jsp'">Cancel</button>
    </form>
</div> 

<div class="forgot_img">
    <img src="Images/forgot-password.jpg" alt="">
</div>

<script>
    // New Password Toggle
    const toggleNewPass = document.getElementById('toggleNewPass');
    const toggleNewPassSlash = document.getElementById('toggleNewPassSlash');
    const newPassField = document.getElementById('new-pass');

    toggleNewPass.style.display = 'inline';
    toggleNewPassSlash.style.display = 'none';

    toggleNewPass.addEventListener('click', () => {
        newPassField.type = 'text'; // Show password
        toggleNewPass.style.display = 'none'; // Hide eye icon
        toggleNewPassSlash.style.display = 'inline'; // Show eye-slash icon
    });

    toggleNewPassSlash.addEventListener('click', () => {
        newPassField.type = 'password'; // Hide password
        toggleNewPassSlash.style.display = 'none'; // Hide eye-slash icon
        toggleNewPass.style.display = 'inline'; // Show eye icon
    });

    // Confirm Password Toggle
    const toggleConfPass = document.getElementById('toggleConfPass');
    const toggleConfPassSlash = document.getElementById('toggleConfPassSlash');
    const confPassField = document.getElementById('conf-pass');

    toggleConfPass.style.display = 'inline';
    toggleConfPassSlash.style.display = 'none';

    toggleConfPass.addEventListener('click', () => {
        confPassField.type = 'text'; // Show password
        toggleConfPass.style.display = 'none'; // Hide eye icon
        toggleConfPassSlash.style.display = 'inline'; // Show eye-slash icon
    });

    toggleConfPassSlash.addEventListener('click', () => {
        confPassField.type = 'password'; // Hide password
        toggleConfPassSlash.style.display = 'none'; // Hide eye-slash icon
        toggleConfPass.style.display = 'inline'; // Show eye icon
    });
</script>
</body>
</html>