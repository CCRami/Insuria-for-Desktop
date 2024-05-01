package Entities;

import java.util.HashSet;

public final class UserSession {

    private static UserSession instance;

    public static String email;
    private static String id;

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

    public String getEamil() {
        return email;
    }

    public String getId() {
        return id;
    }

    public static void cleanUserSession() {
        email = "";// or null
        id = "";// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}