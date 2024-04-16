package org.example;

import util.DataSource;
import Entities.User;
import Services.UserService;

public class Main {

    public static void main(String[] args) {

        User u1= new User("Tabib", "Rami", "ramitoubib@hotmail.com", "toubibrami10@", 20496381, "2014-09-09", "[\"ROLE_CLIENT\"]");
        UserService us=new UserService();
        us.add(u1);
    }
}