package org;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/GenerateOtpServlet")
public class GenerateOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Generate a random 6-digit OTP
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(999999)); // Generate a 6-digit OTP

        // Store the OTP in the session
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);  // Store OTP as a session attribute

        // Store the email for later use (if needed)
        String email = request.getParameter("email");
        request.setAttribute("email", email);

        // Forward to OTP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("OtpPage.jsp");
        dispatcher.forward(request, response);
    }
}