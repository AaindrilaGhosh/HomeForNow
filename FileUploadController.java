package org.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.misc.ConnectionProvider;
//import org.misc.ImageConnectionProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
@WebServlet("/FileUploadController")
@MultipartConfig
public class FileUploadController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// Retrieve property_id from the request
    	String propertyIdStr = request.getParameter("property_id");
    	if (propertyIdStr == null || propertyIdStr.isEmpty()) {
    	    throw new ServletException("Missing propertyId in the request");
    	}
    	
    	int property_id = Integer.parseInt(propertyIdStr);
    	System.out.println("In do post method of FileUploadController" + property_id);
    	
    	// Retrieve the uploaded file
    	Part file=request.getPart("image");
    	if (file == null) {
            throw new ServletException("No file uploaded");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("postImage.jsp");
        }
    	
    	String imageFileName=file.getSubmittedFileName();  //get selected image file
    	System.out.println("Select image file name:"+ imageFileName);
    	
    	
    	
//    	String uploadPath="C:\\Rental_House\\HomeForNow\\src\\main\\webapp\\demo\\"+imageFileName;  //upload path where we hv to upload image
//    	String uploadPath = getServletContext().getRealPath("/") + "demo/";
//    	System.out.println("upload path:"+uploadPath);
    	
    	// Define the upload directory
//    	String uploadDir = "C:/Rental_House/UploadedImages/";
    	String uploadDir = getServletContext().getRealPath("/") + "UploadedImages/";
    	File directory = new File(uploadDir);
    	if (!directory.exists()) {
            directory.mkdirs();  // Create directories if they do not exist
        }
    	
    	// Generate unique file name
    	String uniqueFileName = System.currentTimeMillis() + "_" + imageFileName;
    	
    	// Define the full file path
        String uploadPath = uploadDir + uniqueFileName;
        System.out.println("File will be uploaded to: " + uploadPath);
        
     
    	
        String webPath = "UploadedImages/" + uniqueFileName;
    	// Save file path to the database
        Connection con = ConnectionProvider.createCon();
     // Save the file to the specified directory
    	try {
    		  file.write(uploadPath);
		      String query="Insert into uploaded_image(imageFileName, imagePath, property_id) values(?,?,?)"; //modified  
		      PreparedStatement stmt=con.prepareStatement(query);	      
		      stmt.setString(1,uniqueFileName);      
		      stmt.setString(2, webPath);
//		      stmt.setString(2, uploadPath);
		      stmt.setInt(3, property_id);
		      
		      int rows=stmt.executeUpdate();
		      
		      if(rows>0) {
		    	  request.setAttribute("successMsg", "Image uploaded successfully!");
		    	  System.out.println("Image successfully uploaded");
		      }
		      else
		      {
		    	  request.setAttribute("errorMsg", "Failed to uploade image");
		    	  System.out.println("Failed to uploade image");
		      }
		   }
		   catch(Exception e) {
			  e.printStackTrace();
		   }
    	
    	//response.sendRedirect("postImage.jsp?propertyId=" + property_id);
    	
    	// Forward the request to the JSP with the attributes
//    	request.setAttribute("propertyId", property_id);  // Set property ID
    	RequestDispatcher dispatcher = request.getRequestDispatcher("postImage.jsp?propertyId=" + property_id);
    	dispatcher.forward(request, response);
    }
    
}                         