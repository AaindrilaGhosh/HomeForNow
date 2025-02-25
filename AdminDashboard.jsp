<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
	<%@ page import="org.Property" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="AdminDashboardStyle.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
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
            	<a href="Users.jsp">
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
        <div class="main-content">
            <h1 id="dash-head">Dashboard</h1>            
           <div class="property-table">
               <h3>PROPERTIES</h3>
               <a href="Properties.jsp">See More...</a>
               <table>
               <!-- Define column widths using colgroup -->
               <colgroup>
        <col style="width: 10%;"> <!-- UserID -->
        <col style="width: 13%;"> <!-- Email -->
        <col style="width: 10%;"> <!-- Type -->
        <col style="width: 10%;"> <!-- Carpet Area -->
        <col style="width: 10%;"> <!-- Rent -->
    </colgroup>
                  <tr>
                    <th>UserID</th>
                    <th>Email</th>
                    <th>Type</th>
                    <th>Carpet Area(Sq.ft.)</th>
                    <th>Rent</th>
                  </tr>
                  
				<%
                // Fetch the properties list from the request attribute
                ArrayList<Property> properties = (ArrayList<Property>) request.getAttribute("properties");

                if (properties != null && !properties.isEmpty()) {
                    for (Property property : properties) {
            	%>
                        <tr>
                            <td><%= property.getUserId() %></td>
                            <td style="width: 12%;"><%= property.getEmail() %></td>
                            <!-- <td><%= property.getAddress() %></td>  -->
                            <td><%= property.getType() %></td>
                            <td><%= property.getCarpetArea() %></td>
                            <!-- <td><%= property.getFurnishing() %></td>  -->
                            <td><%= property.getPrice() %></td> 
                            <!--  <td><%= property.getDetails() %></td>
                            <td><%= property.getRules() %></td>
                            <td><%= property.getCancellation() %></td>  -->
                        </tr>
            <%
                    }
                } else {
                        response.sendRedirect("DashPropertyServlet");
                        return;
                    }
                %>
               </table>
           </div>
        </div>
    </div>
</body>
</html>