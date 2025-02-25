<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.misc.ConnectionProvider" %>
<%@ page import="java.util.*" %>
<jsp:include page="header.jsp"/>
<%
	//HttpSession session = request.getSession(false);
	if (session == null || session.getAttribute("user") == null) {
    	response.sendRedirect("login.jsp");
    	return;
	}
    // Get database connection
    Connection con = ConnectionProvider.createCon();
    String query = "SELECT * FROM saved_items";
    PreparedStatement pst = con.prepareStatement(query);
    ResultSet rs = pst.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Saved Items</title>
</head>
<body>
    <h1>Saved Properties</h1>
    <c:choose>
        <% if (!rs.isBeforeFirst()) { %>
            <p>No properties saved yet.</p>
        <% } else { %>
            <div>
                <% while (rs.next()) { %>
                   <div style="border: 1px solid #ddd; margin: 10px; padding: 10px;box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
                        <img src="<%= rs.getString("imagePath") %>" alt="Property Image" width="200">
                        <p >Address: <%= rs.getString("address") %></p>
                        <p >Type: <%= rs.getString("type") %></p>
                        <p >Price: ₹<%= rs.getDouble("price") %></p>
                        
                        <!-- Delete Button -->
                    <form action="DeleteSavedPropertyServlet" method="post" style="margin-left: 1200px;">
                        <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                        <button style="cursor:pointer;button:hover;background-color:purple;padding:10px;color:white;border-radius:10px;font-size:15px;" type="submit" onclick="return confirm('Are you sure you want to delete this property?');">
                            Delete
                        </button>
                    </form>
                    </div>
                <% } %>
            </div>
        <% } 
        %>
    </c:choose>
    <% rs.close(); pst.close(); con.close(); %>
</body>
</html>
<jsp:include page="footer.jsp"/>