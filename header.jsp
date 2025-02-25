<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="org.model.bean.UserBean" %>
    <%--<% 
       //Assuming 'User' is a class with properties like 'username', 'email' etc.
       UserBean loggedInUser = (UserBean) session.getAttribute("userBean");
    %> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="headerStyle.css">
</head>
<body>
<div class="header">
     <div class="logo">
        <a href="index.jsp"><img src="Images/homefornow.png" alt="logo"></a>
        <!-- <div class="nav-bar">  -->
            <% 
                String userEmail = (String) session.getAttribute("userEmail");
            	String userName = (String) session.getAttribute("userName");
            	System.out.println("Useremail in home:"+ userEmail);
   				System.out.println("username in home:" + userName); %>
   				<div class="nav-bar <%= (userEmail != null && userName != null) ? "logged-in" : "" %>"> 
            <a href="postProperty.jsp">Post Property</a>
            <a href="PropertyLists.jsp">Property List</a>
            <a href="SavedItems.jsp">Saved Items</a>
            <a href="AboutUs.jsp">About us</a>
            <% if (userEmail == null || userName == null) { %>
       				<a href="Register.jsp">Login/Register</a>
			<% } else {%>
			    <div class="user-profile">
    				<i class="fa-solid fa-user" id="user-icon"></i>
    				<button onclick="toggleDropdown()" class="dropdown-toggle">
        			<i class="fa-solid fa-angle-down"></i> <!-- Arrow icon -->
   				 	</button>
    				<div class="dropdown" id="dropdown">
    					<a href="MyPostsServlet" onclick="handleUserOptionChange('my-posts')">My Posts</a>
    					<a href="BookedProperties.jsp" onclick="handleUserOptionChange('booked-properties')">Booked Properties</a>
        				<a href="Profile.jsp" onclick="handleUserOptionChange('prof-update')">Update Profile</a>
        				<a href="help.jsp" onclick="handleUserOptionChange('help')">Help</a>
        				<a href="feedback.jsp" onclick="handleUserOptionChange('feedback')">Send Feedback</a>
        				<a href="logout" onclick="handleUserOptionChange('logout')">Log Out</a>
    				</div>
				</div>
			  <%} %>
     
        	<script>
    			function handleUserOptionChange(option) {
    				if (option === 'my-posts') {
    					window.location.href = "MyPostsServlet";
    				}
    				else if(option === 'booked-properties') {
    					window.location.href = "BookedProperties.jsp";
    				}
    				else if (option === 'prof-update') {
           			 	window.location.href = "Profile.jsp"; // Redirect to the profile update page
        			} else if (option === 'logout') {
            			window.location.href = "logout"; // Redirect to the logout servlet or JSP
        			} else if(option == 'feedback'){
        				window.location.href = "feedback.jsp";
        			} else if(option == 'help') {
        				window.location.href = "help.jsp";
        			} else {
            			alert(`Selected option: ${option}`); // Placeholder for other options
        			}
        
        			// Close dropdown after selection
        			document.getElementById('dropdown').style.display = 'none';
    			}
    	
    			function toggleDropdown() {
    		        const dropdownMenu = document.getElementById('dropdown');
    		        if (dropdownMenu.style.display === 'block') {
    		            dropdownMenu.style.display = 'none';
    		        } else {
    		            dropdownMenu.style.display = 'block';
    		        }
    		    }

    		    // Close the dropdown when clicking outside of it
    		    document.addEventListener('click', function(event) {
    		        const dropdown = document.getElementById('dropdown');
    		        const toggleButton = document.querySelector('.dropdown-toggle');
    		        
    		        if (dropdown.style.display === 'block' 
    		            && !dropdown.contains(event.target) 
    		            && !toggleButton.contains(event.target)) {
    		            dropdown.style.display = 'none';
    		        }
    		    });
			</script>
        </div>
     </div>
</div>  
</body>
</html>