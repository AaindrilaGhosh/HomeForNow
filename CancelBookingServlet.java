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

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String propertyId = request.getParameter("property_id");
        String email = request.getParameter("email");

        System.out.println("propertyId");
        System.out.println("email");
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionProvider.createCon();
            String sql = "DELETE FROM bookings WHERE property_id = ? AND email = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(propertyId));
            ps.setString(2, email);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("BookedProperties.jsp?cancel=success");
            } else {
                response.sendRedirect("BookedProperties.jsp?cancel=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("BookedProperties.jsp?cancel=error");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}