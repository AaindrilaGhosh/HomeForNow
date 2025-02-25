package org;

import java.util.ArrayList;

public class Property {
	private int propertyId; // New field for property ID
    private int userId;
    private String email;
    private String address;
    private String type;
    private String carpetArea;
    private String furnishing;
    private String price;
    private String details;
    private String rules;
    private String cancellation;
    private String imagePath;  // For the first image
    private ArrayList<String> imagePaths; // Multiple images for a property
    private ArrayList<String> detailLines;

    // Getters and setters
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCarpetArea() {
        return carpetArea;
    }
    public void setCarpetArea(String carpetArea) {
        this.carpetArea = carpetArea;
    }
    public String getFurnishing() {
        return furnishing;
    }
    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public String getRules() {
        return rules;
    }
    public void setRules(String rules) {
        this.rules = rules;
    }
    public String getCancellation() {
        return cancellation;
    }
    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
    
    public ArrayList<String> getDetailLines() {
        return detailLines;
    }

    public void setDetailLines(ArrayList<String> detailLines) {
        this.detailLines = detailLines;
    }
}
