package Controllers.User;
import Services.MailService;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import Entities.User;
import helper.AlertHelper;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import Services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class SignupController {

    private final UserService us=new UserService();

    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://127.0.0.1/connect/google/check"; // Redirect URI for Google OAuth
    private static final String APPLICATION_NAME = "Insuria"; // Your application name
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String CLIENT_ID = "72213814901-54pea0l9jdmqta8acugefrhjvm90oi1g.apps.googleusercontent.com";
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"};

    private static final NetHttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Error initializing HTTP Transport", e);
        }
    }

    private GoogleAuthorizationCodeFlow flow;



    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField mdpTF;

    @FXML
    private PasswordField mdpTF1;

    @FXML
    private DatePicker DBTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField telTF;

    @FXML
    private Button googlebtn;

    @FXML
    private WebView webView;
    Window window;
    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        if(emailTF.getText().equals("") || mdpTF.getText().equals("")|| DBTF.getValue().toString().equals("")|| nomTF.getText().equals("")|| prenomTF.getText().equals("")){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Empty Fields .");
            return;
        }
        System.out.println(emailTF.getText());
        if(!patternMatches(emailTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Invalid email .");
            return;
        }
        System.out.println(telTF.getText());
        if(!isNumeric(telTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Invalid phone number .");
            return;
        }
        if(mdpTF.getText().length()<8){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Password must be at least 8 characters .");
            return;
        }
        if(!mdpTF.getText().equals(mdpTF1.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "Passwords do not match .");
            return;
        }
        System.out.println("test2");

        System.out.println("test3");
        if (us.exsitemail(emailTF.getText())){
            AlertHelper.showAlert(Alert.AlertType.ERROR,  window, "Error",
                    "User Already Exist.");
        }
        else {
            User u=new User(nomTF.getText(), prenomTF.getText(), emailTF.getText(), mdpTF.getText(), Integer.parseInt(telTF.getText()), DBTF.getValue().toString(), "[\"ROLE_CLIENT\"]", false, false, null, RandomStringUtils.randomAlphanumeric(103));
            us.add(u);
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "User Signed Up", "User Signed Up successfully");
            String emailValue = emailTF.getText();
            MailService.sendConfirmationEmail(emailValue,u);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            LoginController auc = loader.getController();
            nomTF.getScene().setRoot(root);

        }
    }


    public boolean patternMatches(String emailAddress) {
        final String regex = "^(.+)@(.+)$";
        return Pattern.compile(regex) .matcher(emailAddress) .matches(); }

    public static boolean isNumeric(String str) {
        if (str == null) return false; // handle null-pointer
        if (str.length() == 0) return false; // handle empty strings
        // to make sure that the input is numeric, we have to go through the characters
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false; // we found a non-digit character so we can early return
        }
        return true;
    }


    @FXML
    void ggsignup(ActionEvent event) throws IOException {
        if (event.getSource() == googlebtn) {
            initiateGoogleSignup();
        }

    }


    private void initiateGoogleSignup() throws IOException {
        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, getClientSecrets(), Arrays.asList(SCOPES))
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();

        webView.setVisible(true);
        // Load the authorization URL into the WebView
        webView.getEngine().load(url);

        // Add a listener to detect when the WebView finishes loading
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                String currentUrl = webView.getEngine().getLocation();
                if (currentUrl != null && currentUrl.startsWith(REDIRECT_URI)) {
                    handleRedirectUrl(currentUrl);
                }
            }
        });
    }

    private void handleRedirectUrl(String redirectUrl) {
        // Extract the authorization code from the redirect URL
        String authorizationCode = extractAuthorizationCode(redirectUrl);

        if (authorizationCode != null) {
            // Call your existing method to handle the authorization code
            handleGoogleCallback(authorizationCode);
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Failed to extract authorization code");
        }
    }

    public void handleGoogleCallback(String authorizationCode) {
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authorizationCode, REDIRECT_URI)
                    .execute();

            // Now you have the access token, you can use it to make requests on behalf of the user
            String idTokenString = tokenResponse.getIdToken();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Extract user information from the ID token payload
                System.out.println(payload);
                String email = payload.getEmail();
                String firstName = (String) payload.get("given_name");
                String lastName = (String) payload.get("family_name");
                String avatarUrl = (String) payload.get("picture");
                us.add(new User(firstName, lastName, email, idTokenString,0, LocalDate.now().toString(), "[\"ROLE_CLIENT\"]", true, false, avatarUrl, RandomStringUtils.randomAlphanumeric(103)));
            } else {
                System.out.println("Invalid ID token.");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            emailTF.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Failed to retrieve access token");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
        private String extractAuthorizationCode(String redirectUrl) {
        // Extract the authorization code from the redirect URL
        // You need to parse the URL and extract the value of the 'code' parameter
        // For example, if the URL is "http://127.0.0.1/connect/google/check?code=AUTHORIZATION_CODE_HERE"
        // You can extract the authorization code by splitting the URL and getting the value after 'code='
        String[] parts = redirectUrl.split("\\?");
        if (parts.length > 1) {
            String[] queryParams = parts[1].split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("code")) {
                    System.out.println(keyValue[1]);
                    return URLDecoder.decode(keyValue[1]);
                }
            }
        }
        return null;
    }

    private static GoogleClientSecrets getClientSecrets() throws IOException {
        String clientSecretJson = "{\"installed\":{\"client_id\":\"" + CLIENT_ID + "\",\"client_secret\":\"" + CLIENT_SECRET + "\",\"redirect_uris\":[\"" + REDIRECT_URI + "\"]}}";
        return GoogleClientSecrets.load(JSON_FACTORY, new StringReader(clientSecretJson));
    }


}