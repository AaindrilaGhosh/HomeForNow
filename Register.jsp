<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String str=request.getParameter("msg"); %>
<%
    // Retrieve the error message if available
    String errorMessage = (String) request.getAttribute("errorMessage");
    // Retrieve the msg parameter to check for success
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="RegStyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<%
    if (errorMessage != null) {
  %>
    <script type="text/javascript">
        alert('<%= errorMessage %>'); // Show error message in a pop-up
    </script>
  <%
    }
  %>
  
    <!-- Show success message pop-up if registration was successful -->
  <%
    if ("valid".equals(msg)) {
  %>
    <script type="text/javascript">
        alert('Registration successful!'); // Show success message in a pop-up
     // Redirect to the login page after a delay (2 seconds in this case)
        setTimeout(function() {
            window.location.href = "login.jsp";  // Redirect to login page
        }, 500);  // 2000 milliseconds (1 seconds) delay
    </script>
  <%
    }
  %>
  <form action="RegisterController" method="post">
    <div class="reg-cont">
        <h1 class="reg-head">Register</h1>
        <input type="text" id="name" placeholder="Username" name="username" required>
        <i class="fa-solid   d fa-user" style="color: #000000;"></i>
        <input type="email" id="email" placeholder="Email" name="email" required>
        <i class="fa-solid fa-envelope" style="color: #000000;"></i>
        <input type="tel" id="phone" placeholder="Phone Number" name="phone" required pattern="[0-9]{10}" title="Enter a valid 10-digit phone number">
        <i class="fa-solid fa-phone" style="color: #000000;"></i>
        
        
        
        <div class="pass">
            <input type="password" id="pswd" placeholder="Password" name="pswd" required>
            <%-- <i class="fa-solid fa-lock" style="color: #000000;"></i> --%>
            <span id="togglePswd"><i class="fa-solid fa-eye"></i></span>
            <span id="togglePswdSlash"><i class="fa-solid fa-eye-slash"></i></span>
        </div>
        
        
        <div class="agreement">
            <input type="checkbox" id="agreement">
            <p>I agree to HomeForNow's <a href="Terms&Conditions.jsp">Terms of Service.</a></p>
        </div>
        <button id="reg" type="submit">Register</button>
        <p class="login">Already have an account? <a href="login.jsp">Login</a></p>
    </div>
   </form>
   
   <script>
       const togglePswd = document.getElementById('togglePswd');
       const togglePswdSlash = document.getElementById('togglePswdSlash');
       const pswdField = document.getElementById('pswd');
       
       // Initialize: Ensure only one icon is visible at a time
       togglePswd.style.display = "inline";
       togglePswdSlash.style.display = "none";

       togglePswd.addEventListener('click', () => {
           pswdField.type = 'text'; // Show password
           togglePswd.style.display = 'none'; // Hide eye icon
           togglePswdSlash.style.display = 'inline'; // Show eye-slash icon
       });

       togglePswdSlash.addEventListener('click', () => {
           pswdField.type = 'password'; // Hide password
           togglePswdSlash.style.display = 'none'; // Hide eye-slash icon
           togglePswd.style.display = 'inline'; // Show eye icon
       });
   </script>
</body>
</html>