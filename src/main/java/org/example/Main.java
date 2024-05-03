package org.example;

import util.DataSource;

import java.sql.SQLException;
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



    }}