package org;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {

    // Method to send OTP email
    public static void sendOTPEmail(String recipientEmail, String otp) {
        // Sender's email ID needs to be mentioned
        String fromEmail = "aaindrilaghosh@gmail.com"; // Replace with your email
        String fromPassword = "nhfh vjlr cceh gxtp"; // Replace with your email password
        System.out.println(recipientEmail);

        // Assuming you are using Gmail SMTP server
        String host = "smtp.gmail.com";  // SMTP server for Gmail

        // Set properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("Password Reset OTP");

            // Set the actual message
            String emailBody = "Your OTP for password reset is: " + otp;
            message.setText(emailBody);

            // Send the message
            Transport.send(message);
            System.out.println("OTP email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
 // Method to send confirmation email
    public static void sendConfirmationEmail(String recipientEmail, String subject, String body) {
        // Sender's email ID needs to be mentioned
        String fromEmail = "aaindrilaghosh@gmail.com"; // Replace with your email
        String fromPassword = "nhfh vjlr cceh gxtp"; // Replace with your email password
        System.out.println("Sending confirmation email to: " + recipientEmail);

        // Assuming you are using Gmail SMTP server
        String host = "smtp.gmail.com";  // SMTP server for Gmail

        // Set properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            //message.setSubject("Booking Confirmation");
            message.setSubject(subject);

            // Set the actual message
//            String emailBody = "Thank you for your booking!\n\n" +
//                    "Property Type: " +  + "\n" +
//                    "Booking Amount: ₹" + body + "\n\n" +
//                    "We look forward to your stay!";
//            message.setText(emailBody);
            message.setText(body);
            
            // Send the message
            Transport.send(message);
            System.out.println("Confirmation email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
