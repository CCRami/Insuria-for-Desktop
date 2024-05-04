package org.example;

import Entities.OfferCategory;
import Services.OffreCatService;
import util.DataSource;

public class Main {

    public static void main(String[] args) {

        DataSource ds1=DataSource.getInstance();
        System.out.println(ds1);

        OfferCategory offcat=new OfferCategory("Auto2","Automobile Cars2","t");

        OffreCatService offcats=new OffreCatService();
        offcats.AddCatOff(offcat);
        offcats.getAll().forEach(System.out::println);
    }
}
