package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/FetchUserDetailsServlet")
public class FetchUserDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Establish database connection
            Connection con = ConnectionProvider.createCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            // Start building the HTML response
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User Management</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 80%; margin: 20px auto; }");
            out.println("th, td { border: 1px solid black; padding: 10px; text-align: center; }");
            out.println("th { background-color: #09648b; color: white; }");
            out.println("a { text-decoration: none; padding: 5px 10px; color: white; border-radius: 4px; }");
            out.println(".edit { background-color: #4CAF50; }");
            out.println(".delete { background-color: #f44336; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 style='text-align: center;'>User Management</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>User ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Email</th>");
            out.println("<th>Password</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");

            // Populate table rows from the result set
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + password + "</td>");
                out.println("<td>");
                out.println("<a href='AdminEditUserServlet?id=" + id + "' class='edit'>Edit</a>");
                out.println("<a href='AdminDeleteUserServlet?id=" + id + "' class='delete'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            // Close the table and HTML
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red; text-align:center;'>An error occurred: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }
}
   /*   try {
            // Get connection from ConnectionProvider
            Connection con = ConnectionProvider.createCon();

            // Prepare SQL query
            String sql = "SELECT user_id, name, email, password FROM users";
            PreparedStatement statement = con.prepareStatement(sql);

            // Execute query and retrieve result set
            ResultSet resultSet = statement.executeQuery();

            // Set result set in request scope to forward it to JSP
            request.setAttribute("userDetails", resultSet);
            request.getRequestDispatcher("Users.jsp").forward(request, response);

            // Do not close connection here as it may be managed by ConnectionProvider
        } catch (Exception e) {
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}*/