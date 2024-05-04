package Entities;

import java.util.ArrayList;
import java.util.Date;

public class Commande {
    private int id;

    private float ins_value;




    public Commande(int id, float ins_value) {
        this.id = id;

        this.ins_value = ins_value;
    }

    public Commande() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






    public float getIns_value() {
        return ins_value;
    }

    public void setIns_value(float ins_value) {
        this.ins_value = ins_value;
    }




    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                '}';
    }

}