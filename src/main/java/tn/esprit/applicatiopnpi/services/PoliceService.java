package tn.esprit.applicatiopnpi.services;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.utils.MyDatabase;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PoliceService implements Iservice<Police> {

    private Connection connection;
    private EntityManager entityManager;


    public PoliceService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    public PoliceService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void add(Police p) {
        String req = "INSERT INTO police(police_name, description_police, sinistre_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, p.getPoliceName());
            ps.setString(2, p.getDescriptionPolice());
            ps.setInt(3, p.getSinistre().getId()); // Assuming that Sinistre ID is set

            ps.executeUpdate();
            System.out.println("Police added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ajouter(Police police) {}

    @Override
    public void modifier(Police police) {

    }

    public void moddifier(Police p) {
        String req = "UPDATE Police SET police_name = ?, description_police = ?, sinistre_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, p.getPoliceName());
            ps.setString(2, p.getDescriptionPolice());
            ps.setInt(3, p.getSinistre().getId());
            ps.setInt(4, p.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Police updated successfully, " + rowsAffected + " row(s) affected.");
            } else {
                System.out.println("No rows affected, check if the ID exists.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM police WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Police deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  List<Police> getAll() {
        List<Police> list = new ArrayList<>();
        String sql = "SELECT p.*, s.sin_name , s.description_sin FROM police p LEFT JOIN sinistre s ON p.sinistre_id = s.id";
        try (Statement ste = connection.createStatement();
             ResultSet res = ste.executeQuery(sql)) {
            while (res.next()) {
                System.out.println("Reading data from ResultSet");
                int id = res.getInt("id");
                String policeName = res.getString("police_name");
                String description = res.getString("description_police");
                String sinName = res.getString("sin_name");
                String sindescription = res.getString("description_sin");
                System.out.println("Data: " + id + ", " + policeName + ", " + description + ", " + sinName);

                Sinistre sinistre = new Sinistre();
                sinistre.setSin_name(sinName);
                sinistre.setDescription_sin(sindescription);

                list.add(new Police(id, policeName, description, sinistre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total police loaded: " + list.size());
        return list;
    }
    /**
     * Fetches all policies associated with a given sinistre ID.
     * @param sinistreId The ID of the sinistre to filter the policies by.
     * @return A list of Police objects associated with the sinistre.
     */
    public List<Police> getPoliciesBySinistre(int sinistreId) {
        List<Police> policies = new ArrayList<>();
        String query = "SELECT p.*, s.sin_name, s.description_sin FROM police p LEFT JOIN sinistre s ON p.sinistre_id = s.id WHERE p.sinistre_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sinistreId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String policeName = rs.getString("police_name");
                String description = rs.getString("description_police");
                String sinName = rs.getString("sin_name");
                String sindescription = rs.getString("description_sin");

                Sinistre sinistre = new Sinistre();
                sinistre.setSin_name(sinName);
                sinistre.setDescription_sin(sindescription);

                Police police = new Police(id, policeName, description, sinistre);
                policies.add(police);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }

        return policies;
    }





    public List<Object[]> countPolicesBySinistre() {
        // Check if connection is not initialized
        if (this.connection == null) {
            throw new IllegalStateException("Database connection has not been initialized");
        }

        List<Object[]> results = new ArrayList<>();
        String sql = "SELECT s.sin_name, COUNT(p.id) as count FROM police p " +
                "INNER JOIN sinistre s ON p.sinistre_id = s.id " +
                "GROUP BY s.sin_name";

        try (Statement stmt = this.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String sinistreName = rs.getString("sin_name");
                int count = rs.getInt("count");
                results.add(new Object[]{sinistreName, count});
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }







}
