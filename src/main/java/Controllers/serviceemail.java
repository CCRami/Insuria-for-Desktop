package Controllers;




import javax.mail.*;
        import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class serviceemail {
    public static void sentemail(String recipientEmail) {
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
            emailContent.append("<p>Warning!!!</p>");
            emailContent.append("</div>");
            emailContent.append("<p>Cher Client,</p>");
            emailContent.append("<p>you wrote a comment containing a bad word </p>");
            emailContent.append("<p>If you have any questions or require further assistance regarding this compensation, please do not hesitate to reach out to us.<p>");
            emailContent.append("<p>Best regards,<br>Insuria Team</p>");
            emailContent.append("</body></html>");
            //    message.setText("Nous avez ecrit un commentaire contient un mot grave ");
            message.setContent(emailContent.toString(), "text/html");
            message.setSubject("Avertissement");
            Transport.send(message, message.getAllRecipients());


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}