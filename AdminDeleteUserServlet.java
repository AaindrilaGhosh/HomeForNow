package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.misc.ConnectionProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminDeleteUserServlet")
public class AdminDeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        try {
            Connection connection = ConnectionProvider.createCon();
            
            // Delete related property inquiries first
            String deleteInquiriesSql = "DELETE FROM property_inquiries WHERE user_id = ?";
            PreparedStatement deleteInquiriesStmt = connection.prepareStatement(deleteInquiriesSql);
            deleteInquiriesStmt.setInt(1, userId);
            deleteInquiriesStmt.executeUpdate();

            // Now delete the user
            String deleteUserSql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserSql);
            deleteUserStmt.setInt(1, userId);
            deleteUserStmt.executeUpdate();

            response.sendRedirect("FetchUserDetailsServlet?message=User deleted successfully."); // Redirect back to the user list
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
