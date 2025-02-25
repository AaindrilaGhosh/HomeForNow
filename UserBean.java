package org.model.bean;

public class UserBean {
	private Integer userId;
	private String name;
	private String email;
	private String pass;
	private String phone;

	
	// Constructor with four parameters
    public void UserBean(int userId, String name, String email, String pass, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
    }
    
//    // Constructor with three parameters
//    public UserBean(String name, String email, String pass) {
//        this.name = name;
//        this.email = email;
//        this.pass = pass;
//    }
    
//    // Constructor with two parameters
//    public UserBean(String email, String pass) {
//        this.email = email;
//        this.pass = pass;
//    }
    
	//Getters and setters for each field
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
public String getPhone() { // Getter for phone
    return phone;
}

public void setPhone(String phone) { // Setter for phone
    this.phone = phone;
}
}