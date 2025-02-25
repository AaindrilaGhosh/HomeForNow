<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="org.model.bean.UserBean" %>
    <%
    String successMsg = (String) request.getAttribute("successMsg");
    String errorMsg = (String) request.getAttribute("errrorMessage");
    
    System.out.println("successMsg" + successMsg);
    System.out.println("errorMsg" + errorMsg);
%>

<script type="text/javascript">
<% if (errorMsg != null && !errorMsg.isEmpty()) { %>
        alert('<%= errorMsg %>');
<% } %>

<% if (successMsg != null && !successMsg.isEmpty()) { %>
        alert('<%= successMsg %>');
<% } %>
</script>
       
    
   <% 
      String userName = (String) session.getAttribute("userName");
      String userEmail = (String) session.getAttribute("userEmail");
      String userPass = (String) session.getAttribute("userPass");
      Integer userId = (Integer) session.getAttribute("userId");
      System.out.println("Retrieved UserId type: " + (userId != null ? userId.getClass().getName() : "null"));
      //System.out.println("Profile page" + userName);
      
      
      if (userName == null || userEmail == null) {
        response.sendRedirect("Register.jsp");
      return;
    }
   %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="ProfileStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
</head>
<body>
    <div class="profile-cont">
        <h2>User Profile</h2>
        <form action="UpdateProfileServlet" method="post">
            <!-- Hidden field for userId -->
            <input type="hidden" name="userId" value="<%= userId %>">
            
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="<%= userName %>" required><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= userEmail %>" required><br>
        
            
            <label for="pass">Password:</label>
            <div class="pass">
    			<input type="password" id="pass" name="pass" placeholder="Enter new password">
    			<!-- <button type="button" id="togglePass">
        		Show
    			</button>  -->
    			
    			<span id="togglePass"><i class="fa-solid fa-eye"></i></span>
				<span id="togglePassSlash"><i class="fa-solid fa-eye-slash" id="togglePassSlash"></i></span>
    		</div>
            <button type="submit">Update Profile</button>
        </form>
    </div>
    <script>
        const togglePass = document.getElementById('togglePass');
        const togglePassSlash = document.getElementById('togglePassSlash');
        const passField = document.getElementById('pass');
        
        // Initialize: Ensure only one icon is visible at a time
        togglePass.style.display = "inline";
        togglePassSlash.style.display = "none";
        //console.log(togglePass, togglePassSlash, passField);
        
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