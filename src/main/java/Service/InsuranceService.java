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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsuranceService {
    private static final Logger logger = Logger.getLogger(InsuranceService.class.getName());

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public InsuranceService(){

        cnx= DataSource.getInstance().getConnection();
    }

    public void addInsurance(Insurance insurance) {
        String sql = "INSERT INTO assurance (name_ins, montant, doa, cat_a_id, ins_image, pol_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, insurance.getName_ins());
            pst.setFloat(2, insurance.getMontant());
            pst.setString(5, insurance.getIns_image());
            pst.setInt(4, insurance.getCatins_id().getId());
            pst.setInt(6, insurance.getPol_id().getId());

            String doaJson = convertToJson(insurance.getDoa());
            logger.info("DOA JSON before insertion: " + doaJson); // Logging statement
            pst.setString(3, doaJson);

            pst.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding insurance", e);
            throw new RuntimeException(e);
        }
    }



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
        String sql = "UPDATE assurance SET name_ins = ?, montant = ?, ins_image = ?, cat_a_id = ?, pol_id = ?, doa = ? WHERE id = ?";
        try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1, insurance.getName_ins());
            pst.setFloat(2, insurance.getMontant());
            pst.setString(3, insurance.getIns_image());
            pst.setInt(4, insurance.getCatins_id().getId());
            pst.setInt(5, insurance.getPol_id().getId());

            // Convert ArrayList<String> to JSON string for DOA
            String doaJson = convertToJson(insurance.getDoa());
            System.out.println("DOA JSON before update: " + doaJson); // Debug statement
            pst.setString(6, doaJson);

            pst.setInt(7, insurance.getId());
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

    public Insurance getInsuranceById(int insuranceId) {
        try {
            String sql = "SELECT *, doa FROM assurance WHERE id = ?";
            try (PreparedStatement pst = cnx.prepareStatement(sql)) {
                pst.setInt(1, insuranceId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        InsuranceCatService insuranceCatService = new InsuranceCatService();
                        InsuranceCategory cat = insuranceCatService.getInsuranceCategoryById(rs.getInt("cat_a_id"));

                        policeService policeService = new policeService();
                        police pol = policeService.getPoliceById(rs.getInt("pol_id"));

                        String doaJson = rs.getString("doa");
                        logger.info("DOA JSON from database: " + doaJson); // Logging statement
                        ArrayList<String> doa = null;
                        if (doaJson != null) {
                            doa = parseJson(doaJson);
                            logger.info("Parsed DOA: " + doa); // Logging statement
                        }

                        return new Insurance(rs.getInt("id"), rs.getString("name_ins"), rs.getFloat("montant"),
                                rs.getString("ins_image"), doa, cat, pol);
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting insurance by ID", e);
            // throw new CustomException("Error getting insurance by ID", e); // You can define CustomException
        }
        return null;
    }



    private ArrayList<String> parseJson(String json) {
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

    public List<Insurance> getInsurancesByCategory(InsuranceCategory category) {
        List<Insurance> insurances = new ArrayList<>();
        String sql = "SELECT * FROM assurance WHERE cat_a_id = ?";
        try (Connection cnx = DataSource.getInstance().getConnection();
             PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, category.getId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Assuming you already have methods to fetch InsuranceCategory and police details
                    InsuranceCatService insuranceCatService = new InsuranceCatService();
                    InsuranceCategory cat = insuranceCatService.getInsuranceCategoryById(rs.getInt("cat_a_id"));

                    policeService policeService = new policeService();
                    police pol = policeService.getPoliceById(rs.getInt("pol_id"));

                    // Add insurance to the list
                    insurances.add(new Insurance(rs.getInt("id"), rs.getString("name_ins"), rs.getFloat("montant"),
                            rs.getString("ins_image"), null, cat, pol));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insurances;
    }





}
