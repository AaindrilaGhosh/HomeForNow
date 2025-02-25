<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    // Retrieve the email from the request parameter passed in the URL
    String email = request.getParameter("email");
    String error = request.getParameter("message"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="OtpPageStyle.css">
<script>
        // Automatically move to the next input field when the current input is filled
        function moveToNext(current, next) {
            if (current.value.length == current.maxLength) {
                next.focus();
            }
        }

        // Validate OTP inputs before submitting the form
        function validateOTP() {
            const inputs = document.querySelectorAll('.otp-input');
            let otp = '';
            inputs.forEach(input => otp += input.value);
            if (otp.length === 6) {
                return true; // Form is ready to submit
            } else {
                alert('Please enter a valid 6-digit OTP');
                return false;
            }
        }
    </script>
</head>
<body>
<div class="otp-container">
    <h1>Verify Your OTP</h1>
    <p>Please enter the 6-digit code sent to your email.</p>
    <form id="otp-form" action="OtpValidateServlet" method="post">
      <div class="otp-inputs">
      <!-- Hidden input to store the email -->
        <input type="hidden" name="email" value="<%= email %>">
         <input type="text" class="otp-input" maxlength="1" required id="otp1" name="otp1" oninput="moveToNext(this, document.getElementById('otp2'))">
         <input type="text" class="otp-input" maxlength="1" required id="otp2" name="otp2" oninput="moveToNext(this, document.getElementById('otp3'))">
         <input type="text" class="otp-input" maxlength="1" required id="otp3" name="otp3" oninput="moveToNext(this, document.getElementById('otp4'))">
         <input type="text" class="otp-input" maxlength="1" required id="otp4" name="otp4" oninput="moveToNext(this, document.getElementById('otp5'))">
         <input type="text" class="otp-input" maxlength="1" required id="otp5" name="otp5" oninput="moveToNext(this, document.getElementById('otp6'))">
         <input type="text" class="otp-input" maxlength="1" required id="otp6" name="otp6">
      </div>
      <button type="submit" class="submit-btn">Submit</button>
    </form>
  </div>
</body>
</html>