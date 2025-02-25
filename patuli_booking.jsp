<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="org.misc.ConnectionProvider" %>
<%@ page import="java.io.PrintWriter" %>

<%
    String propertyId = request.getParameter("property_id"); // Assume this is passed dynamically
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    //PrintWriter out = response.getWriter();

    String propertyType = "Unknown Property";
    String address = "Not Available";
    String details = "No description available.";
    double price = 0.0;
    boolean isBooked = false; // Flag to check if the property is booked
    boolean isOwnerBooking = false; // Flag to check if the user is the owner

    Integer loggedInUserId = (Integer) session.getAttribute("userId"); // Fetch the logged-in user's ID

    try {
        // Establish database connection
        con = ConnectionProvider.createCon();
        
        // Check if the property is already booked
        String bookingQuery = "SELECT COUNT(*) FROM bookings WHERE property_id=?"; // Adjust table name as necessary
        ps = con.prepareStatement(bookingQuery);
        ps.setString(1, propertyId);
        rs = ps.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            isBooked = true; // Property is already booked
        }

        // If not booked, fetch property details and owner's user ID
        if (!isBooked) {
            String query = "SELECT type, address, details, price, user_id FROM property_inquiries WHERE property_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, propertyId);
            rs = ps.executeQuery();

            if (rs.next()) {
                propertyType = rs.getString("type");
                address = rs.getString("address");
                details = rs.getString("details");
                price = rs.getDouble("price");

                // Check if the logged-in user is the owner of the property
                String propertyOwnerId = rs.getString("user_id");
                Integer propertyOwnerIdInt = Integer.parseInt(propertyOwnerId); // Parse to Integer
                System.out.println(propertyOwnerId);
                if (loggedInUserId.equals(propertyOwnerIdInt)) {
                    isOwnerBooking = true; // User is the owner of the property
                    System.out.println(isOwnerBooking);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    double bookingAmount = price * 0.10;
    DecimalFormat df = new DecimalFormat("#.##");

    // Fetch user session data
    String userName = (String) session.getAttribute("userName");
    String userEmail = (String) session.getAttribute("userEmail");
    String userPhone = (String) session.getAttribute("userPhone");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Property Booking</title>
    <link rel="stylesheet" href="patuli_booking.css">
</head>
<body>
<header>
    <h1>Book Your Dream Property</h1>
</header>

<div class="container">
    <div class="booking-details">
        <div class="apartment-info">
            <h2><%= propertyType %></h2>
            <p><strong>Address:</strong> <%= address %></p>
            <p><strong>Description:</strong> <%= details %></p>
            <p><strong>Price:</strong> ₹<%= price %></p>
        </div>
    </div>

    <% if (isBooked) { %>
        <h2 style="color: red;">This property is already booked.</h2>
    <% } else if (isOwnerBooking) { %>
        <h2 style="color: red;">You cannot book your own property.</h2>
    <% } else { %>
        <div class="booking-form">
            <h1>Book Now</h1>
            <form action="BookingServlet" method="post">
                <input type="hidden" id="propertyId" name="propertyId" value="<%= propertyId %>">
                <input type="hidden" id="propertyType" name="propertyType" value="<%= propertyType %>">
                <input type="hidden" id="propertyAddress" name="propertyAddress" value="<%= address %>">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="<%= userName != null ? userName : "" %>" 
                           placeholder="Enter your name" required>
                </div>
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" id="email" name="email" value="<%= userEmail != null ? userEmail : "" %>" 
                           placeholder="Enter your email" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="<%= userPhone != null ? userPhone : "" %>" 
                           placeholder="Enter your phone number" required>
                </div>
                <div class="form-group">
                    <label for="totalAmount">Total Property Amount (₹):</label>
                    <input type="number" id="totalAmount" name="totalAmount" value="<%= price %>" readonly>
                </div>
                <div class="form-group">
                    <label for="bookingAmount">Booking Amount (10%):</label>
                    <input type="text" id="bookingAmount" name="bookingAmount" value="<%= df.format(bookingAmount) %>" readonly>
                </div>
                <button type="submit" class="btn2">Proceed to Payment</button>
            </form>
        </div>
    <% } %>
</div>

<script>
    document.querySelector("form").addEventListener("submit", function(e) {
        const phone = document.getElementById("phone").value;

        if (phone.length < 10 || isNaN(phone)) {
            alert("Please enter a valid phone number.");
            e.preventDefault();
        }
    });
</script>
</body>
</html>
