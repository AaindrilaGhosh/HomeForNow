<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.misc.ConnectionProvider" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Owner</title>
    <link rel="stylesheet" href="ContactOwner.css">
</head>
<body>
<h1>Contact Property Owner</h1>
<div class="owner-details-cont">
<%
    //String ownerEmail = request.getParameter("email");
    String propertyId = request.getParameter("property_id");
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection con = ConnectionProvider.createCon();
    
    // Variables to store owner details
    String name = null, phone = null, email = null;

    try {
    	String sql = "SELECT u.name, u.email, u.phone FROM users u JOIN property_inquiries p ON u.user_id = p.user_id WHERE p.property_id = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, propertyId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            name = rs.getString("name");
            email = rs.getString("email");
            phone = rs.getString("phone");
%>
            <h2>Owner Information</h2>
            <p><strong>Name:</strong> <%= name %></p>
            <p><strong>Email:</strong> <%= email %></p>
            <p><strong>Phone:</strong> <%= phone %></p>
<%
        } else {
%>
            <h2>Owner details not found.</h2>
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
</body>
</html>
<jsp:include page="footer.jsp" />