package org.model.dao;
//import java.sql.*;
//import java.util.UUID;
//
//import org.misc.ConnectionProvider;
//
//public class PswdResetDao {
//
////    private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdb";
////    private static final String DB_USER = "root";
////    private static final String DB_PASSWORD = "password";
//
//    // Method to generate and store the reset token
//    public boolean storeResetToken(String email) {
//        String token = UUID.randomUUID().toString(); // Generate a unique token
//        long expiryTime = System.currentTimeMillis() + 3600000; // 1 hour from now
//        Timestamp expiryDate = new Timestamp(expiryTime);
//
//        int i=0;
//        Connection con=ConnectionProvider.createCon();
//        try{
//             PreparedStatement pstm = con.prepareStatement("INSERT INTO password_reset (Email, reset_token, expiry_date) VALUES (?, ?, ?)");
//            pstm.setString(1, email);
//            pstm.setString(2, token);
//            pstm.setTimestamp(3, expiryDate);
//             i = pstm.executeUpdate();
//            return i > 0; // Return true if token was stored successfully
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    
//    public boolean isTokenValid(String token) {
//        boolean status=false;
//        Connection con=ConnectionProvider.createCon();
//        try {
//             PreparedStatement pstm = con.prepareStatement("SELECT * FROM password_reset WHERE reset_token = ? AND expiry_date > NOW()");
//             pstm.setString(1, token);
//             ResultSet rs = pstm.executeQuery();
//             if (rs.next()) {
//                    status = true; // Token is valid
//             }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return status;
//    }
//    
//    // Method to update the user's password
//    public boolean updatePassword(String token, String newPassword) {
//    	boolean status = false;
//
//        // Retrieve the email associated with the token
//        String email = getTokenByEmail(token);
//        if (email == null) {
//            status = false; // Token is invalid or expired
//        }
//
//        // Update the password in the users table
//        int i = 0;
//        Connection con=ConnectionProvider.createCon();
//        try {
//             PreparedStatement pstm = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
//             pstm.setString(1, newPassword);
//             pstm.setString(2, email);
//             i = pstm.executeUpdate();
//             status = i > 0; // Return true if password was updated successfully
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return status;
//    }
//    
//// // Method to retrieve the email from the token
////    private String getEmailByToken(String token) {
////        Connection con=ConnectionProvider.createCon();
////        try {
////             PreparedStatement pstm = con.prepareStatement("SELECT Email FROM password_reset WHERE reset_token = ? AND expiry_date > NOW()");
////             pstm.setString(1, token);
////            try (ResultSet rs = pstm.executeQuery()) {
////                if (rs.next()) {
////                    return rs.getString("Email");
////                }
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return null; // Token not found or expired
////    }
//    
//    // Method to retrieve the reset token associated with an email (for Forgot Password flow)
//    public String getTokenByEmail(String email) {
//        String token = null;
//        Connection con = ConnectionProvider.createCon();
//        try {
//            PreparedStatement pstm = con.prepareStatement("SELECT reset_token FROM password_reset WHERE Email = ? AND expiry_date > NOW()");
//            pstm.setString(1, email);
//            ResultSet rs = pstm.executeQuery();
//            if (rs.next()) {
//                token = rs.getString("reset_token"); // Return the token if found
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return token; // If not found, returns null
//    }
//    
//    //optional
// // Cleanup expired tokens from the database (optional)
////    public void deleteExpiredTokens() {
////        Connection con = ConnectionProvider.createCon();
////        try {
////            PreparedStatement pstm = con.prepareStatement("DELETE FROM password_reset WHERE expiry_date < NOW()");
////            pstm.executeUpdate(); // Delete expired tokens
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//
//}

import java.sql.*;
//import org.utils.DBConnection;
import org.misc.ConnectionProvider;
//import org.model.PasswordReset;

public class PswdResetDao {
    // Store OTP for password reset
    public boolean storeOTP(String email, String otp) {
    	Connection con=ConnectionProvider.createCon();
        try {
        	PreparedStatement pst = con.prepareStatement("INSERT INTO password_reset_otp (email, otp, expiry) VALUES (?, ?, DATE_ADD(NOW(), INTERVAL 15 MINUTE))");
                pst.setString(1, email);
                pst.setString(2, otp);
                int rows = pst.executeUpdate();
                return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Validate OTP
    public boolean validateOTP(String email, String otp) {
    	Connection con=ConnectionProvider.createCon();
        try {
        	PreparedStatement pst = con.prepareStatement("SELECT * FROM password_reset_otp WHERE email = ? AND otp = ? AND expiry > NOW()");
                pst.setString(1, email);
                pst.setString(2, otp);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.next(); // returns true if OTP is valid and not expired
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}