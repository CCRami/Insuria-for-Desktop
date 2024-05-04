package Services;

import Entities.OfferCategory;
import Entities.Offre;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public OffreService(){
        cnx= DataSource.getInstance().getCnx();
    }

    public void AddOff(Offre of){
        String sql="insert into offre (advantage,conditions,duration,discount,offreimg,categorie_id ) value (?,?,?,?,?,?)";
        try {
            pst=cnx.prepareStatement(sql);
            pst.setString(1,of.getAdvantage());
            pst.setString(2, of.getConditions());
            pst.setString(3, of.getDuration());
            pst.setDouble(4, of.getDiscount());
            pst.setString(5, of.getOffreimg());
            pst.setInt(6, of.getId_catoff().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateOffre(Offre of) {
        String sql = "UPDATE offre SET advantage=?, conditions=?, duration=?, discount=?, offreimg=?, categorie_id=? WHERE id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, of.getAdvantage());
            pst.setString(2, of.getConditions());
            pst.setString(3, of.getDuration());
            pst.setDouble(4, of.getDiscount());
            pst.setString(5, of.getOffreimg());
            pst.setInt(6, of.getId_catoff().getId());
            pst.setInt(7,of.getId_off());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOffre(int Id_off) {
        String sql = "DELETE FROM offre WHERE id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, Id_off);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<Offre> getAll(){
        List<Offre> listoff=new ArrayList<>();
        String sql="select * from offre";
        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while (rs.next()) {
                OffreCatService OffreCatService = new OffreCatService();
                OfferCategory cat = OffreCatService.getOfferCategoryById(rs.getInt("categorie_id")); // Update column name here


                listoff.add(new Offre(rs.getInt("id"), rs.getString("advantage"), rs.getString("conditions"), rs.getString("duration"),rs.getDouble("discount"),rs.getString("offreimg"), cat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listoff;
    }

    public List<Offre> getOffersByCategoryId(int categoryId){
        List<Offre> listoff=new ArrayList<>();
        String sql="select * from offre where categorie_id = ?";
        try {
            pst=cnx.prepareStatement(sql);
            pst.setInt(1, categoryId);
            ResultSet rs=pst.executeQuery();
            while (rs.next()) {
                OffreCatService OffreCatService = new OffreCatService();
                OfferCategory cat = OffreCatService.getOfferCategoryById(rs.getInt("categorie_id"));

                listoff.add(new Offre(rs.getInt("id"), rs.getString("advantage"), rs.getString("conditions"), rs.getString("duration"),rs.getDouble("discount"),rs.getString("offreimg"), cat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listoff;
    }

}

