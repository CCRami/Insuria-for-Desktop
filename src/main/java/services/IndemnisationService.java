package services;


import Entity.Indemnissation;
import Entity.Reclamation;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndemnisationService implements IServiceIndemnisation<Reclamation, Indemnissation> {
    private Connection cnx;
    private Statement ste;

    public IndemnisationService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public int addIndemnisation(Indemnissation indemnisation) throws SQLException {
        String sql = "INSERT INTO indemnissation (montant, date, beneficitaire) VALUES (?, ?, ?)";

        try (PreparedStatement pre = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pre.setFloat(1, indemnisation.getMontant());
            pre.setString(2, indemnisation.getDate());
            pre.setString(3, indemnisation.getBeneficitaire());

            int rowsAffected = pre.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pre.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Récupère l'ID généré pour l'indemnisation
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1; // Retourne -1 si aucun ID n'a été généré (erreur)
    }


    @Override
    public void modifierIndemnisation(Indemnissation indemnisation) throws SQLException {
        String sql = "UPDATE indemnissation SET montant = ?, date = ?, beneficitaire = ? WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(sql)) {
            pre.setFloat(1, indemnisation.getMontant());
            pre.setString(2, indemnisation.getDate());
            pre.setString(3, indemnisation.getBeneficitaire());
            pre.setInt(4, indemnisation.getId());

            int rowsAffected = pre.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Indemnisation modifiée avec succès.");
            } else {
                System.out.println("Échec de la modification de l'indemnisation. Aucune indemnisation trouvée avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'indemnisation : " + e.getMessage());
            throw e;
        }
    }


    @Override
    public void supprimerIndemnisation(int idIndemnisation) throws SQLException {
        try {
            // Mettre à jour la réclamation qui a une indemnisation avec l'ID spécifique
            String miseAJourReclamationSql = "UPDATE reclamation SET indemnisation = NULL WHERE idIndemnisation = ?";
            try (PreparedStatement miseAJourReclamationStatement = cnx.prepareStatement(miseAJourReclamationSql)) {
                miseAJourReclamationStatement.setInt(1, idIndemnisation);
                int rowsAffected = miseAJourReclamationStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Réclamation mise à jour avec succès.");
                } else {
                    System.out.println("Aucune réclamation trouvée avec une indemnisation ayant l'ID spécifié.");
                }
            }

            // Supprimez l'indemnisation de la base de données
            String suppressionIndemnisationSql = "DELETE FROM indemnissation WHERE id = ?";
            try (PreparedStatement suppressionIndemnisationStatement = cnx.prepareStatement(suppressionIndemnisationSql)) {
                suppressionIndemnisationStatement.setInt(1, idIndemnisation);
                int rowsAffected = suppressionIndemnisationStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Indemnisation supprimée avec succès.");
                } else {
                    System.out.println("Aucune indemnisation trouvée avec l'ID spécifié.");
                }
            }
        } catch (SQLException e) {
            // Gestion des exceptions
            e.printStackTrace();
        }
    }


    @Override
    public List<Indemnissation> afficherIndemnisation() throws SQLException {
        List<Indemnissation> indemnisations = new ArrayList<>();
        String req = "SELECT * FROM indemnissation";
        try (PreparedStatement pre = cnx.prepareStatement(req); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Indemnissation indemnisation = new Indemnissation();
                indemnisation.setId(rs.getInt("id"));
                indemnisation.setMontant(rs.getFloat("montant"));
                indemnisation.setDate(rs.getString("date"));
                indemnisation.setBeneficitaire(rs.getString("beneficitaire"));


                indemnisations.add(indemnisation);
            }
        }
        return indemnisations;
    }


    @Override

    public Indemnissation afficherUneIndemnisation(int id) throws SQLException {
        Indemnissation indemnisation = null; // Initialiser à null pour le cas où aucune indemnisation n'est trouvée
        String req = "SELECT * FROM indemnissation WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    indemnisation = new Indemnissation();
                    indemnisation.setId(rs.getInt("id"));
                    indemnisation.setMontant(rs.getFloat("montant"));
                    indemnisation.setDate(rs.getString("date"));
                    indemnisation.setBeneficitaire(rs.getString("beneficitaire"));
                }
            }
        }
        return indemnisation;
    }
}