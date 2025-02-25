<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String successMsg = (String) request.getAttribute("successMsg");
      System.out.println("asshhdhhd"+successMsg);%>
    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Form</title>
    <link rel="stylesheet" href="feedback.css">
</head>
<body>
    <% if(successMsg != null) { %>
    <script>
        alert("<%= successMsg %>"); // Make sure to use quotes around the message
    </script>
    <% } %>
    <div class="form-container">
        <h2>Feedback Form</h2>
        <form action="FeedbackServlet" method="POST">
            <label for="name">Username:</label>
            <input type="text" id="name" name="name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="rating">Rating:</label>
            <select id="rating" name="rating" required>
                <option value="1">1 - Very Poor</option>
                <option value="2">2 - Poor</option>
                <option value="3">3 - Average</option>
                <option value="4">4 - Good</option>
                <option value="5">5 - Excellent</option>
            </select>

            <label for="comments">Comments:</label>
            <textarea id="comments" name="comments" rows="4" required></textarea>

            <input type="submit" value="Submit Feedback">
        </form>
    </div>

</body>
</html>