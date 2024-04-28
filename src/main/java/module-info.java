module com.example.insuria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    exports org.example;
    exports controller;
    opens controller;
    opens entity;
}
