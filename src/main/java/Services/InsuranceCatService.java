package Services;

import Entities.InsuranceCategory;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceCatService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public InsuranceCatService(){
        cnx= DataSource.getInstance().getCnx();
    }

    public void AddCatIns(InsuranceCategory ci){
        String sql="insert into categorie_assurance (name_cat_ins,desc_cat_ins) value (?,?)";
        try {
            pst=cnx.prepareStatement(sql);
            pst.setString(1,ci.getName_cat_ins());
            pst.setString(2,ci.getDesc_cat_ins());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCatIns(InsuranceCategory ci) {
        String sql = "update categorie_assurance set name_cat_ins = ?, desc_cat_ins = ? where id = ?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, ci.getName_cat_ins());
            pst.setString(2, ci.getDesc_cat_ins());
            pst.setInt(3, ci.getId()); // Assuming getId() returns the ID of the category to be updated
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCatIns(int categoryId) {
        String sql = "delete from categorie_assurance where id = ?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, categoryId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<InsuranceCategory> getAll(){
        List<InsuranceCategory> listinscat=new ArrayList<>();
        String sql="select * from categorie_assurance";
        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while(rs.next()){
                listinscat.add(new InsuranceCategory(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listinscat;
    }

    public InsuranceCategory getInsuranceCategoryById(int catinsId) {
        String sql = "SELECT * FROM categorie_assurance WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, catinsId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Assuming InsuranceCategory has a constructor that takes relevant parameters
                return new InsuranceCategory(rs.getInt(1),rs.getString(2),rs.getString(3)) ;
            } else {
                // Handle case when no category with the given ID is found
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching InsuranceCategory by ID", e);
        }
    }

}
