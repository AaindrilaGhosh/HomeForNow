package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.misc.ConnectionProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/SavePropertyServlet")

public class SavePropertyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		 
		 // Retrieve property details from the request
		    String imagePath = request.getParameter("imagePath");
	        String address = request.getParameter("address");
	        String type = request.getParameter("type");
	        Double price = Double.parseDouble(request.getParameter("price"));
	        boolean isSaved = false;
	        int savedItemId = -1; // To store the generated ID

	     // Validate that all parameters are provided
	        if (imagePath == null || address == null || type == null || price == null) {
	            System.err.println("Missing parameters in the request.");
	            response.sendRedirect("index.jsp?success=false");
	            return;
	        }
	        
	        try {
	        	System.out.println("imagePath: " + imagePath);
	            System.out.println("address: " + address);
	            System.out.println("type: " + type);
	            System.out.println("price: " + price);
	            
	            // Get database connection
	            Connection con = ConnectionProvider.createCon();

	            // SQL to insert data into saved_items table
	            String sql = "INSERT INTO saved_items (imagePath, address, type, price) VALUES (?, ?, ?, ?)";
	            PreparedStatement pst = con.prepareStatement(sql);
	            

	            pst.setString(1, imagePath);
	            pst.setString(2, address);
	            pst.setString(3, type);
	            pst.setDouble(4, price);
	           
	            int rowsAffected = pst.executeUpdate();
	            if (rowsAffected > 0) {
	                isSaved = true;
	                ResultSet generatedKeys = pst.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    savedItemId = generatedKeys.getInt(1); // Get the newly generated ID
	                }
	            }

	            pst.close();
	            con.close();
	        } catch (Exception e) {
	        	System.err.println("SQL Exception: " + e.getMessage());
	            e.printStackTrace();
	        }
	        //response.sendRedirect("index.jsp?success=" + isSaved);
	     // Redirect to the appropriate page based on the result
	        if (isSaved) {
//	            response.sendRedirect("index.jsp?success=true");
	        	response.sendRedirect("SavedItems.jsp?saved=" + isSaved + "&id=" + savedItemId);
	        } else {
	            response.sendRedirect("index.jsp?success=false");
	        }
	 }
}