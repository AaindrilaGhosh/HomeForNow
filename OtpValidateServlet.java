package org;

import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.SQLException;

//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class ValidateOtp
 */
@WebServlet("/OtpValidateServlet")
public class OtpValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Retrieve all 6 OTP digits entered by the user
        String otpInput = request.getParameter("otp1") + request.getParameter("otp2") + request.getParameter("otp3") +
                          request.getParameter("otp4") + request.getParameter("otp5") + request.getParameter("otp6");
        
        
//        System.out.println(otpInput);
//		int value=Integer.parseInt(request.getParameter("otp"));
//		int value = request.getParameter("otp");
		
		//Get the OTP from the session
		HttpSession session=request.getSession();
		String sessionOtp = (String) session.getAttribute("otp");
		
		// Get the email from the request parameters
        String email = request.getParameter("email");
		
        System.out.println("otpservlet" + email);
        
		 // Debugging: Print the session OTP to check if it was properly set
        System.out.println("Session OTP: " + sessionOtp);
        System.out.println("User OTP input: " + otpInput);
//		System.out.println(sessionOtp);
//		int otp=(int)session.getAttribute("otp");
		
//		RequestDispatcher dispatcher=null;
		
		if (otpInput.equals(sessionOtp)) 
		{
			//OTP is correct, forward to ResetPswd.jsp
			request.setAttribute("email", request.getParameter("email"));
			request.setAttribute("status", "success");
			response.sendRedirect("ResetPswd.jsp?email=" + email);
//			dispatcher.forward(request, response);
			
		}
		else
		{
			//OTP is incorrect, forward back to OtpPage.jsp with error message
			request.setAttribute("message","Wrong OTP");
			response.sendRedirect("OtpPage.jsp");
//		    dispatcher=request.getRequestDispatcher("OtpPage.jsp");
		}
		//Forward the request to the appropriate page
//		dispatcher.forward(request, response);
	}
}