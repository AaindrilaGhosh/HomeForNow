<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="header.jsp"/>
     <%
    String successMsg = (String) request.getAttribute("successMsg");
    String errorMsg = (String) request.getAttribute("errorMsg");
    
    System.out.println("successMsg" + successMsg);
    System.out.println("errorMsg" + errorMsg);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="postImage.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script>
        // Preview the image before uploading
        function previewImage(event) {
            const file = event.target.files[0];
            if (file) {
                const preview = document.getElementById('image-preview');
                preview.src = URL.createObjectURL(file);
                preview.style.display = 'block';
            }
        }
    </script>
    <title>Upload Images</title>
</head>
<body>
   <div class="form-container">
            <h1>Upload Your Photo</h1>

     
            <%
            // Retrieve propertyId from the query string
            String propertyId = request.getParameter("propertyId");

            System.out.println("propertyId in postImage:" + propertyId);
            // Display error message if propertyId is missing or invalid
            if (propertyId == null || propertyId.isEmpty()) {
            	System.out.println("Missing or invalid propertyId.");
            	//propertyId = request.getParameter("propertyId");
            	//System.out.println("propertyId after upload:" + propertyId);
        } else { %>

			<!-- Display the form if propertyId is valid -->
            <form id="upload-form" action="FileUploadController" method="post" enctype="multipart/form-data">
            <%System.out.println("PropertyId in postImage: " + propertyId); %>
            <input type="hidden" name="property_id" value="<%= propertyId %>">
        
                <label for="photo">Choose a photo:-</label>
                <input type="file" id="photo" name="image" accept="image/*" class="file" onchange="previewImage(event)">  <!-- modified -->
                
                <div id="image-preview-container">
                    <img id="image-preview" src="" alt="Image Preview" style="display:none;">
                </div>
                <button type="submit" id="upload-btn">Upload Photo</button>
            </form>
            <!-- Replace the done button link with this button -->
           <button type="button" class="done-button" onclick="doneAddingImages()">Post Property</button>
            <% } %>
        </div>
        
        <script>
    function doneAddingImages() {
        // Display a pop-up message
        alert("You have finished adding images!");

        // Redirect to index.jsp after the alert is closed
        window.location.href = "index.jsp";
    }

    // Existing script to handle error/success messages
    <% if (errorMsg != null && !errorMsg.isEmpty()) { %>
        alert('<%= errorMsg %>');
    <% } else if (successMsg != null && !successMsg.isEmpty()) { %>
        alert('<%= successMsg %>');
        setTimeout(function() {
            window.location.href = "postImage.jsp?propertyId=<%= propertyId %>"; // Redirects after 2 seconds
        }, 0);
    <% } %>
</script>
</body>
</html>       
<jsp:include page="footer.jsp"/>