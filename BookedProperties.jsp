<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="org.misc.ConnectionProvider" %>
<jsp:include page="header.jsp"/>
<%
    // Get email from session
    String userEmail = (String) session.getAttribute("userEmail");

    // Check if the user is logged in
    if (userEmail == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = ConnectionProvider.createCon();

        // SQL query to fetch booked properties
        String sql = "SELECT p.property_id, p.type, p.address, b.booking_amount FROM bookings b JOIN property_inquiries p ON b.property_id = p.property_id WHERE b.email = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, userEmail);

        rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Booked Properties</title>
    <link rel="stylesheet" href="BookedProperties.css">
</head>
<body>
    <h1>Booked Properties</h1>
    <p>Below are the properties you have booked:</p>
    <table>
        <tr>
            <th>Property Name</th>
            <th>Address</th>
            <th>Booking Amount</th>
            <th>Cancellation</th>
        </tr>
        <% 
            // Check if there are bookings
            boolean hasBookings = false;
            while (rs.next()) {
                hasBookings = true;
        %>
        <tr>
            <td><%= rs.getString("type") %></td>
            <td>
                <a href="PropertyDetails.jsp?id=<%= rs.getInt("property_id") %>">
                    <%= rs.getString("address") %>
                </a>
            </td>
            <td><%= rs.getDouble("booking_amount") %></td>
            <td>
        <form action="CancelBookingServlet" method="post">
            <input type="hidden" name="property_id" value="<%= rs.getInt("property_id") %>">
            <input type="hidden" name="email" value="<%= userEmail %>">
            <button type="submit">Cancel</button>
        </form>
    </td>
        </tr>
        <% 
            } 
            if (!hasBookings) {
        %>
        <tr>
            <td colspan="3">No bookings found.</td>
        </tr>
        <% 
            }
        %>
    </table>
</body>
</html>
<%
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>Error fetching booked properties. Please try again later.</p>");
    } finally {
        // Close resources
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }
%>
<jsp:include page="footer.jsp"/>