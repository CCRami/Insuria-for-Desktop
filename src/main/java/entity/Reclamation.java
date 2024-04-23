package entity;

public class Reclamation {

    private  int id;
    private String libelle;

    private String contenu_rec;
    private String reponse;
    private String dateSinitre;
    private String dateReclamation;
    private Indemnissation indemnisation;

    public Reclamation() {
    }



    public Reclamation(String libelle, String contenu_rec, String reponse, String dateSinitre, String dateReclamation) {
        this.libelle = libelle;
        this.contenu_rec = contenu_rec;
        this.reponse = reponse;
        this.dateSinitre = dateSinitre;
        this.dateReclamation = dateReclamation;
    }

    public String getDateSinitre() {
        return dateSinitre;
    }

    public void setDateSinitre(String dateSinitre) {
        this.dateSinitre = dateSinitre;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(String dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public String getContenu_rec() {
        return contenu_rec;
    }

    public void setContenu_rec(String contenu_rec) {
        this.contenu_rec = contenu_rec;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }


    public Indemnissation getIndemnisation() {
        return indemnisation;
    }

    public void setIndemnisation(Indemnissation indemnisation) {
        this.indemnisation = indemnisation;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "libelle='" + libelle + '\'' +
                ", contenu_rec='" + contenu_rec + '\'' +
                ", reponse='" + reponse + '\'' +
                ", dateSinitre='" + dateSinitre + '\'' +
                ", dateReclamation='" + dateReclamation + '\'' +
                '}';
    }
}