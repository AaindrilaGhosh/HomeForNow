package org;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/AdminEditUserServlet")
public class AdminEditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection connection = ConnectionProvider.createCon();
            String sql = "SELECT user_id, name, email, password FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Set user details in request attributes
                request.setAttribute("id", resultSet.getInt("user_id"));
                request.setAttribute("name", resultSet.getString("name"));
                request.setAttribute("email", resultSet.getString("email"));
                request.setAttribute("password", resultSet.getString("password"));
            }

            // Forward to edit form JSP
            request.getRequestDispatcher("editUserForm.jsp").forward(request, response);

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}