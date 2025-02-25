package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MyPostsServlet")
public class MyPostsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        System.out.println("userEmail in MyPostsServlet: " + userEmail);

        if (userEmail == null) {
            request.setAttribute("errorMsg", "You must be logged in to view your posts.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<Property> properties = new ArrayList<>();

        
        Connection con = ConnectionProvider.createCon();
        try {
            String query = "SELECT pi.property_id, pi.user_id, pi.email, pi.address, pi.type, pi.carpet_area, " +
                           "pi.furnishing, pi.price, pi.details, pi.rules, pi.cancellation " +
                           "FROM property_inquiries pi WHERE pi.email = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
            	
                System.out.println("No properties found for email: " + userEmail);
            } else {
                while (rs.next()) {
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

                    // Fetch images for the property
                    String imageQuery = "SELECT imagePath FROM uploaded_image WHERE property_id = ? LIMIT 1"; // Limit to 1 image
                    PreparedStatement imagePs = con.prepareStatement(imageQuery);
                    imagePs.setInt(1, property.getPropertyId());
                    ResultSet imageRs = imagePs.executeQuery();
                    
                    if (imageRs.next()) {
                        property.setImagePath(imageRs.getString("imagePath")); // Change to single image path
                    } else {
                        property.setImagePath(null); // Handle case with no images
                    }

                    properties.add(property);
                    System.out.println("Added property ID: " + property.getPropertyId() + ", Images: " + property.getImagePath());

                    imageRs.close();
                    imagePs.close();
                }
                System.out.println("Fetched Properties: " + properties);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            request.setAttribute("errorMsg", "Error retrieving your posts. Please try again later.");
//            System.err.println("Error while retrieving properties: " + e.getMessage());
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//            return;
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        
        if (properties.isEmpty()) {
            request.setAttribute("noPostsMsg", "You haven't posted anythong yet.");
        } else {
            request.setAttribute("properties", properties);
        }

        //request.setAttribute("properties", properties);
        request.getRequestDispatcher("MyPosts.jsp").forward(request, response);
    }
}
