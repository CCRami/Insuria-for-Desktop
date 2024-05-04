package edu.esprit.tests;

import edu.esprit.entities.Agence;
import edu.esprit.service.AgenceService;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {

        Date create_at = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(create_at);
        //Agence A1=new Agence("nomage", "addresse", "email", 22222222, formattedDate);
        AgenceService as=new AgenceService();
        //as.add(A1);

        as.getAllage().forEach(System.out::println);


      //DataSource ds1=DataSource.getInstance();
    //    System.out.println(ds1);
    }
}