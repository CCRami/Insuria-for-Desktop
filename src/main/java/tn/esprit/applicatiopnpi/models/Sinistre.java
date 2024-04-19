package tn.esprit.applicatiopnpi.models;
import javax.persistence.*;
@Entity
@Table(name = "sinistre")
public class Sinistre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private String sin_name;
    private String description_sin;
    private String image_path;
    public Sinistre(){}

    public Sinistre(int id,String name, String description, String image){
        this.id = id;
        this.sin_name = name;
        this.description_sin = description;
        this.image_path = image;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSin_name() {
        return this.sin_name;
    }
    public void setSin_name(String sin_name) {
        this.sin_name = sin_name;
    }
    public String getDescription_sin() {
        return this.description_sin;
    }
    public void setDescription_sin(String description_sin) {
        this.description_sin = description_sin;
    }
    public String getImage_path() {
        return this.image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
   @Override
    public String toString() {
        return "Sinistre{id=" + this.id + ", sin_name='" + this.sin_name + "', description_sin='" + this.description_sin + "', image_path='" + this.image_path + "}";
    }
}
