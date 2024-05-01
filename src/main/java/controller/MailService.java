package controller;

import entity.Indemnissation;
import entity.Reclamation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {
    public static void sendConfirmationEmail(String recipientEmail, Reclamation selectedReclamation) {
        final String username = "farahaddad68@gmail.com";
        final String password = "sdcv chwm wlzu phds";

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
            String imageUrl = "https://drive.google.com/uc?export=view&id=1PF9iT3BL-ZP_mEncQVTVjeSQ3zAhuuIL";
            emailContent.append("<img src='" + imageUrl + "' alt='Logo' width='100' height='100' style='border-radius: 50%;'>");
            emailContent.append("<h1>Assurance Insuria</h1>");
            emailContent.append("<p>Bienvenue chez Insuria, votre partenaire en assurance.</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client,</p>");
            emailContent.append("<p>Nous avons le plaisir de vous informer que votre réclamation a été traitée avec succès.</p>");
            emailContent.append("<p>Détails de la Réclamation :</p>");
            emailContent.append("<ul>");
            emailContent.append("<li>Libellé de la Réclamation : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedReclamation.getLibelle() + "</span></li>");
            emailContent.append("<li>Date de Réclamation : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedReclamation.getDateReclamation() + "</span></li>");
            emailContent.append("</ul>");
            emailContent.append("<p>Votre demande a été prise en compte et nous avons pris les mesures nécessaires pour y répondre.</p>");
            emailContent.append("<p>Nous vous remercions pour votre confiance et votre patience.</p>");
            emailContent.append("<p>Cordialement,<br>L'équipe de support de Insuria</p>");
            emailContent.append("</body></html>");
            message.setText("Your Compensation has been successfully registered. Thank you!");
            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("Confirmation de réclamation avec Insuria");
            Transport.send(message, message.getAllRecipients());

            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}



