package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.model.bean.UserBean;
import org.model.dao.RegDao;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RegisterController() {
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("username");
		String email=request.getParameter("email");
		String phone = request.getParameter("phone");
		String pass=request.getParameter("pswd");
		
		// Validate phone number (ensure it is numeric and 10 digits long)
        if (!phone.matches("\\d{10}")) {
            request.setAttribute("errorMessage", "Invalid phone number! Please enter a 10-digit phone number.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }
		
		int status=0;
		UserBean rb=new UserBean();
		rb.setName(name);
		rb.setEmail(email);
		 rb.setPhone(phone); 
		rb.setPass(pass);
		
		RegDao rd=new RegDao();
		status=rd.insert(rb);
		if(status!=0)
		{	
			//request.getSession().setAttribute("userPhone", phone);
			response.sendRedirect("Register.jsp?msg=valid");
			
		}	
        else {
		    request.setAttribute("errorMessage" ,"Invalid email or password!");

            // Forward to the login page with the error message
           request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
	}
}