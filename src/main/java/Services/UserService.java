package Services;

import Entities.User;
import util.DataSource;
import org.mindrot.jbcrypt.BCrypt;


import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;


public class UserService implements IUser<User>{

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public UserService() {
        conn = DataSource.getInstance().getCnx();
    }
    public void add(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String request = "insert into user (last_name,first_name,email,password,phone_number,birth_date,roles,is_verified,is_blocked,avatar,secret) values ('" + user.getLast_name() + "','" + user.getFirst_name() + "','" + user.getEmail() + "','" + hashedPassword + "'," + user.getPhone_number() + ",'" + user.getBirth_date() + "','" + user.getRole() + "','"+ user.isVerified() +"','"+ user.isBlocked() +"','"+ user.getAvatar() +"','"+ user.getSecret() +"')";


        try {
            ste = conn.createStatement();
            ste.execute(request);
            System.out.println("added succefully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(User user) {
        String requete = "DELETE FROM user WHERE id = " + user.getId();

        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(User user) {
        System.out.println(user.getBirth_date());
        String requete = "UPDATE user SET last_name='" + user.getLast_name() + "',first_name='" + user.getFirst_name() +"',phone_number='"+user.getPhone_number()+"' where id= " + user.getId();
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> displayAll() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";

        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLast_name(rs.getString("last_name"));
                user.setFirst_name(rs.getString("first_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone_number(rs.getInt("phone_number"));
                user.setBirth_date(String.valueOf(rs.getDate("birth_date").toLocalDate()));
                user.setRole(rs.getString("roles"));
                user.setAvatar(rs.getString("avatar"));
                user.setVerified(rs.getBoolean("is_verified"));
                user.setBlocked(rs.getBoolean("is_blocked"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public User displayByid(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = " + id;
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setLast_name(rs.getString("last_name"));
                user.setFirst_name(rs.getString("first_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone_number(rs.getInt("phone_number"));
                user.setBirth_date(String.valueOf(rs.getDate("birth_date").toLocalDate()));
                user.setRole(rs.getString("roles"));
                user.setVerified(rs.getBoolean("is_verified"));
                user.setBlocked(rs.getBoolean("is_blocked"));
                user.setAvatar(rs.getString("avatar"));
                user.setSecret(rs.getString("secret"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public int authenticate(String email, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ? ");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                System.out.println(hashedPassword);
                System.out.println(password);
                if (BCrypt.checkpw(password, hashedPassword))
                    return rs.getInt("id");
                else
                    return 0;

            }
        } catch (SQLException e) {
            return 0;
        }
        return 0;
    }
    public String role(int id) {
        try {

            PreparedStatement stmt1 = conn.prepareStatement("SELECT roles FROM user where id=?");
            stmt1.setInt(1, id);

            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("roles"));
                return rs.getString("roles");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "not found";
    }
    public void updateUserPassword(int userId, String newPassword) {

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
//                    stmt.close();
                }
                if (conn != null) {
//                    conn.close();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }


    public int session(int id) {
        try {
            if (id != 0)
                return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean exsitemail(String email) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ? ");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getUserIdByEmail(String email) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM user WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // User not found
    }
    public void verify(User u) {
        String requete = "UPDATE user SET is_verified = 1 WHERE id = " + u.getId();
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void block(User u) {
        String requete = "UPDATE user SET is_blocked = 1 WHERE id = " + u.getId();
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void unverify(User u) {
        String requete = "UPDATE user SET is_verified = 0 WHERE id = " + u.getId();
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void unblock(User u) {
        String requete = "UPDATE user SET is_blocked = 0 WHERE id = " + u.getId();
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String updateVerifiedBySecret(String secret) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE secret = ?");
            stmt.setString(1, secret);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE user SET is_verified = 1 WHERE secret = ?");
                updateStmt.setString(1, secret);
                updateStmt.executeUpdate();
                return "User verified successfully";
            } else {
                return "Secret not found";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSecretToNull(String secret) {
        try {
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE user SET secret = NULL WHERE secret = ?");
            updateStmt.setString(1, secret);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSecret(int userId, String secret) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE user SET secret = ? WHERE id = ?");
            stmt.setString(1, secret);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getUserEmailById(int userId) {
        String email = null;
        String sql = "SELECT email FROM user WHERE id = ?";
        try (
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return email;
    }

    public List<String> getAllUserEmails() {
        List<String> emails = new ArrayList<>();
        String query = "SELECT email FROM user";

        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return emails;
    }

}
