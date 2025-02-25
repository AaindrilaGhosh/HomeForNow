package org.misc;
import java.sql.*;
public class ConnectionProvider {
static Connection con;
public static Connection createCon()
{
	try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	con=DriverManager.getConnection("JDBC:MYSQL://localhost:3306/home_for_now","root","B7971@jkth#");//("URL","USER NAME","PASSWORD");
	}catch(Exception e) {}
	return con;
}
}