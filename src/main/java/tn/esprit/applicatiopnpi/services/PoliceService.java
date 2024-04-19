package tn.esprit.applicatiopnpi.services;
import tn.esprit.applicatiopnpi.models.Police;
import tn.esprit.applicatiopnpi.models.Sinistre;
import tn.esprit.applicatiopnpi.utils.MyDatabase;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PoliceService implements Iservice<Police> {

    private Connection connection;

    public PoliceService() {
        connection = MyDatabase.getInstance().getConnection();
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
    public void modifier(Police p) {
        String req = "UPDATE police SET police_name = ?, description_police = ?, sinistre_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, p.getPoliceName());
            ps.setString(2, p.getDescriptionPolice());
            ps.setInt(3, p.getSinistre().getId());
            ps.setInt(4, p.getId());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Police updated successfully!");
        } catch (SQLException e) {
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
    public List<Police> getAll() {
        List<Police> list = new ArrayList<>();
        String sql = "SELECT p.*, s.sin_name FROM police p LEFT JOIN sinistre s ON p.sinistre_id = s.id";
        try (Statement ste = connection.createStatement();
             ResultSet res = ste.executeQuery(sql)) {
            while (res.next()) {
                System.out.println("Reading data from ResultSet");
                int id = res.getInt("id");
                String policeName = res.getString("police_name");
                String description = res.getString("description_police");
                String sinName = res.getString("sin_name");
                System.out.println("Data: " + id + ", " + policeName + ", " + description + ", " + sinName);

                Sinistre sinistre = new Sinistre();
                sinistre.setSin_name(sinName);

                list.add(new Police(id, policeName, description, sinistre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total police loaded: " + list.size());
        return list;
    }





}
