package Service;

import util.DataSource;
import Entity.Insurance;
import Entity.InsuranceCategory;
import Entity.police;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InsuranceService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public InsuranceService(){

        cnx= DataSource.getInstance().getConnection();
    }

    public void addInsurance(Insurance insurance) {
        String sql = "INSERT INTO assurance (name_ins, montant, ins_image, cat_a_id, pol_id, doa) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, insurance.getName_ins());
            pst.setFloat(2, insurance.getMontant());
            pst.setString(3, insurance.getIns_image());
            pst.setInt(4, insurance.getCatins_id().getId());
            pst.setInt(5, insurance.getPol_id().getId());

            // Convert ArrayList<String> to JSON string
            String doaJson = convertToJson(insurance.getDoa());
            pst.setString(6, doaJson);

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to convert ArrayList<String> to JSON string
    private String convertToJson(ArrayList<String> dynamicFields) {
        // Using a simple approach here, you can use a JSON library for more complex structures
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (String field : dynamicFields) {
            jsonBuilder.append("\"").append(field).append("\",");
        }
        // Remove the last comma and close the JSON array
        if (jsonBuilder.length() > 1) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }




    public void updateInsurance(Insurance insurance) {
        String sql = "UPDATE assurance SET name_ins = ?, montant = ?, ins_image = ?, catins_id = ?, pol_id = ? WHERE id = ?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, insurance.getName_ins());
            pst.setFloat(2, insurance.getMontant());
            pst.setString(3, insurance.getIns_image());
            pst.setInt(4, insurance.getCatins_id().getId()); 
            pst.setInt(5, insurance.getPol_id().getId());
            pst.setInt(6, insurance.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteInsurance(int insuranceId) {
        String sql = "DELETE FROM assurance WHERE id = ?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setInt(1, insuranceId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Insurance> getAllInsurances() {
        List<Insurance> insurances = new ArrayList<>();
        String sql = "SELECT * FROM assurance";
        try {
            pst = cnx.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InsuranceCatService insuranceCatService = new InsuranceCatService();
                InsuranceCategory cat = insuranceCatService.getInsuranceCategoryById(rs.getInt("cat_a_id"));

                policeService policeService = new policeService();
                police pol = policeService.getPoliceById(rs.getInt("pol_id"));
                insurances.add(new Insurance(rs.getInt("id"), rs.getString("name_ins"), rs.getFloat("montant"),
                        rs.getString("ins_image"), null, cat, pol));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insurances;
    }


}
