package Services;

import Entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {
    public static void sendConfirmationEmail(String recipientEmail, User user) {
        final String username = "rami.toubib2014@gmail.com";
        final String password = "kngp embc okyp iodi";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));


            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body>");
            emailContent.append("<div style='text-align: center;'>");
            emailContent.append("<h1>Email Address Confirmation</h1>");
            emailContent.append("</div>");
            emailContent.append("<p>Dear Customer "+ user.getLast_name() +",</p>");
            emailContent.append("<p>Thank you for signing up with Insuria Insurance.</p>");
            emailContent.append("<p>To confirm your email address, please use this code:</p>");
            emailContent.append("<p>"+ user.getSecret() +"</p>");
            emailContent.append("<p>Confirm your email address</p>");
            emailContent.append("<p>If you did not sign up with Insuria Insurance, you can ignore this email.</p>");
            emailContent.append("<p>Regards,<br>Insuria Team</p>");
            emailContent.append("</body></html>");
            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("Email Address Confirmation - Insuria Insurance");


// Send the email
            Transport.send(message, message.getAllRecipients());


            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendResetPassEmail(String recipientEmail, String Pass, User user) {
        final String username = "rami.toubib2014@gmail.com";
        final String password = "kngp embc okyp iodi";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));


            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body>");
            emailContent.append("<div style='text-align: center;'>");
            emailContent.append("<h1>Password Reset Confirmation</h1>");
            emailContent.append("</div>");
            emailContent.append("<p>Dear "+ user.getLast_name() +",</p>");
            emailContent.append("<p>We have received a request to reset the password associated with your account.</p>");
            emailContent.append("<p>To proceed with the email reset, please use the following verification code:</p>");
            emailContent.append("<p>"+ Pass +"</p>");
            emailContent.append("<p>If you did not request this change, please contact our support team immediately.</p>");
            emailContent.append("<p>Regards,<br>Insuria Team</p>");
            emailContent.append("</body></html>");

            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("Password Reset Confirmation - Insuria Insurance");

// Send the email
            Transport.send(message, message.getAllRecipients());


            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


