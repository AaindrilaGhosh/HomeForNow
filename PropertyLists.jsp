<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.misc.ConnectionProvider" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Property List</title>
    <link rel="stylesheet" href="PropertyLists.css">
</head>
<body>
    <h1>Available Properties</h1>
    <div class="property-list">
        <%
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Connection con = ConnectionProvider.createCon();
        try {
            String sql = "SELECT pi.property_id, pi.address, pi.type, pi.carpet_area, pi.furnishing, pi.price, " +
                         "(SELECT imagePath FROM uploaded_image ui WHERE ui.property_id = pi.property_id LIMIT 1) AS imagePath " +
                         "FROM property_inquiries pi";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int propertyId = rs.getInt("property_id");
                String address = rs.getString("address");
                String type = rs.getString("type");
                float carpetArea = rs.getFloat("carpet_area");
                String furnishing = rs.getString("furnishing");
                double price = rs.getDouble("price");
                String imagePath = rs.getString("imagePath");
        %>
                <div class="property">
        <%
                if (imagePath != null && !imagePath.isEmpty()) {
                    String imageUrl = request.getContextPath() + "/" + imagePath; // Prepend context path
        %>
                    <img src="<%= imageUrl %>" alt="Property Image">
        <%
                }
        %>
                  <div class="property-head">
                    <h2><a href="PropertyDetails.jsp?id=<%= propertyId %>"><%= type %> in <%= address %></a></h2>
                    <p>Carpet Area: <%= carpetArea %> sq.ft. | Furnishing: <%= furnishing %> | Price: ₹<%= price %></p>
                  </div>
                
                <div class="side-box">    
              
                <%
    // Check if the user is logged in
    if (session.getAttribute("userId") != null) {
%>
        <%-- <a href="ContactOwner.jsp?property_id=<%= propertyId %>"><button class="btn-1">Contact Owner</button></a> --%>
        <a href="patuli_booking.jsp?property_id=<%= propertyId %>"><button class="btn-2">Book Now</button></a>
<%
    } else {
        // Redirect to login if the user is not logged in
        String loginPage = request.getContextPath() + "/login.jsp";
%>
        
        <a href="<%= loginPage %>"><button class="btn-2">Book Now</button></a>
<%
    }
%>
            
            </div>
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
    
</body>
</html>
<jsp:include page="footer.jsp"/>