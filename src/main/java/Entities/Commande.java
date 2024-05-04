package Entities;

import java.util.ArrayList;
import java.util.Date;

public class Commande {
    private int id;
    private float montant;
    private Date date_effet;
    private Date date_exp;
    private ArrayList doa_full;
    private Insurance doa_com_id;
    private User user_id;

    private float ins_value;


    public Commande(float montant, Date date_effet, Date date_exp, ArrayList doa_full, Insurance doa_com_id, User user_id, float ins_value) {
        this.montant = montant;
        this.date_effet = date_effet;
        this.date_exp = date_exp;
        this.doa_full = doa_full;
        this.doa_com_id = doa_com_id;
        this.user_id = user_id;
        this.ins_value = ins_value;
    }

    public Commande(int id, float montant, Date date_effet, Date date_exp, ArrayList doa_full, Insurance doa_com_id, User user_id, float ins_value) {
        this.id = id;
        this.montant = montant;
        this.date_effet = date_effet;
        this.date_exp = date_exp;
        this.doa_full = doa_full;
        this.doa_com_id = doa_com_id;
        this.user_id = user_id;
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

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDate_effet() {
        return date_effet;
    }

    public void setDate_effet(Date date_effet) {
        this.date_effet = date_effet;
    }

    public Date getDate_exp() {
        return date_exp;
    }

    public void setDate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }

    public ArrayList getDoa_full() {
        return doa_full;
    }

    public void setDoa_full(ArrayList doa_full) {
        this.doa_full = doa_full;
    }

    public Insurance getDoa_com_id() {
        return doa_com_id;
    }

    public void setDoa_com_id(Insurance doa_com_id) {
        this.doa_com_id = doa_com_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
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
