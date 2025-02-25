<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.misc.ConnectionProvider" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Property Details</title>
    <link rel="stylesheet" href="PropertyDetails.css">
</head>
<body>
<h1>Property Details</h1>
<div class="details-cont">
<%
	//Get session and check if the user is logged in
	boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

    String propertyId = request.getParameter("id");
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection con = ConnectionProvider.createCon();
    
    // Variables to store property details
    String email = null, address = null, type = null, furnishing = null, details = null, rules = null, cancellation = null;
    float carpetArea = 0;
    double price = 0;
    List<String> imagePaths = new ArrayList<>(); // List to store image paths
    
    try {
        String sql = "SELECT pi.*, ui.imagePath FROM property_inquiries pi LEFT JOIN uploaded_image ui ON pi.property_id = ui.property_id WHERE pi.property_id = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, Integer.parseInt(propertyId));
        rs = pstmt.executeQuery();

        boolean propertyFound = false;

        while (rs.next()) {
            if (!propertyFound) {
                // capture property details 
                email = rs.getString("email");
                address = rs.getString("address");
                type = rs.getString("type");
                carpetArea = rs.getFloat("carpet_area");
                furnishing = rs.getString("furnishing");
                price = rs.getDouble("price");
                details = rs.getString("details");
                rules = rs.getString("rules");
                cancellation = rs.getString("cancellation");
                
                propertyFound = true; // Mark that the property has been found
            }
         // Capture image paths
            String imagePath = rs.getString("imagePath");
            if (imagePath != null && !imagePath.isEmpty()) {
                imagePaths.add(request.getContextPath() + "/" + imagePath); // Prepend context path
            }
        }

        if (propertyFound) {
        	
%>
         <!-- Display Property Details -->
            <h2><%= type %> in <%= address %></h2>
        <!-- Display Images -->
        <h3>Images</h3>
        <div class="image-gallery">
<%
            if (imagePaths.isEmpty()) {
%>
            <p>No images available for this property.</p>
<%
            } else {
                for (String imageUrl : imagePaths) {
%>
            <img src="<%= imageUrl %>" alt="Property Image">
<%
                }
            }
%>
        </div>  
            
                <h3>Details</h3>
        
<%
     if (isLoggedIn) {
%>
        <form action="SavePropertyServlet" method="post">
    <!-- Dynamically set hidden fields for the property details -->
    <input type="hidden" name="imagePath" value="<%= imagePaths.isEmpty() ? "" : imagePaths.get(0) %>">
    <input type="hidden" name="address" value="<%= address %>">
    <input type="hidden" name="type" value="<%= type %>">
    <input type="hidden" name="price" value="<%= price %>">

    <button type="submit" id="save-btn">Save</button>
</form>

<%
        } else {
%>
        <p><a href="login.jsp">Log in</a> to save or book this property.</p>
<% } %>
                <p>Carpet Area: <%= carpetArea %> sqft</p>
                <p>Furnishing: <%= furnishing %></p>
                <p>Price: ₹<%= price %></p>
                
                <div>          
                <%
        if (details != null && !details.isEmpty()) {
            // Split details by newlines or the appropriate delimiter
            String[] detailLines = details.split("\n");
            for (String line : detailLines) {
%>
            <p><%= line %></p>
<%
            }
        } else {
%>
            <p>No details available.</p>
<%
        }
%>  </div>
                <h3>Rules</h3>
                <p><%= rules %></p>
                <h3>Cancellation Policy</h3>
                <p><%= cancellation %></p>
                <%-- <a href="ContactOwner.jsp?property_id=<%= propertyId %>"><button id="contact-btn">Contact Owner</button></a> --%>
                <a href="patuli_booking.jsp?property_id=<%= propertyId %>"><button id="book-btn">Book Now</button></a>
               
<%            
            } else {
%>
            	<h2>Property not found</h2>
<%
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
        if (con != null) try { con.close(); } catch (SQLException e) {}
    }
%>
</div>
</form>
</body>
</html>
<jsp:include page="footer.jsp"/>