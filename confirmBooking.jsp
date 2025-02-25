<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirm Booking</title>
    <script type="text/javascript">
        window.onload = function() {
            // Automatically submit the form after the page loads
            document.getElementById("confirmForm").submit();
        }
    </script>
</head>
<body>
    <form id="confirmForm" action="ConfirmBookingServlet" method="POST">
        <input type="hidden" name="propertyId" value="${propertyId}">
        <input type="hidden" name="email" value="${email}">
        <input type="hidden" name="amount" value="${amount}">
    </form>
    <p>Please wait while we confirm your booking...</p>
</body>
</html>