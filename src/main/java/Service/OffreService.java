package Service;

import Entity.OfferCategory;
import Entity.Offre;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public OffreService(){
        cnx= DataSource.getInstance().getConnection();
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
        String sql = "UPDATE offre SET advantage=?, conditions=?, duration=?, discount=?, offreimg=?, categorie_id=? WHERE offre_id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, of.getAdvantage());
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

    public void deleteOffre(int offreId) {
        String sql = "DELETE FROM offre WHERE offre_id=?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, offreId);
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
                OfferCategory cat = OffreCatService.getOfferCategoryById(rs.getInt("catofId"));


                listoff.add(new Offre(rs.getInt("id_off"), rs.getString("advantage"), rs.getString("conditions"), rs.getString("duration"),rs.getDouble("discount"),rs.getString("offreimg"), cat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listoff;
    }
}

