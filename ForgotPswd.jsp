<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%// Retrieve the error message if available
      String errorMsg = (String) request.getAttribute("error"); %>
    <%
    if (errorMsg != null) {
  %>
    <script type="text/javascript">
        alert('<%= errorMsg %>'); // Show error message in a pop-up
    </script>
  <%
    }
  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="ForgotPswdStyle.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
    <div class="content">
        <div>
            <h1>Forgot Your Password?</h1>
        </div>
        <p>Enter your email and we'll help you reset your password</p>
        
        <form action="ForgotPswdController" method="post">
          <div class="email-box">
            <input type="email" placeholder="Enter your email" class="box" name="email" required><br>
            <button type="submit" class="btn">Continue</button>
          </div>
        </form>
    </div>
    
    <div class="forgot_img">
        <img src="Images/forgot-password.jpg" alt="">
    </div>
</body>
</html>