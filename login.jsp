<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String check = request.getParameter("status"); %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login page</title>
  <link rel="stylesheet" href="loginStyle.css">
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css'>
</head>
<body>
  <div class="wrapper">
    <form action="LoginController" method="post">
      <h1>Login</h1>
      <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
  <script type="text/javascript">
    // Show pop-up with the error message
    alert('<%= errorMessage %>');
  </script>
  <%
    }
  %>
      <div class="input-box">
        <input type="email" placeholder="Email" name="uemail" required>
        <i class='bx bxs-user'></i>
      </div>
      <div class="input-box" id="input-box-pass">
        <input type="password" placeholder="Password" name="upass" id="password" required>
        
        <span id="togglePass"><i class='fa-solid fa-eye'></i></span>
        <span id="togglePassSlash"><i class='fa-solid fa-eye-slash'></i></span>
      </div>
      <div class="remember-forgot">
        <label><input type="checkbox" class="rem-chckbox">Remember Me</label>
        <a href="ForgotPswd.jsp">Forgot Password</a>
      </div>
      <button type="submit" class="btn">Login</button>
      <div class="register">
        <p>Don't have an account? <a href="Register.jsp">Register</a></p>
      </div>
    </form>
  </div>
  
  <script>
    const togglePass = document.getElementById('togglePass');
    const togglePassSlash = document.getElementById('togglePassSlash');
    const passField = document.getElementById('password');

    // Initialize: Ensure only one icon is visible at a time
    togglePass.style.display = 'inline';
    togglePassSlash.style.display = 'none';

    togglePass.addEventListener('click', () => {
      passField.type = 'text'; // Show password
      togglePass.style.display = 'none'; // Hide eye icon
      togglePassSlash.style.display = 'inline'; // Show eye-slash icon
    });

    togglePassSlash.addEventListener('click', () => {
      passField.type = 'password'; // Hide password
      togglePassSlash.style.display = 'none'; // Hide eye-slash icon
      togglePass.style.display = 'inline'; // Show eye icon
    });
  </script>
</body>
</html>