package org;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.misc.ConnectionProvider;

@WebServlet("/AdminEditPropertyServlet")
@MultipartConfig
public class AdminEditPropertyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String propertyIdStr = request.getParameter("property_id");
        System.out.println("First IDStr in servlet: " + propertyIdStr);
        String action = request.getParameter("action");
        
        if (action != null && action.equals("deleteImage")) {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            String imagePath = request.getParameter("imagePath");

            try (Connection con = ConnectionProvider.createCon()) {
                // Delete image from database
                String deleteQuery = "DELETE FROM uploaded_image WHERE property_id = ? AND imagePath = ?";
                PreparedStatement ps = con.prepareStatement(deleteQuery);
                ps.setInt(1, propertyId);
                ps.setString(2, imagePath);
                ps.executeUpdate();

                // Delete file from the file system
                File file = new File(getServletContext().getRealPath("/") + imagePath);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Redirect back to the edit page
            response.sendRedirect("AdminEditPropertyServlet?propertyId=" + propertyId);
            return;
        }
        
        
        if (propertyIdStr == null || propertyIdStr.isEmpty()) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing propertyId");
        	request.setAttribute("error", "Missing Property ID");
            request.getRequestDispatcher("AdminEditProperty.jsp").forward(request, response);
            return;
//            return;
        }

        int propertyId = Integer.parseInt(propertyIdStr);
        try (Connection con = ConnectionProvider.createCon()) {
            // Fetch property details
            String query = "SELECT * FROM property_inquiries WHERE property_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, propertyId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setUserId(rs.getInt("user_id"));
                property.setEmail(rs.getString("email"));
                property.setAddress(rs.getString("address"));
                property.setType(rs.getString("type"));
                property.setCarpetArea(rs.getString("carpet_area"));
                property.setFurnishing(rs.getString("furnishing"));
                property.setPrice(rs.getString("price"));
                property.setDetails(rs.getString("details"));
                property.setRules(rs.getString("rules"));
                property.setCancellation(rs.getString("cancellation"));

                request.setAttribute("property", property);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Property not found");
                return;
            }

            // Fetch associated images
            String imageQuery = "SELECT imagePath FROM uploaded_image WHERE property_id = ?";
            PreparedStatement imagePs = con.prepareStatement(imageQuery);
            imagePs.setInt(1, propertyId);
            ResultSet imageRs = imagePs.executeQuery();

            List<String> imagePaths = new ArrayList<>();
            while (imageRs.next()) {
                imagePaths.add(imageRs.getString("imagePath"));
            }
            request.setAttribute("imagePaths", imagePaths);

         // Set property_id attribute
            request.setAttribute("property_id", propertyIdStr);
            
            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminEditProperty.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String propertyIdStr = request.getParameter("property_id");
    	System.out.println("IDStr in servlet: " + propertyIdStr);
    	
    	if (propertyIdStr == null || propertyIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing propertyId");
            return;
    	}
            
        try {
            // Parse form inputs
        	int propertyId = Integer.parseInt(propertyIdStr);
        	System.out.println("ID in servlet: " + request.getParameter("propertyId"));
            String address = request.getParameter("address");
            String type = request.getParameter("type");
            String carpetArea = request.getParameter("carpetArea");
            String furnishing = request.getParameter("furnishing");
            String price = request.getParameter("price");
            String ownerEmail = request.getParameter("ownerEmail");

            // Update property details
            String updateQuery = "UPDATE property_inquiries SET address = ?, type = ?, carpet_area = ?, furnishing = ?, price = ?, email = ? WHERE property_id = ?";
            
            try (Connection con = ConnectionProvider.createCon(); 
                    PreparedStatement ps = con.prepareStatement(updateQuery)) {
                   ps.setString(1, address);
                   ps.setString(2, type);
                   ps.setString(3, carpetArea);
                   ps.setString(4, furnishing);
                   ps.setString(5, price);
                   ps.setString(6, ownerEmail);
                   ps.setInt(7, propertyId);
                   ps.executeUpdate();
               }
            
            // Redirect to admin property list or confirmation page
//            response.sendRedirect("PropertyServlet?message=updateSuccess");
            
            response.sendRedirect("PropertyServlet");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("PropertyServlet?error=invalidPropertyId");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PropertyServlet?error=serverError");
        }
    }
}	