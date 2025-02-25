package org.controller;

//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.misc.ConnectionProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DisplayImageController")

public class DisplayImageController extends HttpServlet{
	private static final long serialVersionUID = 1L;



	/**

	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)

	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In do post method  display of FileUploadController");
		String id=request.getParameter("id");
		if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid ID");
            return;
        }
		
		int ID=Integer.parseInt(id);
//	    	int imgId=0;
	    	String imgFileName=null;
	    	String imgFilePath = null;
	    	Connection con = ConnectionProvider.createCon();
	    	try {
//			      String query="select * from uploaded_image";
	    		String query = "SELECT imageFileName, imagePath FROM uploaded_image WHERE id = ?";
	            PreparedStatement stmt = con.prepareStatement(query);
	            stmt.setInt(1, ID);
	            
			      ResultSet rs=stmt.executeQuery(query);
			      while(rs.next())
			      {
			    	  if(rs.getInt("id")==ID)
			    	  {
//			    		  imgId=rs.getInt("id");
//			    		  imgFileName=rs.getString("imageFileName");
			    		  imgFileName = rs.getString("imageFileName");
			              imgFilePath = rs.getString("imagePath");
			              System.out.println("Image found: " + imgFileName + ", Path: " + imgFilePath);
			    	  } else {
			    		  System.out.println("No image found for ID: " + ID);
			                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
			                return;
			    	  }
			      }
			      
			   }
			   catch(Exception e) {
				   e.printStackTrace();
			       response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
				  System.out.println(e);
			   }
	    	
	    	// Forward the image details to the JSP page
	        RequestDispatcher rd = request.getRequestDispatcher("DisplayImages.jsp");
	        request.setAttribute("imgFileName", imgFileName);
	        request.setAttribute("imgFilePath", imgFilePath);
//	    	RequestDispatcher rd;
//	    	request.setAttribute("ID", String.valueOf(imgId));
//	    	request.setAttribute("img", imgFileName);
//	    	rd=request.getRequestDispatcher("imageDisplay.jsp");.
//	    	rd=request.getRequestDispatcher("DisplayImages.jsp");
	    	rd.forward(request, response);
	    }
	
	}
