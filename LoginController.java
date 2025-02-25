package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import org.model.bean.UserBean;
import org.model.dao.RegDao;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("uemail");
		String pass=request.getParameter("upass");
		
	
		UserBean rb=new UserBean();
		rb.setEmail(email);
		rb.setPass(pass);
		
		RegDao rd=new RegDao();
		boolean status=rd.login(rb);	
		
		if(status) {
			// Assuming login method sets userId and other details in rb if login is successful
            // Create a session and store the username
            HttpSession session = request.getSession();
            session.setAttribute("userName", rb.getName()); // Only storing the name as a string
            session.setAttribute("userEmail", rb.getEmail());
            session.setAttribute("userPass", rb.getPass());
            session.setAttribute("userId", rb.getUserId());
            session.setAttribute("userPhone", rb.getPhone());
			  response.sendRedirect("index.jsp");
		}else {
			
		    request.setAttribute("errorMessage" ,"Invalid email or password!");

            // Forward to the login page with the error message
           request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}