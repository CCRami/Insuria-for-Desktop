package tn.esprit.applicatiopnpi.models;

import javax.persistence.*;

@Entity
@Table(name = "police")
public class Police {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String policeName;
    private String descriptionPolice;

    // Relation ManyToOne avec Sinistre
    @ManyToOne
    @JoinColumn(name = "sinistre_id") // nom de la colonne dans la base de données
    private Sinistre sinistre;

    public Police() {}
    public Police(int id, String policeName, String descriptionPolice, Sinistre sinistre) {
        this.id = id;
        this.policeName = policeName;
        this.descriptionPolice = descriptionPolice;
        this.sinistre = sinistre;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getDescriptionPolice() {
        return descriptionPolice;
    }

    public void setDescriptionPolice(String descriptionPolice) {
        this.descriptionPolice = descriptionPolice;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }

    @Override
    public String toString() {
        return "Police{" +
                "id=" + id +
                ", policeName='" + policeName + '\'' +
                ", descriptionPolice='" + descriptionPolice + '\'' +
                ", sinistre=" + (sinistre != null ? sinistre.getSin_name() : "null") +
                '}';
    }
}
