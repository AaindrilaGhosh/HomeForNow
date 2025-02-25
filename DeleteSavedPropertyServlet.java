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

@WebServlet("/DeleteSavedPropertyServlet")
public class DeleteSavedPropertyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int propertyId = Integer.parseInt(request.getParameter("id")); // Get the property ID
        int savedItemId = Integer.parseInt(request.getParameter("id")); // Get the id from the saved_items table
        boolean isDeleted = false;

        try {
            Connection con = ConnectionProvider.createCon();
            String sql = "DELETE FROM saved_items WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, savedItemId);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }

            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to saved items page with a success flag
        response.sendRedirect("SavedItems.jsp?deleted=" + isDeleted);
    }
}