package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = ConnectionProvider.createCon();
            String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE user_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, userId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                response.getWriter().println("<h3>User updated successfully!</h3>");
                response.sendRedirect("FetchUserDetailsServlet");
            } else {
                response.getWriter().println("<h3>Failed to update user. Try again!</h3>");
            }

            statement.close();
        } catch (Exception e) {
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}