//package org;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import com.google.gson.Gson; // Make sure to include Gson dependency for JSON handling
//
//@WebServlet("/ToggleSavedServlet")
//public class ToggleSavedServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private SavedItemService savedItemService = new SavedItemService(); // Assuming you have a service class for handling saved items
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        
//        // Retrieve session and user ID
//        HttpSession session = request.getSession();
//        int userId = (int) session.getAttribute("user_id");
//
//        // Read property ID from request body
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = request.getReader().readLine()) != null) {
//            sb.append(line);
//        }
//        String jsonRequest = sb.toString();
//        int propertyId = new Gson().fromJson(jsonRequest, PropertyRequest.class).property_id;
//
//        // Check if the property is already saved
//        boolean isSaved = savedItemService.isPropertySaved(userId, propertyId);
//        
//        if (isSaved) {
//            // Unsaving the property
//            savedItemService.unsaveProperty(userId, propertyId);
//            out.print(new Gson().toJson(new ResponseStatus(false))); // Response: {"saved": false}
//        } else {
//            // Saving the property
//            savedItemService.saveProperty(userId, propertyId);
//            out.print(new Gson().toJson(new ResponseStatus(true))); // Response: {"saved": true}
//        }
//
//        out.flush();
//        out.close();
//    }
//
//    // Inner class to represent the request JSON structure
//    private static class PropertyRequest {
//        int property_id;
//    }
//
//    // Inner class to represent the response structure
//    private static class ResponseStatus {
//        boolean saved;
//
//        ResponseStatus(boolean saved) {
//            this.saved = saved;
//        }
//    }
//}
