package services;

import Entity.Commande;
import Entity.Reclamation;
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
    String sql = " insert into Reclamation (libelle,contenu_rec,reponse,dateSinitre,dateReclamation,latitude,longitude,image_file,command_id) values('" + rec.getLibelle() + "','" + rec.getContenu_rec() + "','" + rec.getReponse() + "','" + rec.getDateSinitre() + "','" + rec.getDateReclamation() +  "','" +rec.getLatitude()+ "','" +rec.getLongitude()+ "','" +rec.getImage_file()+"','"+rec.getCommande().getId()+"')";
    try {
        ste = cnx.createStatement();
        ste.executeUpdate(sql);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
    @Override
    public void modifierReclamation(Reclamation reclamation) throws SQLException {
        String req = "UPDATE reclamation SET libelle=?, contenu_rec=?, dateSinitre=?,latitude=?,longitude =?,image_file=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, reclamation.getLibelle());
            pre.setString(2, reclamation.getContenu_rec());
            pre.setString(3, reclamation.getDateSinitre());
            pre.setString(4, reclamation.getLatitude());
            pre.setString(5, reclamation.getLongitude());
            pre.setString(6, reclamation.getImage_file());
            pre.setInt(7, reclamation.getId());
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
                rec.setLatitude(rs.getString("latitude"));
                rec.setLongitude(rs.getString("longitude"));

                rec.setImage_file(rs.getString("image_file"));
                reclamations.add(rec);
            }
        }
        return reclamations;
    }

    @Override
    public int selectId(Reclamation reclamation) throws SQLException {
        String req = "SELECT indemnisation_id FROM reclamation WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, reclamation.getId());
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("indemnisation_id");
                } else {
                    // Gérer le cas où aucun résultat n'est trouvé
                    return -1; // Ou une autre valeur par défaut appropriée
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public List<Reclamation> afficherReclamationsRefusees() throws SQLException {
        List<Reclamation> reclamationsRefusees = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE reponse = 'refused'";
        try (PreparedStatement pre = cnx.prepareStatement(req); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setContenu_rec(rs.getString("contenu_rec"));
                rec.setReponse(rs.getString("reponse"));
                rec.setDateSinitre(rs.getString("dateSinitre"));
                rec.setDateReclamation(rs.getString("dateReclamation"));
                rec.setLatitude(rs.getString("latitude"));
                rec.setLongitude(rs.getString("longitude"));
                rec.setImage_file(rs.getString("image_file"));
                reclamationsRefusees.add(rec);
            }
        }
        return reclamationsRefusees;
    }


    public List<Reclamation> afficherReclamationsAccepted() throws SQLException {
        List<Reclamation> reclamationsRefusees = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE reponse = 'accepted'";
        try (PreparedStatement pre = cnx.prepareStatement(req); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setContenu_rec(rs.getString("contenu_rec"));
                rec.setReponse(rs.getString("reponse"));
                rec.setDateSinitre(rs.getString("dateSinitre"));
                rec.setDateReclamation(rs.getString("dateReclamation"));
                rec.setLatitude(rs.getString("latitude"));
                rec.setLongitude(rs.getString("longitude"));
                rec.setImage_file(rs.getString("image_file"));

                reclamationsRefusees.add(rec);
            }
        }
        return reclamationsRefusees;
    }

    public List<Reclamation> afficherReclamationsEnCourDeTraitement() throws SQLException {
        List<Reclamation> reclamationsRefusees = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE reponse = 'Currently being processed'";
        try (PreparedStatement pre = cnx.prepareStatement(req); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setContenu_rec(rs.getString("contenu_rec"));
                rec.setReponse(rs.getString("reponse"));
                rec.setDateSinitre(rs.getString("dateSinitre"));
                rec.setDateReclamation(rs.getString("dateReclamation"));
                rec.setLatitude(rs.getString("latitude"));
                rec.setLongitude(rs.getString("longitude"));
                rec.setImage_file(rs.getString("image_file"));
                reclamationsRefusees.add(rec);
            }
        }
        return reclamationsRefusees;
    }
    @Override
    public double afficherUneCommande(int id_cmd) throws SQLException {
        double ins_value = 0.0; // Initialiser à 0 pour le cas où aucune valeur n'est trouvée
        String req = "SELECT insvalue FROM commande WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, id_cmd); // Utiliser le bon nom du paramètre
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    ins_value = rs.getDouble("insvalue"); // Récupérer la valeur 'insvalue'
                }
            }
        }
        return ins_value;
    }
    @Override
    public Commande afficher(int id) throws SQLException {
        Commande indemnisation = null; // Initialize to null in case no indemnisation is found
        String req = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    indemnisation = new Commande();
                    indemnisation.setId(rs.getInt("id"));

                    indemnisation.setIns_value(rs.getFloat("insvalue"));
                }
            }
        }
        return indemnisation;
    }
    @Override
    public int select_id_cmd(Reclamation reclamation) throws SQLException {
        String req = "SELECT command_id FROM reclamation WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, reclamation.getId());
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("command_id");
                } else {
                    // Gérer le cas où aucun résultat n'est trouvé
                    return -1; // Ou une autre valeur par défaut appropriée
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
