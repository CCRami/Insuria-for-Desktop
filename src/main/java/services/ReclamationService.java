package services;

import entity.Reclamation;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IServiceReclamation<Reclamation>{
    private Connection cnx;
    private Statement ste;

    public ReclamationService() {
        cnx = DataSource.getInstance().getConnection();
    }
@Override
    public void addReclamation(Reclamation rec)throws SQLException {
    String sql = " insert into Reclamation (libelle,contenu_rec,reponse,dateSinitre,dateReclamation) values('" + rec.getLibelle() + "','" + rec.getContenu_rec() + "','" + rec.getReponse() + "','" + rec.getDateSinitre() + "','" + rec.getDateReclamation() + "')";
    try {
        ste = cnx.createStatement();
        ste.executeUpdate(sql);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
    @Override
    public void modifierReclamation(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET libelle=?, contenu_rec=?, dateSinitre=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, reclamation.getLibelle());
            pre.setString(2, reclamation.getContenu_rec());
            pre.setString(3, reclamation.getDateSinitre());
            pre.setInt(4, reclamation.getId());
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Réclamation avec l'identifiant : " + reclamation.getId() + " mise à jour avec succès !");
            } else {
                System.out.println("Aucune réclamation trouvée avec l'identifiant : " + reclamation.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierReclamationReponse(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET reponse=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, reclamation.getReponse());

            pre.setInt(2, reclamation.getId());
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Réclamation avec l'identifiant : " + reclamation.getId() + " mise à jour avec succès !");
            } else {
                System.out.println("Aucune réclamation trouvée avec l'identifiant : " + reclamation.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void modifierReclamationIndemnisation(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET indemnisation_id =? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1,reclamation.getIndemnisation().getId());

            pre.setInt(2, reclamation.getId());
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Réclamation avec l'identifiant : " + reclamation.getId() + " mise à jour avec succès !");
            } else {
                System.out.println("Aucune réclamation trouvée avec l'identifiant : " + reclamation.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void supprimerReclamation(int idRec) throws SQLException {
        String req = "DELETE FROM reclamation WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1,idRec);
            int row=pre.executeUpdate();
            if (row > 0) {
                System.out.println("reclamation with id : " + idRec + " updated successfully!");
            } else {
                System.out.println("No reclamation found with id" + idRec );
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }


    }

    @Override
    public List<Reclamation> afficherReclamations() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM reclamation";
        try (PreparedStatement pre = cnx.prepareStatement(req); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setContenu_rec(rs.getString("contenu_rec"));
                rec.setReponse(rs.getString("reponse"));
                rec.setDateSinitre(rs.getString("dateSinitre"));
                rec.setDateReclamation(rs.getString("dateReclamation"));


                reclamations.add(rec);
            }
        }
        return reclamations;
    }



}