package org;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.SQLException;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddAdminServlet")
public class AddAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String name = request.getParameter("adminName");
        String email = request.getParameter("adminEmail");
        String password = request.getParameter("adminPassword");

        Connection con = ConnectionProvider.createCon();
        try {

            // Insert query
            PreparedStatement pstm = con.prepareStatement("INSERT INTO admins (name, email, password) VALUES (?, ?, ?)");

            // Set parameters
            pstm.setString(1, name);
            pstm.setString(2, email);
            pstm.setString(3, password);

            // Execute update
            int rowsInserted = pstm.executeUpdate();

            if (rowsInserted > 0) {
            	request.setAttribute("successMsg", "Admin added successfully!");
                request.getRequestDispatcher("AddAdmin.jsp").forward(request, response);
            } else {
            	request.setAttribute("errorMsg", "Failed to add admin!");
            	request.getRequestDispatcher("AddAdmin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", e.getMessage());
        } 
}
}
