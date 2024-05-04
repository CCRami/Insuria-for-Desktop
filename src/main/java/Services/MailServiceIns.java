package Services;

import Entities.Commande;
import Entities.Insurance;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class MailServiceIns {
    public static void sendConfirmationEmail(String recipientEmail, Insurance selectedInsurance, List<Commande> userCommandes) {
        final String username = "rami.toubib2014@gmail.com";
        final String password = "kngp embc okyp iodi";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Create a StringBuilder to store the email content
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body>");
            emailContent.append("<div style='text-align: center;'>");
            emailContent.append("<h1>Insuria Insurance</h1>");
            emailContent.append("<p>Welcome to Insuria, your insurance partner.</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Dear Client,</p>");
            emailContent.append("<p>We are pleased to inform you that a new insurance has been successfully added.</p>");
            emailContent.append("<p>Insurance Details :</p>");
            emailContent.append("<ul>");
            emailContent.append("<li><strong>Name:</strong> ").append(selectedInsurance.getName_ins()).append("</li>");
            emailContent.append("<li><strong>Price:</strong> ").append(selectedInsurance.getMontant()).append("</li>");
            // Add other insurance details...

            // Compare with existing insurances in user's commandes
            emailContent.append("<p>Here are your existing insurances:</p>");
            emailContent.append("<ul>");
            boolean isMatchFound = false;
            for (Commande commande : userCommandes) {
                Insurance existingInsurance = commande.getDoa_com_id();
                boolean isMatch = existingInsurance.getName_ins().equals(selectedInsurance.getName_ins())
                        && existingInsurance.getMontant() == selectedInsurance.getMontant();

                if (isMatch) {
                    isMatchFound = true;
                    emailContent.append("<li><strong>").append(existingInsurance.getName_ins()).append(" (Price: ").append(existingInsurance.getMontant()).append(") - This matches your new insurance!</strong></li>");
                } else {
                    emailContent.append("<li>").append(existingInsurance.getName_ins()).append(" (Price: ").append(existingInsurance.getMontant()).append(")</li>");
                }
            }
            emailContent.append("</ul>");

            if (isMatchFound) {
                emailContent.append("<p>Note: One or more of your existing insurances match the new one added.</p>");
            } else {
                emailContent.append("<p>All existing insurances are listed above, with no direct matches to the new one.</p>");
            }


            emailContent.append("<p>Your insurance portfolio has been updated with this new insurance.</p>");
            emailContent.append("<p>Thank you for your trust and loyalty to Insuria.</p>");
            emailContent.append("<p>Regards,<br>The Insuria Team</p>");
            emailContent.append("</body></html>");

            // Set email content and subject
            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("New Insurance Successfully Added at Insuria");

            // Send the email
            Transport.send(message);

            System.out.println("Confirmation email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}


