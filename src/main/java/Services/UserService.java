package Services;

import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    public Connection cnx;

    public UserService() {
        cnx = DataSource.getInstance().getConnection();
    }

    private static final Logger logger = Logger.getLogger(CommandeService.class.getName());

    public List<Integer> getAllEmailsWithInsurances() {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT DISTINCT email FROM user";
        try (Connection cnx = DataSource.getInstance().getConnection();
             PreparedStatement pst = cnx.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                userIds.add(rs.getInt("email"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving user IDs with insurances", e);
            throw new RuntimeException(e);
        }
        return userIds;
    }
    public String getUserEmailById(int userId) {
        String email = null;
        String sql = "SELECT email FROM user WHERE id = ?";
        try (
                PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving user email by ID", e);
            throw new RuntimeException(e);
        }

        // Log retrieved email
        logger.log(Level.INFO, "Retrieved email for user ID {0}: {1}", new Object[]{userId, email});

        return email;
    }


}
