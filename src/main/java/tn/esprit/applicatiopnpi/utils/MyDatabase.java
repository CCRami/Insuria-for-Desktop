package tn.esprit.applicatiopnpi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
    private final String url="jdbc:mysql://localhost:3306/insuria";
    private final String login="root";
    private final String pwd="";
    private Connection connection;




    private MyDatabase() {
        try {
            connection= DriverManager.getConnection(url,login,pwd);
            System.out.println("connected successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MyDatabase getInstance() {

        if(instance==null)
            instance=new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
