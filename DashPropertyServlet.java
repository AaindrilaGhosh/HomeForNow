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

@WebServlet("/DashPropertyServlet")
public class DashPropertyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	Connection con = ConnectionProvider.createCon();
        try {
            String query = "SELECT * FROM property_inquiries";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        

            ArrayList<Property> properties = new ArrayList<>();
            while (rs.next()) {
            	
                Property property = new Property();
                property.setUserId(rs.getInt("user_id"));
                property.setEmail(rs.getString("email"));
//                property.setAddress(rs.getString("address"));
                property.setType(rs.getString("type"));
                property.setCarpetArea(rs.getString("carpet_area"));
//                property.setFurnishing(rs.getString("furnishing"));
                property.setPrice(rs.getString("price"));
//                property.setDetails(rs.getString("details"));
//                property.setRules(rs.getString("rules"));
//                property.setCancellation(rs.getString("cancellation"));
            
                properties.add(property);
            }
            
            request.setAttribute("properties", properties);
            System.out.println("Number of properties: " + properties.size());
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminDashboard.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}