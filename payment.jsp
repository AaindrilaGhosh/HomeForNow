<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String errorMsg = (String) request.getParameter("error"); %>
  <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="payment.css">
    <script>
        // Function to format card number as 'XXXX XXXX XXXX XXXX'
        function formatCardNumber(input) {
            const value = input.value.replace(/\D/g, ''); // Remove non-digit characters
            const formattedValue = value.match(/.{1,4}/g)?.join(' ') || ''; // Group digits in sets of 4
            input.value = formattedValue;
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Confirm Your Payment</h2>
         <form action="PaymentServlet" method="post">
         <!-- Email Field -->
            <!-- Hidden Email Field -->
            <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
            
            <!-- Hidden Amount Field -->
            <input type="hidden" name="amount" value="<%= request.getParameter("amount") %>">
            <input type="hidden" name="propertyType" value="<%= request.getParameter("propertyType") %>">
            <input type="hidden" name="propertyAddress" value="<%= request.getParameter("propertyAddress") %>">
            <input type="hidden" name="propertyId" value="<%= request.getParameter("propertyId") %>">
            
        <div class="card-num">
            <h4>Card Number</h4>
              <input type="text" placeholder="Enter card number"  name="cardNumber" oninput="formatCardNumber(this)"required maxlength="19" pattern="\d{4} \d{4} \d{4} \d{4}"  class="card-num-box">
            
        </div>
        <div class="exp-month">
            <h4>Exp Month</h4>
<!--             <input type="text" placeholder="Enter month" name="expMonth" required maxlength="2" pattern="\d{2}"  class="exp-month-box"> -->
            <select id="expMonth" name="expMonth"  class="exp-month-box" required>
                <option value="" disabled selected>Select Month</option>
                <% for (int i = 1; i <= 12; i++) { %>
                    <option value="<%= String.format("%02d", i) %>"><%= String.format("%02d", i) %></option>
                <% } %>
            </select>
            
        </div>
        <div class="exp-year">
            <h4>Exp Year</h4>
                <select id="exp-year"  name="expYear"  required maxlength="4" pattern="\d{4}" class="exp-year-box">
                  <option value="" disabled selected>Select Year</option>
                  
                  <% 
        // Generate options dynamically for the next 10 years
        int currentYear = java.time.Year.now().getValue();
        for (int i = 0; i < 10; i++) {
            int year = currentYear + i;
    %>
        <option value="<%= year %>"><%= year %></option>
    <% 
        } 
    %>
                </select>
        </div>
        <div class="cvv">
            <h4>CVV</h4>
            <input type="number" placeholder="CVV" name="cvv" required maxlength="3" pattern="\d{3}" class="cvv-box">
        </div>
            
        <div class="accept-card">
            <h4>Accepted Cards</h4> 
        </div>
            
            <div class="card">
              <img src="Images/master_card.png" alt="MasterCard" class="master_card">
          <img src="Images/paypal.png" alt="PayPal" class="paypal_card">
            <img src="Images/visa.png" alt="Visa" class="visa_card">
        </div>
       <button class="pay" >Pay</button>
       </form>
    </div>
    <% if(errorMsg != null) {%>
    <script>
       alert('<%= errorMsg %>');
    </script>
    <%} %>
</body>
</html>