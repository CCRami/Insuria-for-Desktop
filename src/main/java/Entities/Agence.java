package Entities;

import javafx.scene.control.TableColumn;

public class Agence {
    private int idage ;
    private String nomage;
    private String addresse;
    private String email;
    private int tel;
    private String create_at;

    public Agence (String nomage, String addresse, String email, int tel, String create_at){
        this.idage=idage;
        this.nomage=nomage;
        this.addresse=addresse;
        this.email=email;
        this.tel=tel;
        this.create_at=create_at;
    }

    public Agence (TableColumn<Agence, String> nomage, TableColumn<Agence, String> addresse, TableColumn<Agence, String> emailage, TableColumn<Agence, Integer> tel, TableColumn<Agence, String> formattedDate){
        this.nomage= this.nomage;
        this.addresse= this.addresse;
        this.email=email;
        this.tel= this.tel;
        this.create_at=create_at;
    }
    public int getIdage() {
        return idage;
    }
    public String getNomage() {
        return nomage;
    }

    public void setIdage(int id) {
        this.idage = id;
    }

    public void setNomage(String nomage) {
        this.nomage = nomage;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getAddresse() {
        return addresse;
    }

    public String getEmail() {
        return email;
    }

    public int getTel() {
        return tel;
    }

    public String getCreate_at() {
        return create_at;
    }

    @Override
    public String toString() {
        return "Agence{" +
                "id=" + idage +
                ", nomage='" + nomage + '\'' +
                ", addresse='" + addresse + '\'' +
                ", email='" + email + '\'' +
                ", tel=" + tel +
                ", create_at=" + create_at +
                '}';
    }


}
