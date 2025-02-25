package org;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.catalina.User;
//import org.model.bean.UserBean;
import org.misc.ConnectionProvider;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//HttpSession session = request.getSession();
    	//String userName = (String) session.getAttribute("userName");
    	//String userEmail = (String) session.getAttribute("userEmail");
    	Integer userId = Integer.parseInt(request.getParameter("userId"));
        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("pass");

        
        System.out.println("Id" + userId);
        System.out.println("newName" + newUsername);
        System.out.println("newEmail" + newEmail);
        System.out.println("newPass" + newPassword);
        
        // Update the database
        Connection con = ConnectionProvider.createCon();
        try{
        	// Check if the new username already exists
        	PreparedStatement pstm = con.prepareStatement("UPDATE users SET name = ?, email = ?, password = ? WHERE user_id = ?");
        	pstm.setString(1, newUsername);
        	pstm.setString(2, newEmail);
        	
        	
        	// If password is provided, hash it; otherwise, keep the existing one
            if (newPassword != null && !newPassword.isEmpty()) {
//                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                  pstm.setString(3, newPassword);
            } else {
                // Retain the current password by querying it
                PreparedStatement passwordStmt = con.prepareStatement("SELECT password FROM users WHERE id = ?");
                passwordStmt.setInt(1, userId);
                ResultSet rs = passwordStmt.executeQuery();
                if (rs.next()) {
                    pstm.setString(3, rs.getString("password"));
                }
            }
        	
            pstm.setInt(4, userId); // Set the user ID for the WHERE clause
            int rowsUpdated = pstm.executeUpdate();
        	
            if (rowsUpdated > 0) {
                // Update session attributes
            	request.getSession().setAttribute("userId", userId);
                request.getSession().setAttribute("userName", newUsername);
                request.getSession().setAttribute("userEmail", newEmail);
                request.getSession().setAttribute("userPass", newPassword);
                // Do not store the password in the session for security reasons

                // Redirect back to the profile page with a success message
                request.setAttribute("successMsg", "User Profile Updated Successfully!");
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
                //response.sendRedirect("Profile.jsp?success=1");
            } else {
                request.setAttribute("errorMessage", "Failed to update profile.");
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the profile.");
            request.getRequestDispatcher("Profile.jsp").forward(request, response);
        }

        //response.sendRedirect("Profile.jsp");
    }
}
