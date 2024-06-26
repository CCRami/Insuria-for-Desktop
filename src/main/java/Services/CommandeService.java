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

        cnx= DataSource.getInstance().getCnx();
    }

    public void addCommande(Commande commande, int userId) {
        String sql = "INSERT INTO commande (montant, date_effet, date_exp, full_doa, doa_com_id, user_id, ins_value, is_checked) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            float insValue = commande.getIns_value();
            float montant = insValue * 0.0075f; // Multiply insValue by 0.0075 to get montant

            // Set parameters for the prepared statement
            pst.setFloat(1, montant);
            LocalDate dateEffet = LocalDate.now();
            pst.setString(2, dateEffet.toString());
            LocalDate dateExp = dateEffet.plusYears(1);
            pst.setString(3, dateExp.toString());

            // Convert ArrayList<String> to JSON string
            String fullDoaJson = convertToJson(commande.getDoa_full());
            pst.setString(4, fullDoaJson);

            pst.setInt(5, commande.getDoa_com_id().getId());
            pst.setInt(6, userId);
            pst.setFloat(7, insValue);
            pst.setBoolean(8, false);

            int rowsAffected = pst.executeUpdate();

            // Retrieve and set the generated ID to the Commande object
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    commande.setId(generatedId);
                    System.out.println("Inserted Commande with ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding Commande", e);
        }
    }

    // In CommandeService.java

    public void updateCommande(Commande commande, int id) {
        String updateSql = "UPDATE commande SET montant = ?, ins_value = ?, full_doa = ? WHERE id = ?";
        try (PreparedStatement updatePst = cnx.prepareStatement(updateSql)) {
            float insValue = commande.getIns_value();
            float montant = insValue * 0.0075f;
            updatePst.setFloat(1, montant);
            updatePst.setFloat(2, commande.getIns_value());

            String fullDoaJson = convertToJson(commande.getDoa_full());
            System.out.println("Converted fullDoaJson: " + fullDoaJson); // Debug print
            updatePst.setString(3, fullDoaJson);

            updatePst.setInt(4, id);

            int rowsAffected = updatePst.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No rows affected. Commande with ID " + id + " not found.");
            } else {
                System.out.println("Commande updated in the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Commande", e);
        }
    }



    // Updated convertToJson method to handle multiple fields correctly
    // Refined convertToJson method to ensure accurate JSON conversion
    private String convertToJson(ArrayList<String> dynamicFields) {
        StringBuilder jsonBuilder = new StringBuilder("{");
        for (String field : dynamicFields) {
            // Check if field is already a JSON string
            if (field.trim().startsWith("{") && field.trim().endsWith("}")) {
                jsonBuilder.append(field.substring(1, field.length() - 1)).append(",");
            } else {
                String[] parts = field.split(":");
                if (parts.length == 2) {
                    jsonBuilder.append("\"").append(parts[0].trim()).append("\":\"")
                            .append(parts[1].trim()).append("\",");
                } else {
                    System.out.println("Skipping invalid field: " + field);
                }
            }
        }
        if (jsonBuilder.length() > 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove last comma
        }
        jsonBuilder.append("}");
        System.out.println("Final JSON string: " + jsonBuilder.toString());
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

    public void setischecked(int id) {
        String sql = "UPDATE commande SET is_checked = 1 WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Commande", e);
        }
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

    public List<Commande> getCommandesByUserId(int userId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE user_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InsuranceService doaComService = new InsuranceService();
                Insurance doaCom = doaComService.getInsuranceById(rs.getInt("doa_com_id"));
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setMontant(rs.getFloat("montant"));
                commande.setDate_effet(rs.getDate("date_effet"));
                commande.setDate_exp(rs.getDate("date_exp"));
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

    public List<Commande> getUncheckedCommandesByUserId(int userId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE is_checked = 0 AND user_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InsuranceService doaComService = new InsuranceService();
                Insurance doaCom = doaComService.getInsuranceById(rs.getInt("doa_com_id"));
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setMontant(rs.getFloat("montant"));
                commande.setDate_effet(rs.getDate("date_effet"));
                commande.setDate_exp(rs.getDate("date_exp"));
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
    public List<Commande> getcheckedCommandesByUserId(int userId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE is_checked = 1 AND user_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InsuranceService doaComService = new InsuranceService();
                Insurance doaCom = doaComService.getInsuranceById(rs.getInt("doa_com_id"));
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setMontant(rs.getFloat("montant"));
                commande.setDate_effet(rs.getDate("date_effet"));
                commande.setDate_exp(rs.getDate("date_exp"));
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
    public void setAllUserCommandsChecked(int userId) {
        String sql = "UPDATE commande SET is_checked = 1 WHERE user_id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Commandes", e);
        }
    }
    public Commande getCommandeById(int id) {
        String sql = "SELECT * FROM commande WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                InsuranceService doaComService = new InsuranceService();
                Insurance doaCom = doaComService.getInsuranceById(rs.getInt("doa_com_id"));
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setMontant(rs.getFloat("montant"));
                commande.setDate_effet(rs.getDate("date_effet"));
                commande.setDate_exp(rs.getDate("date_exp"));
                String fullDoaJson = rs.getString("full_doa");
                ArrayList<String> fullDoa = parseJson(fullDoaJson);
                commande.setDoa_full(fullDoa);
                commande.setDoa_com_id(doaCom);
                commande.setUser_id(new User(rs.getInt("user_id")));
                commande.setIns_value(rs.getFloat("ins_value"));
                return commande;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Commande", e);
        }
        return null;
    }
}