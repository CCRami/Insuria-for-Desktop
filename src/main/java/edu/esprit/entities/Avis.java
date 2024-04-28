package edu.esprit.entities;
import javafx.scene.control.TableColumn;
import edu.esprit.entities.Agence;
public class Avis {
    private int idAV;
    private Agence agenceav_id;
    private int avis_id;

    private  String commentaire;

    private int note;
    private boolean etat;
    private String date_avis;

   /* public Avis(String comment, int note, int i, Agence agence, String creation) {
    }

    */


    public int getIdAV() {
        return idAV;
    }

    public void setIdAV(int idAV) {
        this.idAV = idAV;
    }

    public Agence getAgenceav_id() {
        return agenceav_id;
    }

    public void setAgenceav_id(Agence agenceav_id) {
        this.agenceav_id = agenceav_id;
    }

    public int getAvis_id() {
        return avis_id;
    }

    public void setAvis_id(int avis_id) {
        this.avis_id = avis_id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public String getDate_avis() {
        return date_avis;
    }

    public void setDate_avis(String date_avis) {
        this.date_avis = date_avis;
    }

    public Avis (String commentaire, int note, String date_avis, int avis_id, Agence agenceav_id, boolean etat){
        this.idAV=idAV;
        this.commentaire=commentaire;
        this.note=note;
        this.date_avis=date_avis;
        this.avis_id=avis_id;
        this.agenceav_id=  agenceav_id;
        this.etat=etat;

    }

    public Avis (int idAV, String commentaire, int note, String date_avis, int avis_id, Object agenceav_id, boolean etat){
        this.idAV=idAV;
        this.commentaire=commentaire;
        this.note=note;
        this.date_avis=date_avis;
        this.avis_id=avis_id;
        this.agenceav_id= (Agence) agenceav_id;
        this.etat=etat;

    }




    public Avis (TableColumn<Avis, String> commentaire, TableColumn<Avis, Integer> note, TableColumn<Avis, String> date_avis, TableColumn<Avis, Integer> avis_id, TableColumn<Avis, Integer> agenceav_id, TableColumn<Avis, Boolean> etat){
        this.commentaire= this.commentaire;
        this.note= this.note;
        this.date_avis=this.date_avis;
        this.avis_id= this.avis_id;
        this.agenceav_id=this.agenceav_id;
        this.etat=this.etat;
    }


    @Override
    public String toString() {
        return "Avis{" +
                "idAV=" + idAV +
                ", agenceav_id=" + agenceav_id +
                ", avis_id=" + avis_id +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                ", etat=" + etat +
                ", date_avis='" + date_avis + '\'' +
                '}';
    }








}
