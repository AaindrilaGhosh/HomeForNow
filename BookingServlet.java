package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/BookingServlet")
//public class BookingServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//    	String propertyId = request.getParameter("propertyId");
//    	String propertyType = request.getParameter("propertyType");
//    	String propertyAddress = request.getParameter("propertyAddress");
//    	 String name = request.getParameter("name");
//         String email = request.getParameter("email");
//         String phone = request.getParameter("phone");
//		 String totalAmount = request.getParameter("totalAmount"); 
//		String bookingAmount = request.getParameter("bookingAmount");
//			 
//        
//
//         try {
//             // Use your existing ConnectionProvider class to get a connection
//             Connection con = ConnectionProvider.createCon();
//             String query = "INSERT INTO bookings (property_id, name, email, phone, total_amount, booking_amount) VALUES (?, ?, ?, ?, ?, ?)";
//             PreparedStatement pstmt = con.prepareStatement(query);
//             pstmt.setInt(1, Integer.parseInt(propertyId)); // Convert propertyId to integer
//             pstmt.setString(2, name);
//             pstmt.setString(3, email);
//             pstmt.setString(4, phone);
//             pstmt.setString(5, totalAmount);
//             pstmt.setString(6, bookingAmount);
//
//             int result = pstmt.executeUpdate();
//             if (result > 0) {
//                 // Redirect to payment page
//                 //response.sendRedirect("payment.jsp?email=" + email + "&amount=" + bookingAmount);
//            	 response.sendRedirect("payment.jsp?email=" + email + "&amount=" + bookingAmount +
//                         "&propertyType=" + propertyType + "&propertyAddress=" + propertyAddress + "&propertyId=" + propertyId);
//             } else {
//                 response.getWriter().write("Booking failed. Please try again.");
//             }
//
//         } catch (Exception e) {
//             e.printStackTrace();
//             response.getWriter().write("An error occurred: " + e.getMessage());
//         }
//     }
// }


@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String propertyId = request.getParameter("propertyId");
        String propertyType = request.getParameter("propertyType");
        String propertyAddress = request.getParameter("propertyAddress");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String totalAmount = request.getParameter("totalAmount");
        String bookingAmount = request.getParameter("bookingAmount");

        try {
            // Save booking data to a temporary table
            Connection con = ConnectionProvider.createCon();
            String query = "INSERT INTO pending_bookings (property_id, name, email, phone, total_amount, booking_amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(propertyId)); // Convert propertyId to integer
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, totalAmount);
            pstmt.setString(6, bookingAmount);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                // Redirect to payment page
                response.sendRedirect("payment.jsp?email=" + email + "&amount=" + bookingAmount +
                        "&propertyType=" + propertyType + "&propertyAddress=" + propertyAddress + "&propertyId=" + propertyId);
            } else {
                response.getWriter().write("Failed to initiate booking. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
