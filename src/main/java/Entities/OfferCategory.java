package Entities;

public class OfferCategory {

    private int id;
    private String categorie_name;
    private String description_cat;
    private String catimg;


    public OfferCategory(int id, String categorie_name, String description_cat, String catimg) {
        this.id = id;
        this.categorie_name = categorie_name;
        this.description_cat = description_cat;
        this.catimg = catimg;
    }


    public OfferCategory(String categorie_name, String description_cat, String catimg) {
        this.categorie_name = categorie_name;
        this.description_cat = description_cat;
        this.catimg = catimg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie_name() {
        return categorie_name;
    }

    public void setCategorie_name(String categorie_name) {
        this.categorie_name = categorie_name;
    }

    public String getDescription_cat() {
        return description_cat;
    }

    public void setDescription_cat(String description_cat) {
        this.description_cat = description_cat;
    }

    public String getCatimg() {
        return catimg;
    }

    public void setCatimg(String catimg) {
        this.catimg = catimg;
    }

    @Override
    public String toString() {
        return

                " " + categorie_name + '\''

        ;
    }


}



