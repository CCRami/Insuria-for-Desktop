package Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entities.Agence;
import Entities.Avis;
import util.DataSource;

public class AvisService implements IServiceAvis {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;

    public AvisService() {
        cnx = DataSource.getInstance().getConnection();
    }

    private static  int STATIC_USER_ID = 111;
    private static boolean ETAT = false;
    public Agence agence;

    @Override
    public void ajouteravis(Avis avis) {
        System.out.println("bb"+avis.getCommentaire());
        System.out.println("bb"+avis);
        System.out.println("bb"+avis.getAgenceav_id().getIdage());

        String req = "INSERT INTO avis(commentaire, note, date_avis,avis_id,agenceav_id,etat) VALUES (?, ?, ?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, avis.getCommentaire());
            ps.setInt(2, avis.getNote());
            ps.setString(3, avis.getDate_avis());
            ps.setInt(4, STATIC_USER_ID);
            ps.setObject(5, avis.getAgenceav_id().getIdage());
            ps.setBoolean(6, ETAT);

            ps.executeUpdate();


            System.out.println("Avis added!");
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }




    /*@Override
    public void modifier(Object o) {
        if (o instanceof Av) {
            AvisRestau avis = (AvisRestau) o;
            String req = "UPDATE avisrestau SET  commentaire = ? WHERE idA = ? AND idR = ?";
            try (PreparedStatement ps = cnx.prepareStatement(req)) {

                ps.setString(1, avis.getCommentaire());
                ps.setInt(2, avis.getIdA());
                ps.setInt(3, avis.getIdR());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Avis with ID " + avis.getIdA() + " and Restaurant ID " + avis.getIdR() + " updated successfully!");
                } else {
                    System.out.println("No record found with ID " + avis.getIdA() + " and Restaurant ID " + avis.getIdR() + ". Update failed.");
                }
            } catch (SQLException e) {
                // Gérer ou journaliser l'exception
                e.printStackTrace();
            }
        }
    }
*/

    @Override
    public void supprimerav(int id) {
        System.out.println(id);
        String req = "DELETE FROM Avis WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {

                System.out.println("avis with ID " + id + " deleted successfully!");
            } else {
                System.out.println("No record found with ID " + id + ". Deletion failed.");
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

    }


   /* @Override
    public Object getOneById(int id) {
        return null;
    }*/

    AgenceService s = new AgenceService();
    public List<Avis> getAllavis() {
        List<Avis> list = new ArrayList<>();
        String sql = "select * from Avis ";
        try {
            ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt(1);
                String commentaire = res.getString(2);
                int note = res.getInt(3);
                String date_avis = res.getString(4);
                int avis_id = res.getInt(5);
                int agenceav_id = res.getInt(6);
                Agence agence = s.getOneById(agenceav_id);
                //int agenceav_id =res.getInt(6);
                boolean etat = res.getBoolean(7);
                list.add(new Avis(id, commentaire, note, date_avis, avis_id,  agence, etat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Avis> getAllavisbyuser(int userid) {
        List<Avis> list = new ArrayList<>();
        String sql = "select * from Avis WHERE avis_id=?";
        try {
            ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt(1);
                String commentaire = res.getString(2);
                int note = res.getInt(3);
                String date_avis = res.getString(4);
               // int avis_id = res.getInt(5);
                int agenceav_id = res.getInt(6);
                Agence agence = s.getOneById(agenceav_id);
                //int agenceav_id =res.getInt(6);
                boolean etat = res.getBoolean(7);
                list.add(new Avis(id, commentaire, note, date_avis, userid,  agence, etat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Avis> getAllavisbyagence(Agence Parametre) {
        System.out.println("ff" + Parametre);
        List<Avis> list = new ArrayList<>();

        String sql = "select * from Avis WHERE agenceav_id=?";

        try {//ste=cnx.createStatement();

            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, Parametre.getIdage());
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                int id = res.getInt(1);
                String commentaire = res.getString(2);
                int note = res.getInt(3);
                String date_avis = res.getString(4);
                int avis_id = res.getInt(5);
                Object agenceav_id =res.getObject(6);

                 //Parametre = res.get(6);
                boolean etat = res.getBoolean(7);
                list.add(new Avis(id, commentaire, note, date_avis, avis_id, Parametre, etat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list);
        return list;
    }

}


