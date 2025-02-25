<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.Property" %>
<%@ page import="java.util.List" %>
<% 
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null) {
%>
    <div class="error-message"><%= errorMessage %></div>
<% 
    }
    //String propertyId = request.getParameter("property_id"); // Get 'id' from URL
    //System.out.println("propertyid in adminjsp: " + propertyId);
    Property property = (Property) request.getAttribute("property");
    List<String> imagePaths = (List<String>) request.getAttribute("imagePaths");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Edit Property</title>
    <link rel="stylesheet" href="AdminEditProperty.css">
</head>
<body>
    <h1>Admin Edit Property</h1>
    <%
    		String propertyId = request.getParameter("property_id");
            System.out.println("ID before null property" + propertyId);
            if (property == null) {
            	out.println("<p>Error: Property not found.</p>");
            	response.sendRedirect("AdminEditPropertyServlet?property_id=" + propertyId);
                return; // Stop further processing if no property
            }
     %>
     
    <form action="AdminEditPropertyServlet" method="post" enctype="multipart/form-data">  
        <input type="hidden" name="property_id" value="<%= property.getPropertyId() %>" />
        <% System.out.println("propertyid in adminjsp: " + property.getPropertyId()); %>
        
        <!-- Existing Property Fields -->
        <label>Address:</label>
        <input type="text" name="address" value="<%= property.getAddress() %>" required /><br/>
        <label>Type:</label>
        <input type="text" name="type" value="<%= property.getType() %>" required /><br/>
        <label>Carpet Area:</label>
        <input type="text" name="carpetArea" value="<%= property.getCarpetArea() %>" required /><br/>
        <label>Furnishing:</label>
        <input type="text" name="furnishing" value="<%= property.getFurnishing() %>" required /><br/>
        <label>Price:</label>
        <input type="text" name="price" value="<%= property.getPrice() %>" required /><br/>
        <label>Owner Email:</label>
        <input type="email" name="ownerEmail" value="<%= property.getEmail() %>" required /><br/>

        <!-- Manage Images Section -->
        <h2>Manage Images</h2>
        <div class="mng-imgs">
            <%
                
                if (imagePaths != null && !imagePaths.isEmpty()) {
                    for (String imagePath : imagePaths) {
            %>
                <div class="image-item">
                    <img src="<%= imagePath %>" alt="Property Image">
                    <a href="AdminEditPropertyServlet?action=deleteImage&propertyId=<%= property.getPropertyId() %>&imagePath=<%= imagePath %>" class="delete-button">Delete</a>
                </div>
            <% 
                    }
                } else {
                    out.println("<p>No images available.</p>");
                }
            %>
        </div>
        <div>
            <h3>Add New Images</h3>
            <input type="file" name="images" multiple /><br/>
        </div>

         <p><%= property.getPropertyId()%></p>
        <button type="submit" class="submit-btn">Update</button>
    </form>
</body>
</html>