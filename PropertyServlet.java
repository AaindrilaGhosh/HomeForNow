package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.misc.ConnectionProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;

@WebServlet("/PropertyServlet")
public class PropertyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	Connection con = ConnectionProvider.createCon();
        try {
//            String query = "SELECT * FROM property_inquiries";
        	//String query = "SELECT p.*, ui.imagePath FROM property_inquiries p LEFT JOIN uploaded_image ui ON p.property_id = ui.property_id";
        	String query = "SELECT p.*, GROUP_CONCAT(ui.imagePath) AS imagePaths FROM property_inquiries p LEFT JOIN uploaded_image ui ON p.property_id = ui.property_id GROUP BY p.property_id";
        	PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        

            ArrayList<Property> properties = new ArrayList<>();
            while (rs.next()) {
            	
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id")); // Fetch property_id from the result set
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
                
            
                
                 // Handle images
                String imagePaths = rs.getString("imagePaths");
                if (imagePaths != null && !imagePaths.isEmpty()) {
                    ArrayList<String> imagePathList = new ArrayList<>();
                    for (String path : imagePaths.split(",")) {
                        imagePathList.add(path.trim());
                    }
                    property.setImagePaths(imagePathList);
                    property.setImagePath(imagePathList.get(0)); // Use the first image as a representative
                } else {
                    property.setImagePaths(new ArrayList<>());
                }
                
             // Handle detail lines
                String details = rs.getString("details");
                if (details != null && !details.isEmpty()) {
                    ArrayList<String> detailLines = new ArrayList<>();
                    for (String line : details.split("\n")) {
                        detailLines.add(line.trim());
                    }
                    property.setDetailLines(detailLines);
                } else {
                    property.setDetailLines(new ArrayList<>());
                }
                
                properties.add(property);
            }
            
            request.setAttribute("properties", properties);
            System.out.println("Number of properties: " + properties.size());
            RequestDispatcher dispatcher = request.getRequestDispatcher("Properties.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
