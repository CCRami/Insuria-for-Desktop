    package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
public class SMS {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String TWILIO_PHONE_NUMBER = "+12314278194";

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField messageField;

    @FXML
    private void sendSMS() {
        String phoneNumber = phoneNumberField.getText();
        String message = messageField.getText();

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send SMS
        Message twilioMessage = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();

        System.out.println("Message sent successfully. SID: " + twilioMessage.getSid());
    }
}