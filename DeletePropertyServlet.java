package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeletePropertyServlet")
public class DeletePropertyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get propertyId from the request
        String propertyIdStr = request.getParameter("propertyId");
        if (propertyIdStr == null || propertyIdStr.isEmpty()) {
            response.sendRedirect("MyPosts.jsp?errorMsg=Invalid property ID.");
            return;
        }

        int propertyId = Integer.parseInt(propertyIdStr);

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            // Establish a database connection
            con = ConnectionProvider.createCon();

            // Delete all associated images from the database and filesystem
            String selectImagesQuery = "SELECT imagePath FROM uploaded_image WHERE property_id = ?";
            stmt = con.prepareStatement(selectImagesQuery);
            stmt.setInt(1, propertyId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("imagePath");

                // Remove the file from the filesystem
                String absolutePath = getServletContext().getRealPath("/") + imagePath;
                java.io.File imageFile = new java.io.File(absolutePath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }

            // Delete images from the database
            String deleteImagesQuery = "DELETE FROM uploaded_image WHERE property_id = ?";
            stmt = con.prepareStatement(deleteImagesQuery);
            stmt.setInt(1, propertyId);
            stmt.executeUpdate();

            // Delete the property from the database
            String deletePropertyQuery = "DELETE FROM property_inquiries WHERE property_id = ?";
            stmt = con.prepareStatement(deletePropertyQuery);
            stmt.setInt(1, propertyId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
            	request.setAttribute("successMsg", "Property deleted successfully.");
            	request.getRequestDispatcher("MyPostsServlet").forward(request, response);
                //response.sendRedirect("MyPostsServlet?successMsg=");
            } else {
                //response.sendRedirect("MyPosts.jsp?errorMsg=Failed to delete property.");
            	request.setAttribute("errorMsg", "Failed to delete property.");
            	request.getRequestDispatcher("MyPostsServlet").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("MyPosts.jsp?errorMsg=An error occurred while deleting the property.");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}