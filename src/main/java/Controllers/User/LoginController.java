package Controllers.User;


import Controllers.dashboardFront;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import Services.UserService;



import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

//import entities.UserSession;
import Controllers.User.UserProfileController;
import Controllers.dashboard;
import Entities.User;
import Entities.UserSession;
import com.google.api.client.googleapis.auth.oauth2.*;
import helper.AlertHelper;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import Services.UserService;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
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
    private Button rest;

    @FXML
    private Button btn;
    @FXML
    private Button btnaz;

    @FXML
    private TextField mail;

    @FXML
    private WebView webView;

    @FXML
    private Button gglog;

    @FXML
    private PasswordField password;
    Window window;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    @FXML
    void AjouterUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Signup.fxml"));
        Parent root=loader.load();
        SignupController auc=loader.getController();
        btnaz.getScene().setRoot(root);


    }


    @FXML
    void gotoConfimer(ActionEvent event) throws IOException {
        UserService us = new UserService();
        int id = us.getUserIdByEmail(mail.getText());
        if(id!=0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPass.fxml"));
            User u = us.displayByid(id);
            String pass= RandomStringUtils.randomAlphanumeric(10);
            MailService.sendResetPassEmail(mail.getText(),pass, u);
            Parent root = loader.load();
            ResetPassController controller = loader.getController();
            controller.pass= pass;
            controller.email=mail.getText();
            Stage stage = new Stage();
            stage.setTitle("Reset password");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "This email is not registered yet. Please sign up first.");
        }
    }


    void goToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root=loader.load();
        HomeController auc= loader.getController();
        rest.getScene().setRoot(root);
    }
    void goToClient() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root=loader.load();
        dashboardFront auc=loader.getController();
        rest.getScene().setRoot(root);
    }

    @FXML
    void login(ActionEvent event) throws IOException {

        UserSession.cleanUserSession();
        if (mail.getText().isEmpty() || password.getText().isEmpty())
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                "Empty Fields");
        else if (us.displayByid(us.getUserIdByEmail(mail.getText())) != null) {
            if (us.authenticate(mail.getText(), password.getText()) != 0) {

            if (us.displayByid(us.getUserIdByEmail(mail.getText())).isBlocked()==0) {

                if (us.displayByid(us.getUserIdByEmail(mail.getText())).isVerified()==1) {

                    if(us.displayByid(us.getUserIdByEmail(mail.getText())).getSecret()==null){

                    UserSession u = UserSession.getInstance(mail.getText(), Integer.toString(us.getUserIdByEmail(mail.getText())));
                    if (us.role(us.authenticate(mail.getText(), password.getText())).equals("[\"ROLE_CLIENT\"]")) {
                        goToClient();
                    } else if (us.role(us.authenticate(mail.getText(), password.getText())).equals("[\"ROLE_ADMIN\"]")) {
                        goToHome();
                    }
                }
                    else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                "Please provide the secret key.");
                        show2fa();

                    }

            }

            else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "This account is not verified yet. Please check your email.");
                showconfirmation();
            }
            }
            else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "This account is blocked. Please contact the administrator.");
            }

            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Invalid email or password.");

            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "This email is not registered yet. Please sign up first.");

        }
    }

        @FXML
        void logingg(ActionEvent event) {
            try {
                initiateGoogleSignup();
            } catch (IOException e) {
                e.printStackTrace();
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Failed to initiate Google signup");
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

        String authorizationCode = extractAuthorizationCode(redirectUrl);

        if (authorizationCode != null) {
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
                if (us.exsitemail(email)) {
                    if (us.displayByid(us.getUserIdByEmail(email)).isBlocked()==0) {
                        System.out.println("User already exists");
                        UserSession.cleanUserSession();
                        UserSession u = UserSession.getInstance(email, Integer.toString(us.getUserIdByEmail(email)));
                        if (us.role(us.getUserIdByEmail(email)).equals("[\"ROLE_CLIENT\"]")) {
                            goToClient();
                            System.out.println("User is a client");
                        } else if (us.role(us.getUserIdByEmail(email)).equals("[\"ROLE_ADMIN\"]")) {
                            goToHome();
                            System.out.println("User is an admin");
                        }
                    }
                    else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                "This account is blocked. Please contact the administrator.");
                    }
                }
                } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "This email is not registered yet. Please sign up first.");
                }


        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error", "Failed to retrieve access token");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
    private String extractAuthorizationCode(String redirectUrl) {

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

    public void showconfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmEmail.fxml"));
            Parent root = loader.load();
            ConfirmEmailController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Confirm Email");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show2fa() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/2fa.fxml"));
            Parent root = loader.load();
            FAController controller = loader.getController();
            controller.email=mail.getText();
            Stage stage = new Stage();
            stage.setTitle("2FA");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (controller.validate){
                UserSession u = UserSession.getInstance(mail.getText(), Integer.toString(us.getUserIdByEmail(mail.getText())));
                if (us.role(us.authenticate(mail.getText(), password.getText())).equals("[\"ROLE_CLIENT\"]")) {
                    goToClient();
                } else if (us.role(us.authenticate(mail.getText(), password.getText())).equals("[\"ROLE_ADMIN\"]")) {
                    goToHome();
                }
            }
            else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                        "Invalid secret key.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }


