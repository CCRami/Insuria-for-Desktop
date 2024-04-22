package Service;

import Entity.Commande;
import Entity.Insurance;
import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class CommandeService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public CommandeService(){

        cnx= DataSource.getInstance().getConnection();
    }

    public void addCommande(Commande commande) {
        String sql = "INSERT INTO commande (montant, date_effet, date_exp, full_doa, doa_com_id, user_id,ins_value) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            pst = cnx.prepareStatement(sql);
            float insValue = commande.getIns_value();
            float montant = insValue * 0.75f; // Multiply insValue by 0.75

// Now set the calculated montant in the prepared statement
            pst.setFloat(1, montant);
            LocalDate dateEffet = LocalDate.now();
            pst.setString(2, dateEffet.toString());
            LocalDate dateExp = dateEffet.plusYears(1);
            pst.setString(3, dateExp.toString());
            String fullDoaJson = convertToJson(commande.getDoa_full());
            pst.setString(4, fullDoaJson);
            pst.setInt(5, commande.getDoa_com_id().getId());
            pst.setInt(6, 1);
            pst.setFloat(7, commande.getIns_value());


            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to convert ArrayList<String> to JSON string
    private String convertToJson(ArrayList<String> dynamicFields) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (String field : dynamicFields) {
            // Split the label and value and format them into a JSON object
            String[] parts = field.split(":");
            if (parts.length == 2) {
                jsonBuilder.append("{\"").append(parts[0]).append("\":\"").append(parts[1]).append("\"},");
            }
        }
        // Remove the last comma and close the JSON array
        if (jsonBuilder.length() > 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

}
