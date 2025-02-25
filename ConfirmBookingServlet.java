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

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String propertyId = request.getParameter("propertyId");
        String email = request.getParameter("email");
        String amount = request.getParameter("amount");

        try {
            Connection con = ConnectionProvider.createCon();
            
            // Move data from pending_bookings to bookings
            String moveQuery = "INSERT INTO bookings (property_id, name, email, phone, total_amount, booking_amount) SELECT property_id, name, email, phone, total_amount, booking_amount FROM pending_bookings WHERE property_id = ? AND email = ?";
            PreparedStatement moveStmt = con.prepareStatement(moveQuery);
            moveStmt.setInt(1, Integer.parseInt(propertyId));
            moveStmt.setString(2, email);

            int result = moveStmt.executeUpdate();
            if (result > 0) {
                // Remove the entry from pending_bookings
                String deleteQuery = "DELETE FROM pending_bookings WHERE property_id = ? AND email = ?";
                PreparedStatement deleteStmt = con.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, Integer.parseInt(propertyId));
                deleteStmt.setString(2, email);
                deleteStmt.executeUpdate();

                //response.getWriter().write("Booking confirmed!");
                response.sendRedirect("confirmation.jsp?email=" + email + "&amount=" + amount);
            } else {
                response.getWriter().write("Failed to confirm booking. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
