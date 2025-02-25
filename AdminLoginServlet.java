package org;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.misc.ConnectionProvider;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
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
		
		String email = request.getParameter("uemail");
		String pass = request.getParameter("upass");
		
		//validate the input
		if (email != null && pass != null) {
			try {
				//check credentials from the database
				if (validateAdminCredentials(email, pass)) {
					response.sendRedirect("AdminDashboard.jsp");
				}
				else {
					request.setAttribute("errorMessage", "Invalid email or password!");
					request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
				}
			} catch(SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "An error occurred. Please try again later.");
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            }
		}
	}

    //Method to validate admin credentials from the database
    private boolean validateAdminCredentials(String email, String password) throws SQLException {
       boolean isValid = false;

       Connection con = ConnectionProvider.createCon();
       try {
          PreparedStatement pstm = con.prepareStatement("SELECT password FROM admins WHERE email = ?");
          pstm.setString(1, email);
          ResultSet resultSet = pstm.executeQuery();

          if (resultSet.next()) {
              String storedPassword = resultSet.getString("password");
              //check if the entered password matches the stored hash
              if (password.equals(storedPassword)) {
                  isValid = true;
              }
          }
       }catch(SQLException e) {}
       return isValid;
    }
}