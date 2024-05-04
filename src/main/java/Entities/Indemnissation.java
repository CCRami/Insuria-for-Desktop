package Entities;

public class Indemnissation {
    private int id;
    private float montant;
    private String date;
private String beneficitaire;

    public Indemnissation(float montant, String date) {
        this.montant = montant;
        this.date = date;
    }

    public Indemnissation(float montant, String date, String beneficitaire) {
        this.montant = montant;
        this.date = date;
        this.beneficitaire = beneficitaire;
    }

    public Indemnissation() {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBeneficitaire() {
        return beneficitaire;
    }

    public void setBeneficitaire(String beneficitaire) {
        this.beneficitaire = beneficitaire;
    }

    @Override
    public String toString() {
        return "Indemnisation{" +
                "id=" + id +
                ", montant=" + montant +
                ", date='" + date + '\'' +
                ", beneficitaire='" + beneficitaire + '\'' +
                '}';
    }
}
