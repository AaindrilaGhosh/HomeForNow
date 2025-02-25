package org.model.dao;

import java.sql.*;
import org.model.bean.UserBean;

import org.misc.ConnectionProvider;


public class ForgotPswdDao {
    // Check if the email exists in the database
    public boolean isEmailRegistered(String email) {
    	Connection con=ConnectionProvider.createCon();
    	boolean status = false;
        try {
              PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE email = ?");
              pst.setString(1, email);
              ResultSet rs = pst.executeQuery();
              if(rs.next())
      		  {
      			status=true;
      		   } // returns true if email is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Get user by email (for password reset validation)
    public UserBean getUserByEmail(String email) {
    	Connection con=ConnectionProvider.createCon();
        try {
              PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE email = ?");
              pst.setString(1, email);
              ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                    	UserBean user = new UserBean(); // Create an empty UserBean object
                        user.setUserId(rs.getInt("userId")); // Set id using setter
                        user.setEmail(rs.getString("email")); // Set email
                        user.setPass(rs.getString("pass")); // Set password
                        user.setName(rs.getString("name")); // Set name
                        return user;
                    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to update user password
    public boolean updatePassword(String email, String newPassword) {
    	Connection con=ConnectionProvider.createCon();
        try {
                PreparedStatement pst = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
                pst.setString(1, newPassword); // Set the new password
                pst.setString(2, email); // Set the user's email
                int rowsUpdated = pst.executeUpdate(); // Execute the update query
                return rowsUpdated > 0; // Return true if the password was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if something went wrong
    }
}