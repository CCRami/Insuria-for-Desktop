module com.example.insuria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires javafx.web;
    requires org.apache.commons.codec;
    requires org.apache.commons.lang3;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.http.client.jackson2;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires org.controlsfx.controls;
    requires java.datatransfer;
    requires jbcrypt;
    requires totp;
    requires java.desktop;
    requires java.persistence;
    requires itextpdf;
    requires restfb;
    exports org.example;
    exports Controllers;
    opens Controllers;
    opens Entities;
    exports Controllers.User;
    opens Controllers.User;

}
