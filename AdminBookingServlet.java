package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminBookingServlet")
public class AdminBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            Connection con = ConnectionProvider.createCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings");
            ResultSet rs = ps.executeQuery();

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1 style='text-align:center;margin-top:20px;'>BOOKING PROPERTY LIST</h1>");
            out.println("<table style='border: 1px solid black; border-collapse: collapse; width: 90%; margin: auto;'>");
            out.println("<tr style='background-color:#D8BFD8; text-align:center; height:40px; font-size: 18px;'>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Booking ID</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Property ID</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Name</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Email</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Phone</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Total Amount</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Booking Amount</th>"
                    + "<th style='border: 1px solid black; padding: 10px;'>Booking Date</th>"
                    + "</tr>");

            while (rs.next()) {
                out.println("<tr style='text-align:center;'>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getInt("booking_id") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getInt("property_id") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getString("name") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getString("email") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getString("phone") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getDouble("total_amount") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getDouble("booking_amount") + "</td>"
                        + "<td style='border: 1px solid black; padding: 8px;'>" + rs.getString("booking_date") + "</td>"
                        + "</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}