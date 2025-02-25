<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.Property" %>
<jsp:include page="header.jsp"/>

<% 
    String errMsg = request.getParameter("errorMsg"); 

    // Check if the user is logged in
    String userEmail = (String) request.getSession().getAttribute("userEmail");
    if (userEmail == null) {
        // User is not logged in, redirect to the login page
        response.sendRedirect("login.jsp"); // Adjust this to your login page
        return; // Exit the JSP
    }
%>
<script type="text/javascript">
<% if (errMsg != null && !errMsg.isEmpty()) { %>
    alert('<%= errMsg %>');
<% } %>
</script>
<!DOCTYPE html>
<html>
<head>
    <title>My Posts</title>
    <link rel="stylesheet" href="MyPosts.css">
</head>
<body>
    <h1>My Posts</h1>
    <script type="text/javascript">
    <% if (errMsg != null && !errMsg.isEmpty()) { %>
        alert('<%= errMsg %>');
    <% } %>
    </script>
    <%
        List<Property> properties = (List<Property>) request.getAttribute("properties");
        if (properties != null && !properties.isEmpty()) { 
    %>
        <div class="property-list">
            <% for (Property property : properties) { %>
                <div class="property-item">
                    <a href="PropertyDetails.jsp?id=<%= property.getPropertyId() %>">
                        <div class="property-image">
                            <img src="<%= property.getImagePath() %>" alt="Property Image" width="100">
                        </div>
                        <div class="property-info">
                    		<h2><a href="PropertyDetails.jsp?id=<%= property.getPropertyId() %>"><%= property.getType() %> in <%= property.getAddress() %></a></h2>
                    	    <p>Carpet Area: <%= property.getCarpetArea() %> sq.ft. | Furnishing: <%= property.getFurnishing() %> | Price: ₹<%= property.getPrice() %></p>
                  		</div>
                    </a>
                    <div class="property-actions">
                        <a href="EditPropertyServlet?propertyId=<%= property.getPropertyId() %>" id="btn">Edit</a>
                        <a href="DeletePropertyServlet?propertyId=<%= property.getPropertyId() %>" onclick="return confirm('Are you sure?')" id="btn">Delete</a>
                    </div>
                </div>
            <% } %>
        </div>
    <% } else { 
    	//response.sendRedirect("MyPostsServlet");
    %>
    	<!-- If no posts are available, show a message -->
        <div class="no-posts">
            <h3>No posts available.</h3>
            <p>You haven't added any properties yet. Click <a href="postProperty.jsp">here</a> to add a new property.</p>
        </div>
   <% } %>
</body>
</html>
<jsp:include page="footer.jsp"/>
