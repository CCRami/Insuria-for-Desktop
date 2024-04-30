package Entity;

import java.util.ArrayList;

public class Insurance {
    private int id;
    private String name_ins;
    private float montant;
    private String ins_image;
    private ArrayList<String> doa;
    private InsuranceCategory cat_ins_id;
    private police pol_id;

    public Insurance(int id, String name_ins, float montant, String ins_image, ArrayList<String> doa, InsuranceCategory cat_ins_id, police pol_id) {
        this.id = id;
        this.name_ins = name_ins;
        this.montant = montant;
        this.ins_image = ins_image;
        this.doa = doa;
        this.cat_ins_id = cat_ins_id;
        this.pol_id = pol_id;
    }

    public Insurance(String name_ins, float montant, String ins_image, ArrayList<String> doa, InsuranceCategory cat_ins_id, police pol_id) {
        this.name_ins = name_ins;
        this.montant = montant;
        this.ins_image = ins_image;
        this.doa = doa;
        this.cat_ins_id = cat_ins_id;
        this.pol_id = pol_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_ins() {
        return name_ins;
    }

    public void setName_ins(String name_ins) {
        this.name_ins = name_ins;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getIns_image() {
        return ins_image;
    }

    public void setIns_image(String ins_image) {
        this.ins_image = ins_image;
    }

    public ArrayList<String> getDoa() {
        return doa;
    }

    public void setDoa(ArrayList<String> doa) {
        this.doa = doa;
    }

    public InsuranceCategory getCatins_id() {
        return cat_ins_id;
    }

    public void setCatins_id(InsuranceCategory cat_ins_id) {
        this.cat_ins_id = cat_ins_id;
    }

    public police getPol_id() {
        return pol_id;
    }

    public void setPol_id(police pol_id) {
        this.pol_id = pol_id;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", name_ins='" + name_ins + '\'' +
                ", montant=" + montant +
                ", ins_image='" + ins_image + '\'' +
                ", doa=" + doa +
                ", catins_id=" + cat_ins_id +
                ", pol_id=" + pol_id +
                '}';
    }
}
