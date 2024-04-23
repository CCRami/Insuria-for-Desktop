package org.example;

import entity.Indemnissation;
import entity.Reclamation;
import services.IndemnisationService;
import services.ReclamationService;
import util.DataSource;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Main {

    public static void main(String[] args) throws SQLException {
        DataSource conn1 = DataSource.getInstance();


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateRc = format.format(date);

        LocalDate date1 = LocalDate.of(2010, 3, 22);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateSinistre = date1.format(formatter);


        Reclamation rec1 = new Reclamation("hh", "kk", "Currently being processed", dateSinistre, dateRc);
        ReclamationService rs = new ReclamationService();
        try {
            rs.addReclamation(rec1);
        } catch (SQLException e) {
            System.out.println("Error adding packs: " + e.getMessage());
        }
        rs.afficherReclamations().forEach(System.out::println);
    }}