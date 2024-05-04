    package Controllers;

    import com.twilio.Twilio;
    import com.twilio.rest.api.v2010.account.Message;
    import com.twilio.type.PhoneNumber;
    import javafx.fxml.FXML;
    import javafx.scene.control.TextField;
public class SMS {
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String TWILIO_PHONE_NUMBER = "";
    public static final String DEFAULT_PHONE_NUMBER = ""; // Default recipient phone number
    public static final String DEFAULT_MESSAGE = "We got a new offer check our app to find out about it"; // Default message content

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField messageField;

    @FXML
    private void sendSMS() {
        String phoneNumber = DEFAULT_PHONE_NUMBER;
        String message = DEFAULT_MESSAGE;

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Send SMS
        Message twilioMessage = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();

        System.out.println("Message sent successfully. SID: " + twilioMessage.getSid());
    }

    public static void sendSMSExternally() {
        SMS sms = new SMS(); // Create an instance of SMS
        sms.sendSMS(); // Call the non-static sendSMS method on the instance
    }
}