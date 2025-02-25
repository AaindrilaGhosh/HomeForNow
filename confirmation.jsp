<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <link rel="stylesheet" href="confirmation.css">
</head>
<body>
    <div class="container">
        <h2>Booking Confirmation</h2>
        <div class="details">
        	<p>Thank you for your payment! Your property has been successfully booked.</p>
        	<div class="pre-details">
        		<p id="email" ><strong style="font-size: 20px;">Email:</strong> <%= request.getParameter("email") %></p>
        		<p id="amount"><strong style="font-size: 20px;">Amount:</strong> <%= request.getParameter("amount") %></p>
        	</div>
        	<p>We will send you a confirmation email shortly.</p>
        </div>
        <a href="index.jsp"><button>Go to Home</button></a>
    </div>
</body>
</html>
