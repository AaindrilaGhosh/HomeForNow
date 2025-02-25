package org;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.misc.ConnectionProvider;

@WebServlet("/AdminDeletePropertyServlet")
public class AdminDeletePropertyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String propertyIdStr = request.getParameter("property_id");

        // Check if property_id is provided
        if (propertyIdStr == null || propertyIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing property ID");
            return;
        }

        int propertyId;
        try {
            propertyId = Integer.parseInt(propertyIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid property ID");
            return;
        }

        try (Connection con = ConnectionProvider.createCon()) {
            // Step 1: Fetch associated images to delete them from the file system
            String fetchImagesQuery = "SELECT imagePath FROM uploaded_image WHERE property_id = ?";
            PreparedStatement fetchImagesStmt = con.prepareStatement(fetchImagesQuery);
            fetchImagesStmt.setInt(1, propertyId);
            ResultSet rs = fetchImagesStmt.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("imagePath");
                File file = new File(getServletContext().getRealPath("/") + imagePath);
                if (file.exists()) {
                    file.delete();
                }
            }

            // Step 2: Delete images from the database
            String deleteImagesQuery = "DELETE FROM uploaded_image WHERE property_id = ?";
            PreparedStatement deleteImagesStmt = con.prepareStatement(deleteImagesQuery);
            deleteImagesStmt.setInt(1, propertyId);
            deleteImagesStmt.executeUpdate();

            // Step 3: Delete the property itself
            String deletePropertyQuery = "DELETE FROM property_inquiries WHERE property_id = ?";
            PreparedStatement deletePropertyStmt = con.prepareStatement(deletePropertyQuery);
            deletePropertyStmt.setInt(1, propertyId);
            int rowsDeleted = deletePropertyStmt.executeUpdate();

            if (rowsDeleted > 0) {
                response.sendRedirect("PropertyServlet?message=propertyDeleted");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Property not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }
}