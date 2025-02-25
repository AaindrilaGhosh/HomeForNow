//package org;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@WebServlet("/logout")
//public class LogoutServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Invalidate the current session
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        // Redirect to the login page or home page
//        response.sendRedirect("adminLogin.jsp");
//    }
//}

package org;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the current session, if it exists
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Check if it's an admin or user session
            String userEmail = (String) session.getAttribute("userEmail");
            String adminEmail = (String) session.getAttribute("adminEmail");

            // Invalidate the session
            session.invalidate();

            // Redirect based on the type of user
            if (adminEmail != null) {
                response.sendRedirect("adminLogin.jsp");
            } else if (userEmail != null) {
                response.sendRedirect("login.jsp");
            } else {
                // Default redirect if no specific session attribute is found
                response.sendRedirect("index.jsp");
            }
        } else {
            // If no session exists, redirect to a default page (e.g., home page)
            response.sendRedirect("index.jsp");
        }
    }
}