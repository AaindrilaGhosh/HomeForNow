package org;

import java.io.BufferedReader;
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
import org.EmailUtils; // Import the EmailUtil class

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve payment details from the form
        String cardNumber = request.getParameter("cardNumber").replaceAll("\\s+", "");
        String expMonth = request.getParameter("expMonth");
        String expYear = request.getParameter("expYear");     
        String cvv = request.getParameter("cvv");
        String email = request.getParameter("email");
        String amount = request.getParameter("amount");
        String propertyType = request.getParameter("propertyType"); // Assuming you pass property name or details
        String propertyAddress = request.getParameter("propertyAddress");
        String propertyId = request.getParameter("propertyId");
        
        try {
        	
        	// Validate card details (basic checks)
            if (!cardNumber.matches("\\d{16}") || !cvv.matches("\\d{3}")) {
                response.getWriter().write("Invalid card details.");
                return;
            }

            // Mask card number to save only last 4 digits
            String cardLast4 = cardNumber.substring(cardNumber.length() - 4);
            
            // Use your existing ConnectionProvider to get a connection
            Connection con = ConnectionProvider.createCon();
            String query = "INSERT INTO payments (user_email, card_number_last4, exp_month, exp_year, amount_paid) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, cardLast4);
            pstmt.setString(3, expMonth);
            pstmt.setString(4, expYear);
            pstmt.setBigDecimal(5, new java.math.BigDecimal(amount)); // Ensure proper decimal handling

            // Execute the query
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
            	request.setAttribute("propertyId", propertyId);
                request.setAttribute("email", email);
                request.setAttribute("amount", amount);
                
            	// Fetch owner details based on the property
                String ownerQuery = "SELECT u.name AS owner_name, u.email AS owner_email, u.phone AS owner_phone FROM property_inquiries p JOIN users u ON p.user_id = u.user_id WHERE p.property_id = ?";
                PreparedStatement ownerStmt = con.prepareStatement(ownerQuery);
                ownerStmt.setString(1, propertyId);
                ResultSet rs = ownerStmt.executeQuery();

                String ownerName = "N/A";
                String ownerEmail = "N/A";
                String ownerPhone = "N/A";

                if (rs.next()) {
                    ownerName = rs.getString("owner_name");
                    ownerEmail = rs.getString("owner_email");
                    ownerPhone = rs.getString("owner_phone");
                }
                
            	// Send confirmation email
                String subject = "Booking Confirmation";
                String body = "Dear Customer,\n\nThank you for your booking!\n"
                        + "You have successfully booked the property:\n" + propertyType + "\n" + propertyAddress + "\n"
                        + "Booking Amount: " + amount + "\n\n"
                        + "Owner Details:\n"
                        + "Name: " + ownerName + "\n"
                        + "Email: " + ownerEmail + "\n"
                        + "Phone: " + ownerPhone + "\n\n"
                        + "For further communication and coordination, please reach out directly to the property owner using the contact details provided.\n"
                        + "Best regards,\nHomeForNow";
                
                EmailUtils.sendConfirmationEmail(email, subject, body); // Send email
                //response.sendRedirect("confirmation.jsp?email=" + email + "&amount=" + amount);
//                response.sendRedirect("ConfirmBookingServlet?propertyId=" + propertyId + "&email=" + email + "&amount=" + amount);
                request.getRequestDispatcher("confirmBooking.jsp").forward(request, response);
            } else {
                // Handle failure case
            	response.sendRedirect("payment.jsp?errorMessage=Transaction failed. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
