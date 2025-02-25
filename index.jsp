<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="org.model.bean.UserBean" %>
    <%@ page import="java.sql.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="org.misc.ConnectionProvider" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" href="indexStyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
  <div class="body-cont">
    <div class="header">
     <div class="logo">
        <a href="index.jsp"><img src="Images/homefornow.png" alt="logo"></a>
         <% String userEmail = (String) session.getAttribute("userEmail");
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
        				<a href="feedback.jsp" onclick="handleUserOptionChange('feedback')">Send Feedback</a>
        				<a href="help.jsp" onclick="handleUserOptionChange('help')">Help</a>
        				<a href="logout" onclick="handleUserOptionChange('logout')">Log Out</a>
    				</div>
				</div>
			  <%} %>
     
        	<script>
    			function handleUserOptionChange(option) {
    				if(option === 'my-posts') {
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
             <div class="title">
                    <h1>Properties for rent in Kolkata</h1>
                    <div class="lower-nav">
                        <h4><p>Are you an Owner?</p><a href="postProperty.jsp">Post property for free</a></h4>
                    </div>
                    
            </div>
    </div>      


    <!-- my part -->
    <div class="container">
        <div class="recent-adds">
            <h3>Recently Added</h3>
            <p class="recent-cap">Based on preferences of users like you</p>
            <a href="PropertyLists.jsp" class="see-all">See All>></a>
                <div class="recent-imgs">
            <%
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // Establish database connection
                con = ConnectionProvider.createCon();
                
                // Query to fetch the latest 3 properties
                String sql = "SELECT pi.property_id, pi.address, pi.type, pi.price, (SELECT imagePath FROM uploaded_image WHERE uploaded_image.property_id = pi.property_id LIMIT 1) AS imagePath FROM property_inquiries pi LIMIT 3;";
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();

                // Dynamically generate property cards
                while (rs.next()) {
                    int propertyId = rs.getInt("property_id");
                    String address = rs.getString("address");
                    String type = rs.getString("type");
                    double price = rs.getDouble("price");
                    String imagePath = rs.getString("imagePath");

                    String imageUrl = (imagePath != null && !imagePath.isEmpty()) 
                        ? request.getContextPath() + "/" + imagePath 
                        : "Images/default-property.jpg"; // Fallback image
            %>
            <div class="recent-one">
                <a href="PropertyDetails.jsp?id=<%= propertyId %>">
                    <img src="<%= imageUrl %>" alt="<%= type %> Image">
                </a>
                <p class="room-cap">
                    <a href="PropertyDetails.jsp?id=<%= propertyId %>"><b><%= type %></b><br>
                        <%= address %></a>
                </p>
                <p class="room-price"><b>₹<%= price %></b></p>
            </div>
            <% 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (rs != null) try { rs.close(); } catch (SQLException e) {}
                if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
                if (con != null) try { con.close(); } catch (SQLException e) {}
            }
            %>
            </div>
        </div>
    </div> 
</body>
</html>
<jsp:include page="footer.jsp"/>