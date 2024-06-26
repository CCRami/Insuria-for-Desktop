package Entities;

import java.util.HashSet;

public final class UserSession {

    private static UserSession instance;

    public static String email;
    public static String id;

    private UserSession(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public static UserSession getInstance(String email, String id) {
        if(instance == null) {
            instance = new UserSession(email, id);
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public static void cleanUserSession() {
        email = "";// or null
        id = "";// or null
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}