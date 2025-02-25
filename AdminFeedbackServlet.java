package org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.misc.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminFeedbackServlet")
public class AdminFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            Connection con = ConnectionProvider.createCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM feedback");
            ResultSet rs = ps.executeQuery();
            PrintWriter out = response.getWriter();

            out.println("<h1 style='text-align:center;margin-top:20px;'>FEEDBACK LIST</h1>");
            out.println("<html><body><table style='border: 1px solid black; border-collapse: collapse; width: 80%; margin-left: 120px;'>");
            out.println("<tr style='border: 1px solid black; border-collapse: collapse; background-color:#D8BFD8;height:30px;font-size: 25px;'>");
            out.println("<td style='border: 1px solid black; border-collapse: collapse; text-align: center;'>ID</td>");
            out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>Username</td>");
            out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>Email</td>");
            out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>Rating</td>");
            out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>Comments</td>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr style='border: 1px solid black; border-collapse: collapse;'>");
                out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>" + rs.getString(1) + "</td>");
                out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>" + rs.getString(2) + "</td>");
                out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>" + rs.getString(3) + "</td>");
                out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>" + rs.getString(4) + "</td>");
                out.println("<td style='border: 1px solid black; border-collapse: collapse;text-align: center;'>" + rs.getString(5) + "</td>");
                out.println("</tr>");
            }

            out.println("</table></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
