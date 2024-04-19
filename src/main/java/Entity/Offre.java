package Entity;

public class Offre {

    private int id_off;
    private String advantage;
    private String conditions;
    private String 	duration;
    private Double discount;
    private String offreimg;


    private OfferCategory categorie_id ;

    public Offre(int id_off, String advantage, String conditions, String duration, Double discount, String offreimg, OfferCategory categorie_id ) {
        this.id_off = id_off;
        this.advantage = advantage;
        this.conditions = conditions;
        this.duration = duration;
        this.discount = discount;
        this.offreimg = offreimg;
        this.categorie_id  = categorie_id ;
    }


    public Offre(String advantage, String conditions, String duration, Double discount, String offreimg, OfferCategory categorie_id ) {
        this.advantage = advantage;
        this.conditions = conditions;
        this.duration = duration;
        this.discount = discount;
        this.offreimg = offreimg;
        this.categorie_id  = categorie_id ;
    }


    public int getId_off() {
        return id_off;
    }

    public void setId_off(int id_off) {
        this.id_off = id_off;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getOffreimg() {
        return offreimg;
    }

    public void setOffreimg(String offreimg) {
        this.offreimg = offreimg;
    }

    public OfferCategory getId_catoff() {
        return categorie_id ;
    }

    public void setId_catoff(OfferCategory categorie_id ) {
        this.categorie_id  = categorie_id ;
    }


    @Override
    public String toString() {
        return "Offre{" +
                "id_off=" + id_off +
                ", advantage='" + advantage + '\'' +
                ", conditions='" + conditions + '\'' +
                ", duration='" + duration + '\'' +
                ", discount=" + discount +
                ", offreimg='" + offreimg + '\'' +
                ", categorie_id =" + categorie_id  +
                '}';
    }
}
