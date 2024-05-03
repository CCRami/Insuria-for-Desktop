package Controller;
import Entity.Offre;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class Mail {
    public static void sendConfirmationEmail(String recipientEmail, Offre selectedOffre) {
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

            // Prepare email content with offer details
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body>");
            emailContent.append("<div style='text-align: center;'>");
            String imageUrl = "https://drive.google.com/uc?export=view&id=1PF9iT3BL-ZP_mEncQVTVjeSQ3zAhuuIL";
            emailContent.append("<img src='" + imageUrl + "' alt='Logo' width='100' height='100' style='border-radius: 50%;'>");
            emailContent.append("<h1>Assurance Insuria</h1>");
            emailContent.append("<p>Bienvenue chez Insuria, votre partenaire en assurance.</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client,</p>");
            emailContent.append("<p>Nous avons le plaisir de vous informer des détails de l'offre que vous avez sélectionnée :</p>");
            emailContent.append("<ul>");
            emailContent.append("<li>Avantage de l'offre : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedOffre.getAdvantage() + "</span></li>");
            emailContent.append("<li>Conditions de l'offre : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedOffre.getConditions() + "</span></li>");
            emailContent.append("<li>Remise (%) : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedOffre.getDiscount() + "</span></li>");
            emailContent.append("<li>Durée de l'offre : <span style='font-weight: bold; text-decoration: underline; color: blue;'>" + selectedOffre.getDuration() + "</span></li>");
            emailContent.append("</ul>");
            emailContent.append("<p>Nous vous remercions pour votre intérêt pour notre offre.</p>");
            emailContent.append("<p>Cordialement,<br>L'équipe de support de Insuria</p>");
            emailContent.append("</body></html>");

            // Set email content as HTML
            message.setContent(emailContent.toString(), "text/html");

            // Set email subject
            message.setSubject("Détails de l'offre avec Insuria");

            // Send the email
            Transport.send(message);

            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}