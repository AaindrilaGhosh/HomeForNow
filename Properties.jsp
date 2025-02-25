<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
	<%@ page import="org.Property" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="Properties.css">
</head>
<body>
    <h2>Property Details Table</h2>
    <div class="property-table">
               <table>
               <!-- Define column widths using colgroup -->
               <colgroup>
        <col style="width: 7%;">  <!-- UserID -->
        <col style="width: 10%;"> <!-- Email -->
        <col style="width: 10%;"> <!-- Address -->
        <col style="width: 6%;">  <!-- Type -->
        <col style="width: 7%;">  <!-- Carpet Area -->
        <col style="width: 10%;">  <!-- Furnishing -->
        <col style="width: 8%;"> <!-- Rent -->
        <col style="width: 20%;"> <!-- Details -->
        <col style="width: 20%;"> <!-- Rules -->
        <col style="width: 12%;"> <!-- Cancellation -->
        <col style="width: 10%;"> <!-- Image Link -->
        <col style="width: 10%;"> <!-- Actions -->
    </colgroup>
                  <tr>
                    <th>UserID</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Type</th>
                    <th>Carpet Area()</th>
                    <th>Furnishing</th>
                    <th>Rent</th>
                    <th>Details</th>
                    <th>Rules</th>
                    <th>Cancellation</th>
                    <th>Image</th>
                    <th>Actions</th>
                  </tr>
                  
				<%
                // Fetch the properties list from the request attribute
                ArrayList<Property> properties = (ArrayList<Property>) request.getAttribute("properties");

                if (properties != null && !properties.isEmpty()) {
                    for (Property property : properties) {
            	%>
                        <tr>
                            <td style="text-align: center;"><%= property.getUserId() %></td>
                            <td><%= property.getEmail() %></td>
                            <td><%= property.getAddress() %></td>
                            <td style="text-align: center;"><%= property.getType() %></td>
                            <td style="text-align: center;"><%= property.getCarpetArea() %></td>
                            <td style="text-align: center;"><%= property.getFurnishing() %></td>
                            <td style="text-align: center;"><%= property.getPrice() %></td>
                            <td>
                    <ul>
                        <%
                            ArrayList<String> detailLines = property.getDetailLines();
                            if (detailLines != null && !detailLines.isEmpty()) {
                                for (String line : detailLines) {
                        %>
                            <li><%= line %></li>
                        <%
                                }
                            } else {
                        %>
                            <li>No details available</li>
                        <%
                            }
                        %>
                    </ul>
                </td>
                     
                    <td><%= property.getRules() %></td>
                    <td style="text-align: center;"><%= property.getCancellation() %></td>
                    <td>
    					<%
                           ArrayList<String> imagePaths = property.getImagePaths();
                           if (imagePaths != null && !imagePaths.isEmpty()) {
                                for (String imagePath : imagePaths) {
                         %>
                                    <%-- a href="<%= imagePath %>" target="_blank">
                                        <img src="<%= imagePath %>" alt="Property Image" style="width: 100px; height: 75px; margin-right: 5px;" />
                                    </a> --%>
                                    
                            <img src="<%= imagePath %>" alt="Property Image" style="width: 100px; height: 75px; margin-right: 5px; cursor: pointer;" 
                             onclick="openModal('<%= imagePath %>')" />
                          <%
                                }
                             } else {
                           %>
                              No Images
                            <%
                              }
                             %>
					  </td>
					  <td style="text-align: center;">
        				<a href="AdminEditProperty.jsp?property_id=<%= property.getPropertyId() %>" class="btn">Edit</a>
        				<a href="AdminDeletePropertyServlet?property_id=<%= property.getPropertyId() %>" class="btn">Delete</a>
    				  </td>
            			</tr>
            <%
                    System.out.println("propertyid in properties" + property.getPropertyId());
                    }
                }
                else{
                	response.sendRedirect("PropertyServlet");
                
                }
                %>
               </table>
           </div>
           
           <!-- Modal for full-size image -->
    <div id="imageModal" class="modal">
        <span class="close" onclick="closeModal()">&times;</span>
        <img class="modal-content" id="modalImage">
    </div>

    <script>
        // Open the modal and set the image source
        function openModal(imagePath) {
            var modal = document.getElementById("imageModal");
            var modalImage = document.getElementById("modalImage");
            modalImage.src = imagePath;
            modal.style.display = "block";
        }

        // Close the modal
        function closeModal() {
            var modal = document.getElementById("imageModal");
            modal.style.display = "none";
        }
    </script>
</body>
</html>