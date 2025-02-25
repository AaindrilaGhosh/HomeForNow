package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/InquiryServlet")
public class InquiryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	response.setContentType("text/html");

    	//Check for existing session
    	HttpSession session = request.getSession(false); // Don't create a new session
    	if (session == null) {
    	    // Session doesn't exist, user is likely logged out
    	    response.getWriter().println("<h3>Error: The session has expired.Please log in again.</h3>");
    	    response.getWriter().println("<a href='Register.jsp'>Login/Register</a>");
    	    return;
    	}

    	Integer user_id = (Integer) request.getSession().getAttribute("userId");
    	String sessionEmail = (String) session.getAttribute("userEmail"); // Assuming you have stored the email in session
    	System.out.println("userId in Inquiry: "+ user_id);
    	
    	if (user_id == null) {
            response.getWriter().println("<h3>Error: User ID is not set. Please log in again.</h3>");
            response.getWriter().println("<a href='Register.jsp'>Login/Register</a>");
            return; // Stop processing
        }
    	
    	String email = request.getParameter("email");
        if (sessionEmail == null || !sessionEmail.equals(email)) {
            response.getWriter().println("<h3>Error: The email you submitted does not match your account email.</h3>");
            response.getWriter().println("<a href='Register.jsp'>Login/Register</a>");
            return;
        }

        
        //String email = request.getParameter("email");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String carpetArea = request.getParameter("carpetArea");
        String furnishing = request.getParameter("furnishing").trim();
        String price = request.getParameter("price");
        String details = request.getParameter("details");
        String rules = request.getParameter("rules");
        String cancellation = request.getParameter("cancellation");

        System.out.println("Furnishing value: " + furnishing);
        
        try {
            Connection con = ConnectionProvider.createCon();
            String query = "INSERT INTO property_inquiries (user_id, email, address, type, carpet_area, furnishing, price, details, rules, cancellation) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
         // Create the PreparedStatement with RETURN_GENERATED_KEYS
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, user_id); // Set user_id
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, type);
            ps.setString(5, carpetArea);
            ps.setString(6, furnishing);
            ps.setString(7, price);
            ps.setString(8, details);
            ps.setString(9, rules);
            ps.setString(10, cancellation);

            int i = ps.executeUpdate();
            if (i > 0) {
            	ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int propertyId = rs.getInt(1); // Assuming property_id is auto-incremented
                    System.out.println("Generated propertyId: " + propertyId);
                    // Pass the propertyId to postImage.jsp
                    response.sendRedirect("postImage.jsp?propertyId=" + propertyId);
                 // Redirect to the image posting page after successful insertion.
//                response.sendRedirect("postImage.jsp");
            } else {
                response.getWriter().println("<h3>Error in submitting the inquiry. Please try again.</h3>");
            }
           }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}