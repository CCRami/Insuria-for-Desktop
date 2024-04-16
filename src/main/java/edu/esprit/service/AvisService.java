package edu.esprit.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.entities.Avis;
import edu.esprit.util.DataSource;

public class AvisService implements IServiceAvis {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public AvisService(){
        cnx = DataSource.getInstance().getConnection();
    }

    private static final int STATIC_USER_ID = 2;
    private static  boolean ETAT = false;
    @Override
    public void ajouteravis(Avis avis) {


            String req = "INSERT INTO avis(commentaire, note, date_avis,avis_id,agenceav_id,etat) VALUES (?, ?, ?,?,?,?)";
            try (PreparedStatement ps = cnx.prepareStatement(req)) {
                ps.setString(1, avis.getCommentaire());
                ps.setInt(2, avis.getNote());
                ps.setString(3, avis.getDate_avis());
                ps.setInt(4, STATIC_USER_ID);
                ps.setInt(5, avis.getAgenceav_id());
                ps.setBoolean(5,ETAT);

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
        System.out.println( id );
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


   public List<Avis> getAllavis() {
       List<Avis> list=new ArrayList<>();
       String sql="select * from Avis ";
       try{ste=cnx.createStatement();
           ResultSet res=ste.executeQuery(sql);
           while (res.next()){
               int id = res.getInt(1);
               String commentaire = res.getString(2);
               int note = res.getInt(3);
               String date_avis = res.getString(4);
               int avis_id= res.getInt(5);

               int agenceav_id = res.getInt(6);
                boolean etat= res.getBoolean(7);
               list.add(new Avis(id,commentaire, note, date_avis, avis_id ,agenceav_id,etat));
           }
       }catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return list;
    }

    public List<Avis> getAllavisbyagence(int Parametre) {
        System.out.println("ff"+ Parametre );
        List<Avis> list=new ArrayList<>();

        String sql="select * from Avis WHERE agenceav_id=?";

        try{//ste=cnx.createStatement();

            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, Parametre);
            ResultSet res = pst.executeQuery();
            while (res.next()){
                int id = res.getInt(1);
                String commentaire = res.getString(2);
                int note = res.getInt(3);
                String date_avis = res.getString(4);
                int avis_id= res.getInt(5);

                int agenceav_id = res.getInt(6);
                boolean etat= res.getBoolean(7);
                list.add(new Avis(id,commentaire, note, date_avis, avis_id ,agenceav_id,etat));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println( list );return list;
    }
/*public List<Avis> getAllAvis() {
        List<Avis> list = new ArrayList<>();
        String sql = "SELECT A.*, C.nom AS nom_client, AG.nom AS nom_agence " +
                "FROM Avis A " +
                "INNER JOIN Client C ON A.client_id = C.id " +
                "INNER JOIN Agence AG ON A.agence_id = AG.id";
        try {
            ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt("id");
                String commentaire = res.getString("commentaire");
                int note = res.getInt("note");
                String dateAvis = res.getString("date_avis");
                int avisId = res.getInt("avis_id");
                int agenceId = res.getInt("agence_id");
                String nomAgence = res.getString("nom_agence");
                int clientId = res.getInt("client_id");
                String nomClient = res.getString("nom_client");
                boolean etat = res.getBoolean("etat");

                list.add(new Avis(id, commentaire, note, dateAvis, avisId, agenceId, nomAgence, clientId, nomClient, etat));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }*/

   /* public List<AvisRestau> getAllByRestaurant(int idR) {
        List<AvisRestau> avisList = new ArrayList<>();
        String req = "SELECT * FROM AvisRestau WHERE idR = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, idR);

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    int idA = res.getInt("idA");
                    String commentaire = res.getString("commentaire");

                    AvisRestau avis = new AvisRestau(idA, idR, commentaire);
                    avisList.add(avis);
                }
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return avisList;
    }*/
}
