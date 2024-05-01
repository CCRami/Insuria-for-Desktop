package entity;

public class Reclamation {

    private  int id;
    private String libelle;

    private String contenu_rec;
    private String reponse;
    private String dateSinitre;
    private String dateReclamation;
    private Indemnissation indemnisation;
    private  String latitude ;
    private String longitude;
    private String image_file ;

    public Reclamation() {
    }

    public Reclamation(String libelle, String contenu_rec, String reponse, String dateSinitre, String dateReclamation,  String latitude, String longitude) {
        this.libelle = libelle;
        this.contenu_rec = contenu_rec;
        this.reponse = reponse;
        this.dateSinitre = dateSinitre;
        this.dateReclamation = dateReclamation;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Reclamation(String libelle, String contenu_rec, String reponse, String dateSinitre, String dateReclamation, String latitude, String longitude, String image_file) {
        this.libelle = libelle;
        this.contenu_rec = contenu_rec;
        this.reponse = reponse;
        this.dateSinitre = dateSinitre;
        this.dateReclamation = dateReclamation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image_file = image_file;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getImage_file() {
        return image_file;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", contenu_rec='" + contenu_rec + '\'' +
                ", reponse='" + reponse + '\'' +
                ", dateSinitre='" + dateSinitre + '\'' +
                ", dateReclamation='" + dateReclamation + '\'' +
                ", indemnisation=" + indemnisation +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}