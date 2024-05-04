package Controllers;

import Entities.Reclamation;

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
            emailContent.append("<img src='" + imageUrl + "' alt='Logo' width='200' height='200' style='border-radius: 50%;'>");
            emailContent.append("<h1>Assurance Insuria</h1>");
            emailContent.append("<p>Bienvenue chez Insuria, votre partenaire en assurance.</p>");
            emailContent.append("</div>");
            emailContent.append("<div style='background-color:  #f0f8ff ; padding: 20px;'>"); // Ajoutez le style pour le cadre bleu ciel
            emailContent.append("<p style='color: #000080;'>Dear ,</p>"); // Changez la couleur du texte en bleu foncé
// Ajoutez le reste de votre contenu ici avec les styles CSS appropriés
            emailContent.append("<p>We hope this email finds you well..</p>");
            emailContent.append("<p>We wanted to inform you that a new compensation has been added to your reclamation </p>");
            emailContent.append("<p>Détails de la Réclamation :</p>");
            emailContent.append("<ul>");
            emailContent.append("<li>Label : <span style='font-weight: bold; text-decoration: underline; color:  red;'>" + selectedReclamation.getLibelle() + "</span></li>");
            emailContent.append("<li>This compensation, applied on: <span style='font-weight: bold; text-decoration: underline; color: red;'>" + selectedReclamation.getDateReclamation() + "</span></li>");
            emailContent.append("</ul>");
            emailContent.append("<p> , has been carefully reviewed to address any concerns or issues you may have experienced.</p>");
            emailContent.append("<p>We are committed to ensuring your satisfaction and strive to provide the best possible service.</p>");
            emailContent.append("<p>If you have any questions or require further assistance regarding this compensation, please do not hesitate to reach out to us.<p>");
            emailContent.append("<p>Best regards,,<br>Insuria Team</p>");
            emailContent.append("</div>");
            emailContent.append("</body></html>");

            message.setText("Your Compensation has been successfully registered. Thank you!");
            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("New Compensation Added to Your Reclamation");
            Transport.send(message, message.getAllRecipients());

            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}



