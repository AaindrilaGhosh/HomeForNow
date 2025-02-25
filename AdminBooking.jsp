<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.AdminBookingServlet" %>
<%@ page import="java.util.List" %>
    <!DOCTYPE html>
<html>
<head>
    <title>Admin Booking</title>
    <link rel="stylesheet" href="AdminBooking.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<form action="AdminBookingServlet" method="post">
    <div class="upper-nav">
        <div class="admin-sec">
            <i class="fa-solid fa-user-plus fa-2x"></i>
            <a href="AddAdmin.jsp">Add Admin</a>
        </div>
    </div> 
    <div class="separator"></div>
    <div class="dash-cont">
        <div class="left-nav">
            <img src="Images/homefornow.png" id="logo">
            <div class="left-nav-items">
            	<a href="AdminDashboard.jsp">
                <i class="fa-solid fa-gauge"></i>
                <span>Dashboard</span>
                </a>
            </div>
            <div class="left-nav-items">
            	<a href="Properties.jsp">
                <i class="fa-solid fa-house"></i>
                <span>Properties</span>
                </a>
            </div>
            <div class="left-nav-items">
             	<a href="AdminBooking.jsp">
                <i class="fa-solid fa-gauge"></i>
                <span>Bookings</span>
                </a>
            </div>
            <div class="left-nav-items">
            	<a href="#">
                <i class="fa-solid fa-user"></i>
                <span>Users</span>
                </a>
            </div>
            <div class="left-nav-items">
            	<a href="AdminFeedbackServlet">
                <i class="fa-solid fa-flag"></i>
                <span>Feedbacks</span>
                </a>
            </div>
            <div class="left-nav-items">
            	<a href="logout">
                <i class="fa-solid fa-right-from-bracket"></i>
                <span>Log Out</span>
                </a>
            </div>
            </div>
        <div class="feedback">
    		<h1 class="feedback-head">Booking Page</h1>
    		<p>Click here to get all records</p>
    		<input type="submit" value="Get All Record" class="fetch-btn">
       </div>
    </div>
</form>
</body>
</html>