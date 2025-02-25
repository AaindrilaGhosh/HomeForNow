<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.Property" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Property</title>
    <link rel="stylesheet" href="EditProperty.css">
</head>
<body>
    <h1>Edit Property</h1>
    <form action="EditPropertyServlet" method="post" enctype="multipart/form-data">
        <%
            Property property = (Property) request.getAttribute("property");
            if (property == null) {
                out.println("<p>Error: Property not found.</p>");
                return; // Stop further processing
            }
        %>
        
        <input type="hidden" name="propertyId" value="<%= property.getPropertyId() %>" />
        
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

        <!-- Manage Images Section -->
        <h2>Manage Images</h2>
        
        <!-- Display Existing Images -->
        <div class="mng-imgs">
            <!-- <h3>Current Images</h3>  -->
            <%
                List<String> imagePaths = (List<String>) request.getAttribute("imagePaths");
                if (imagePaths != null && !imagePaths.isEmpty()) {
                    for (String imagePath : imagePaths) {
            %>
                <div class="image-item">
                    <img src="<%= imagePath %>" alt="Property Image">
                    <a href="EditPropertyServlet?action=deleteImage&propertyId=<%= property.getPropertyId() %>&imagePath=<%= imagePath %>" class="delete-button">Delete</a>
                </div>
            <% 
                    }
                } else {
                    out.println("<p>No images available.</p>");
                }
            %>
        </div>

        <!-- Add New Images -->
        <div>
            <h3>Add New Images</h3>
            <input type="file" name="images" multiple /><br/>
        </div>

        <button type="submit" class="submit-btn">Update</button>
    </form>
</body>
</html>
<jsp:include page="footer.jsp"/>