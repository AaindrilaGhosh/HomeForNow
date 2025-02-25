package org.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.misc.ConnectionProvider;
import org.model.bean.UserBean;

public class RegDao {
public int insert(UserBean bean)
{
	String name=bean.getName();
	String email=bean.getEmail();
	String pass=bean.getPass();
	String phone = bean.getPhone();
	int i=0;
	Connection con=ConnectionProvider.createCon();
	try {
		PreparedStatement pstm=con.prepareStatement("insert into users (name, email, password, phone) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//		pstm.setString(1, userId);
		pstm.setString(1, name);
		pstm.setString(2, email);
		pstm.setString(3, pass);
		pstm.setString(4, phone);
		i=pstm.executeUpdate();
		
		// Retrieve the generated user ID
        if (i > 0) {
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                bean.setUserId(rs.getInt(1)); // Set the generated user ID
            }
        }
        con.close();
	}catch(Exception e) {}
	return i;
}

public boolean login(UserBean bean)
{
	String email=bean.getEmail();
	String pass=bean.getPass();
	boolean status=false;
	Connection con=ConnectionProvider.createCon();
	try {
		PreparedStatement pstm=con.prepareStatement("select * from users where email=? and password=?");
		pstm.setString(1, email);
		pstm.setString(2, pass);
		ResultSet rs=pstm.executeQuery();
		if(rs.next())
		{
//			System.out.println("Username fetched: " + rs.getString("name"));
			// Set user details if login is successful
            bean.setUserId(rs.getInt("user_id")); // Make sure this matches your database column name 
            //System.out.println(rs.getInt("user_id"));
			bean.setName(rs.getString("name")); //set username in userBean
			bean.setPass(rs.getString("password")); 
			bean.setPhone(rs.getString("phone"));
			status=true;
		}
	}catch(Exception e) {}
	return status;
}

public String fetchUserName(UserBean user) {
    String userName = null;
    Connection con = ConnectionProvider.createCon();
    try {
    	PreparedStatement ps = con.prepareStatement("SELECT name FROM users WHERE email = ?");
        ps.setString(1, user.getEmail());
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                userName = rs.getString("name");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return userName;
}
}