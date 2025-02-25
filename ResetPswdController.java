package org.controller;

//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import org.model.dao.PswdResetDao;
//
///**
// * Servlet implementation class ResetPswdServlet
// */
//@WebServlet("/ResetPswdServlet")
//public class ResetPswdServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public ResetPswdServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
////		// Get the token from the request
////        String token = request.getParameter("token");
////        
////     // Check if the token is valid (e.g., compare with a stored token in the database)
////        if (token == null || token.isEmpty()) {
////            response.sendRedirect("ForgotPswd.jsp");
////            return;
////        }
////
////        // If the token is valid, forward the request to the reset password page
////        RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPswd2.jsp");
////        dispatcher.forward(request, response);
//     }
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//		
//		// Get the token and new password from the form
//        String token = request.getParameter("token");
//        String newPassword = request.getParameter("new-pswd");
//        String confirmPassword = request.getParameter("conf-pswd");
//
//        // Validate the token and passwords
//        PswdResetDao dao = new PswdResetDao();
//        
//        if (!dao.isTokenValid(token)) {
//            request.setAttribute("errorMessage", "Invalid or expired token.");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
//            //dispatcher.forward(request, response);
//            return;
//        }
//
////        if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
////            request.setAttribute("errorMessage", "Password cannot be empty.");
////            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
////            dispatcher.forward(request, response);
////            return;
////        }
////
////        if (!newPassword.equals(confirmPassword)) {
////            request.setAttribute("errorMessage", "Passwords do not match.");
////            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
////            dispatcher.forward(request, response);
////            return;
////        }
//        
//        
//     // If valid token, update the password
//        boolean isUpdated = dao.updatePassword(token, newPassword); // Update password for the user
//
//        if (isUpdated) {
//            response.sendRedirect("login.jsp");
//        } else {
//            request.setAttribute("errorMessage", "Failed to update password.");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
//            dispatcher.forward(request, response);
//        }
//
//
//        
//        // At this point, the token is valid, and the passwords match
//        // Ideally, you should validate the token against a stored token (e.g., in the database)
////        String storedToken = getStoredTokenForUser(token); // Example function for retrieving the token from the database
////        if (storedToken == null || !storedToken.equals(token)) {
////            request.setAttribute("errorMessage", "Invalid or expired token.");
////            RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPswd2.jsp");
////            dispatcher.forward(request, response);
////            return;
////        }
//        
//     // Hash the password before saving it (e.g., using bcrypt or another hashing algorithm)
//        //String hashedPassword = hashPassword(newPassword); // hashPassword is a placeholder for your actual hashing function
//
//        // Save the new password (replace with actual database logic)
//        //updatePassword(token, hashedPassword); // Example function to update the password
//
//        // After updating, redirect to a success page or login page
//        //response.sendRedirect("login.jsp");
//	}
//}

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
//import org.model.dao.PswdResetDao;
import org.model.dao.ForgotPswdDao;

@WebServlet("/ResetPswdController")
public class ResetPswdController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handle GET requests: Display the reset password page with email
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    // Handle POST requests: Validate OTP and update the password
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
//        String otp = request.getParameter("otp");
        String newPassword = request.getParameter("new-pswd");
        String confirmPassword = request.getParameter("conf-pswd");  // Get confirm password

        // Check if any field is empty
        if (email == null || newPassword == null || email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
     // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Create instances of DAO classes for password reset and user handling
//        PswdResetDao resetDao = new PswdResetDao();
        ForgotPswdDao ForgotDao = new ForgotPswdDao();

        // Validate the OTP
//        if (resetDao.validateOTP(email, otp)) {
            // If OTP is valid, update the password in the users table
            boolean isUpdated = ForgotDao.updatePassword(email, newPassword);
            if (isUpdated) {
            	request.setAttribute("success", "Password reset successful!");
            	RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
            	dispatcher.forward(request, response);
            	System.out.println("Message set and forwarded: Password reset successful!");
            } else {
                // If password update fails, show an error message
                request.setAttribute("error", "Failed to update the password.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
                dispatcher.forward(request, response);
            }
//        } else {
            // If OTP is invalid or expired, show an error message
//            request.setAttribute("error", "Invalid or expired OTP.");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("ResetPswd.jsp");
//            dispatcher.forward(request, response);
        }
    }
//}