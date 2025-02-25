package org;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/EditPropertyServlet")
@MultipartConfig
public class EditPropertyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("deleteImage".equals(action)) {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            String imagePath = request.getParameter("imagePath");

            // Delete image from the database and file system
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
            response.sendRedirect("EditPropertyServlet?propertyId=" + propertyId);
            return;
        }

        // Fetch property details from the database
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        try (Connection con = ConnectionProvider.createCon()) {
            String propertyQuery = "SELECT * FROM property_inquiries WHERE property_id = ?";
            PreparedStatement ps = con.prepareStatement(propertyQuery);
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();

            Property property = new Property();
            if (rs.next()) {
                property.setPropertyId(rs.getInt("property_id"));
                property.setAddress(rs.getString("address"));
                property.setType(rs.getString("type"));
                property.setCarpetArea(rs.getString("carpet_area"));
                property.setFurnishing(rs.getString("furnishing"));
                property.setPrice(rs.getString("price"));
                // Set other property fields as needed
            }

            // Load image paths separately
            List<String> imagePaths = new ArrayList<>();
            String imageQuery = "SELECT imagePath FROM uploaded_image WHERE property_id = ?";
            PreparedStatement imageStmt = con.prepareStatement(imageQuery);
            imageStmt.setInt(1, propertyId);
            ResultSet imageRs = imageStmt.executeQuery();
            while (imageRs.next()) {
                imagePaths.add(imageRs.getString("imagePath"));
            }

            request.setAttribute("property", property);
            request.setAttribute("imagePaths", imagePaths); // Pass image paths to the JSP
            request.getRequestDispatcher("EditProperty.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle form submission
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String carpetArea = request.getParameter("carpetArea");
        String furnishing = request.getParameter("furnishing");
        String price = request.getParameter("price");

        try (Connection con = ConnectionProvider.createCon()) {
            // Update property details in the database
            String updateQuery = "UPDATE property_inquiries SET address = ?, type = ?, carpet_area = ?, furnishing = ?, price = ? WHERE property_id = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1, address);
            ps.setString(2, type);
            ps.setString(3, carpetArea);
            ps.setString(4, furnishing);
            ps.setString(5, price);
            ps.setInt(6, propertyId);
            ps.executeUpdate();

            // Handle image uploads
            for (Part part : request.getParts()) {
                if (part.getName().equals("images") && part.getSize() > 0) {
                    // Generate unique file name
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

                    // Define the upload directory
                    String uploadDir = getServletContext().getRealPath("/") + "UploadedImages/";
                    File directory = new File(uploadDir);
                    if (!directory.exists()) {
                        directory.mkdirs();  // Create directories if they do not exist
                    }

                    // Save the image
                    part.write(uploadDir + uniqueFileName);

                    // Insert image path into the database
                    String insertImageQuery = "INSERT INTO uploaded_image (imageFileName, imagePath, property_id) VALUES (?, ?, ?)";
                    PreparedStatement imagePs = con.prepareStatement(insertImageQuery);
                    imagePs.setString(1, uniqueFileName);
                    imagePs.setString(2, "UploadedImages/" + uniqueFileName); // Adjusted path to match the database
                    imagePs.setInt(3, propertyId);
                    imagePs.executeUpdate();
                }
            }

            response.sendRedirect("EditPropertyServlet?propertyId=" + propertyId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
 
}

