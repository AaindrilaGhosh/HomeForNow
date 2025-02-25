package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.misc.ConnectionProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
	
	 private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	    	response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        String name = request.getParameter("name");
	        String email = request.getParameter("email");   
	        String rating = request.getParameter("rating");  
	        String comments = request.getParameter("comments");

	        try {
	            Connection con = ConnectionProvider.createCon();
	            String query = "INSERT INTO feedback (name,email, rating, comments) VALUES (?, ?, ?, ?)";
	            PreparedStatement ps = con.prepareStatement(query);

	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, rating);
	            ps.setString(4, comments);
	           
	            int i = ps.executeUpdate();
	            if (i > 0) {
	                // Redirect to the image posting page after successful insertion.
	            	request.setAttribute("successMsg", "Thank you for your feedback!");
	            	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
	                dispatcher.forward(request, response);
	            	//response.sendRedirect("index.jsp");
	            } else {
	                response.getWriter().println("<h3>Error in submitting the inquiry. Please try again.</h3>");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
	        }
	    }
	}