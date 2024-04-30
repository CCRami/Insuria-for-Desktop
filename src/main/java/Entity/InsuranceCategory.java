package Entity;

public class InsuranceCategory {
    private int id;
    private String name_cat_ins;
    private String desc_cat_ins;

    public InsuranceCategory(int id, String name_cat_ins, String desc_cat_ins) {
        this.id = id;
        this.name_cat_ins = name_cat_ins;
        this.desc_cat_ins = desc_cat_ins;
    }

    public InsuranceCategory(String name_cat_ins, String desc_cat_ins) {
        this.name_cat_ins = name_cat_ins;
        this.desc_cat_ins = desc_cat_ins;
    }

    public InsuranceCategory(int catinsIdValue) {
        this.id = catinsIdValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_cat_ins() {
        return name_cat_ins;
    }

    public void setName_cat_ins(String name_cat_ins) {
        this.name_cat_ins = name_cat_ins;
    }

    public String getDesc_cat_ins() {
        return desc_cat_ins;
    }

    public void setDesc_cat_ins(String desc_cat_ins) {
        this.desc_cat_ins = desc_cat_ins;
    }

    @Override
    public String toString() {
        return

                "" + name_cat_ins + " " + ' ';
    }

}
