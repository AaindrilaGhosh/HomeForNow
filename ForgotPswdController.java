package org.controller;

import java.io.IOException;
import java.util.Random;

import org.model.dao.ForgotPswdDao;
import org.model.dao.PswdResetDao;
import org.EmailUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ForgotPswdController
 */
@WebServlet("/ForgotPswdController")
public class ForgotPswdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPswdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
        String email = request.getParameter("email");
        
        ForgotPswdDao userDao = new ForgotPswdDao();
        PswdResetDao resetDao = new PswdResetDao();
        
     // Generate a random 6-digit OTP
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(999999)); // Generate a 6-digit OTP

        // Store the OTP in the session
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);  // Store OTP as a session attribute

        // Store the email for later use (if needed)
        request.setAttribute("email", email);

//        System.out.println("forgotpswdservlet" + email);
        if (userDao.isEmailRegistered(email)) {
         // Store OTP in the database or session (to be verified during password reset)
            if (resetDao.storeOTP(email, otp)) {
                EmailUtils.sendOTPEmail(email, otp); // Helper class to send email
                response.sendRedirect("OtpPage.jsp?email=" + email); // Redirect to OtpPage.jsp with email as a query parameter
            } else {
                request.setAttribute("error", "Failed to send OTP.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPswd.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Email not found.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPswd.jsp");
            dispatcher.forward(request, response);
        }
    }
}