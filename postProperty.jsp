<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="jakarta.servlet.http.HttpSession" %>
    <%
    // Check if the user is logged in
    //HttpSession session = request.getSession(false); // Use false to avoid creating a new session
    String userEmail = (session != null) ? (String) session.getAttribute("userEmail") : null;

    if (userEmail == null) {
        // Redirect to login page if the user is not logged in
        response.sendRedirect("login.jsp");
        return; // Stop further processing of the page
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="postPropertyStyle.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <title>Document</title>
     
</head>
<body>


    <div class="form-container">
        <h2>Property Information Inquiry Form</h2>
        <form action="InquiryServlet" method="POST">
      

 <div class="form-group">
            <label for="email">Email:</label>
             <input type="email" id="email" name="email" required><br><br>
          </div>
          <div class="form-group">
           <!--  <label for="property_add">Property Address</label>
            <input type="property_add" id="property_add" name="property_add" value="${param.property_add}" required required> -->
            <label for="address">Property Address:</label>
    		<input type="text" id="address" name="address" required><br><br>
          </div>
           <div class="form-group">
           <!-- <label for="property_type">Property Type</label>
           <select id="property_type" name="property_type"  required> --> 
            <label for="type">Property Type:</label>
    		<select id="type" name="type" required>
           <option value=""></option>
    		<option value="Apartment">Apartment</option>
    		<option value="Flat">Flat</option>
    		<option value="House">House</option>
    		<option value="Penthouse">Penthouse</option>
    		
  		</select><br><br>
           
          </div>
           <div class="form-group">
            <label for="carpetArea">Carpet Area (sq.ft):</label>
    <input type="number" id="carpetArea" name="carpetArea" required><br><br>
          </div>
           <div class="form-group">
           <label for="furnishing">Furnishing Type:</label>
    <select id="furnishing" name="furnishing" required>
           <option value=""></option>
    		<option value="Fully-Furnished">Fully-Furnished</option>
    		<option value="Semi-Furnished">Semi-Furnished</option>
    		<option value="Unfurnished">Unfurnished</option>
    		
  		</select><br><br>
           
          </div>
          <div class="form-group">
            <label for="price">Sale Price:</label>
   			 <input type="number" id="price" name="price" required><br><br>
          </div>
    <div class="form-group">
            <!--  <label for="prop_details">Property Details</label>
            <input type="prop_details" id="prop_details" name="prop_details"  value="${param.prop_details}" required>-->
            
            <label for="details">Property Details:</label>
   			 <textarea id="details" name="details" rows="4" cols="50"></textarea><br><br>
          </div>
    
          <div class="form-group">
            <!-- <label for="rules">House Rules</label>
            <input type="rules" id="rules" name="rules" value="${param.rules}" required> -->
            
            <label for="rules">House Rules:</label>
   			 <textarea id="rules" name="rules" rows="4" cols="50"></textarea><br><br>
          </div>
          <div class="form-group">    
            <label for="cancellation">Cancellation Period (days):</label>
    <input type="number" id="cancellation" name="cancellation" required><br><br>
          </div>
          <input type="submit" value="Next" class="next-btn">
        </form>
      </div>
    </body>
    </html>