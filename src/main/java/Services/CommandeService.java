package Services;

import Entities.Commande;
import Entities.Insurance;
import Entities.User;
import util.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandeService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public CommandeService(){

        cnx= DataSource.getInstance().getConnection();
    }

    public void addCommande(Commande commande) {
        String sql = "INSERT INTO commande (montant, date_effet, date_exp, full_doa, doa_com_id, user_id, ins_value) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            float insValue = commande.getIns_value();
            float montant = insValue * 0.0075f; // Multiply insValue by 0.75

            // Now set the calculated montant in the prepared statement
            pst.setFloat(1, montant);
            LocalDate dateEffet = LocalDate.now();
            pst.setString(2, dateEffet.toString());
            LocalDate dateExp = dateEffet.plusYears(1);
            pst.setString(3, dateExp.toString());

            // Convert ArrayList<String> to JSON string
            String fullDoaJson = convertToJson(commande.getDoa_full());
            pst.setString(4, fullDoaJson);

            // Set doa_com_id, user_id, and ins_value
            pst.setInt(5, commande.getDoa_com_id().getId());
            pst.setInt(6, 3); // Assuming user_id is 1 for now
            pst.setFloat(7, insValue);

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding Commande", e);
        }
    }


    // Utility method to convert ArrayList<String> to JSON string
    private String convertToJson(ArrayList<String> dynamicFields) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (String field : dynamicFields) {
            // Split the label and value and format them into a JSON object
            String[] parts = field.split(":");
            if (parts.length == 2) {
                jsonBuilder.append("{\"").append(parts[0].trim()).append("\":\"").append(parts[1].trim()).append("\"},");
            }
        }
        // Remove the last comma and close the JSON array
        if (jsonBuilder.length() > 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // Assuming DoaComService and UserService are existing services to retrieve related objects
                InsuranceService doaComService = new InsuranceService();
                Insurance doaCom = doaComService.getInsuranceById(rs.getInt("doa_com_id"));
                // Assuming the "Commande" class has appropriate constructors and setters
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setMontant(rs.getFloat("montant"));
                commande.setDate_effet(rs.getDate("date_effet"));
                commande.setDate_exp(rs.getDate("date_exp"));
                // Retrieve and parse JSON string to ArrayList<String>
                String fullDoaJson = rs.getString("full_doa");
                ArrayList<String> fullDoa = parseJson(fullDoaJson);
                commande.setDoa_full(fullDoa);
                commande.setDoa_com_id(doaCom);
                commande.setUser_id(new User(rs.getInt("user_id")));
                commande.setIns_value(rs.getFloat("ins_value"));

                commandes.add(commande);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Commandes", e);
        }

        return commandes;
    }
    private static ArrayList<String> parseJson(String json) {
        ArrayList<String> dynamicFields = new ArrayList<>();
        try {
            // Check if the JSON string is not null or empty
            if (json != null && !json.isEmpty()) {
                // Remove brackets from the JSON string
                json = json.substring(1, json.length() - 1);
                // Split the string by commas and add each part to the ArrayList
                String[] parts = json.split(",");
                for (String part : parts) {
                    // Remove leading and trailing whitespaces and quotes from each part
                    String field = part.trim().replaceAll("\"", "");
                    dynamicFields.add(field);
                }
            }
        } catch (Exception e) {
            // Handle any potential exceptions during JSON parsing
            e.printStackTrace();
            // You might want to log the error or handle it according to your application's requirements
        }
        return dynamicFields;
    }

    public void deleteCommande(int commandeId) {
        String sql = "DELETE FROM commande WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, commandeId);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted == 0) {
                System.out.println("No Commande found with ID: " + commandeId);
            } else {
                System.out.println("Commande with ID " + commandeId + " deleted successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Commande", e);
        }
    }

}
