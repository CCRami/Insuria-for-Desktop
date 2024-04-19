package Service;

import Entity.OfferCategory;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreCatService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public OffreCatService(){
        cnx= DataSource.getInstance().getConnection();
    }

    public void AddCatOff(OfferCategory co){
        String sql="insert into categorie_offre (categorie_name,description_cat,catimg) value (?,?,?)";
        try {
            pst=cnx.prepareStatement(sql);
            pst.setString(1,co.getCategorie_name());
            pst.setString(2, co.getDescription_cat());
            pst.setString(3, co.getCatimg());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateCatOff(OfferCategory co){
        String sql = "update categorie_offre set categorie_name=?, description_cat=?, catimg=? where id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, co.getCategorie_name());
            pst.setString(2, co.getDescription_cat());
            pst.setString(3, co.getCatimg());
            pst.setInt(4, co.getId()); // Assuming id is the primary key
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DeleteCatOff(int categoryId){
        String sql = "delete from categorie_offre where id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, categoryId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<OfferCategory> getAll(){
        List<OfferCategory> listoffcat=new ArrayList<>();
        String sql="select * from categorie_offre";
        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while(rs.next()){
                listoffcat.add(new OfferCategory(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listoffcat;
    }

    public OfferCategory getOfferCategoryById(int catofId) {
        OfferCategory offerCategory = null;
        String sql = "SELECT * FROM offre_category WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, catofId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Using constructor with all fields
                offerCategory = new OfferCategory(
                        rs.getInt("id"),
                        rs.getString("categorie_name"),
                        rs.getString("description_cat"),
                        rs.getString("catimg")
                );


            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            // Handle exceptions here
            e.printStackTrace();
        }
        return offerCategory;
    }



}
