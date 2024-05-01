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
            emailContent.append("<h1>Confirmation d'adresse email</h1>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client "+ user.getLast_name() +",</p>");
            emailContent.append("<p>Merci pour votre inscription chez Assurance Insuria.</p>");
            emailContent.append("<p>Pour confirmer votre adresse email, utilisez ce code :</p>");
            emailContent.append("<p>"+ user.getSecret() +"</p>");
            emailContent.append("<p>Confirmer votre adresse email</p>");
            emailContent.append("<p>Si vous n'avez pas inscrit chez Assurance Insuria, vous pouvez ignorer cet email.</p>");
            emailContent.append("<p>Cordialement,<br>Insuria Team</p>");
            emailContent.append("</body></html>");

            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("Confirmation d'adresse email - Assurance Insuria");

// Send the email
            Transport.send(message, message.getAllRecipients());


            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


