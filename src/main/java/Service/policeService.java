package Service;

import Entity.InsuranceCategory;
import util.DataSource;
import Entity.police;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class policeService {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    public policeService(){

        cnx= DataSource.getInstance().getConnection();
    }

    public police getPoliceById(int polId) {
        String sql = "SELECT * FROM police WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, polId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Assuming Police has a constructor that takes relevant parameters
                return new police(rs.getInt("id"));
            } else {
                // Handle case when no police with the given ID is found
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching Police by ID", e);
        }
    }

    public List<police> getAll(){
        List<police> listpolice=new ArrayList<>();
        String sql="select * from police";
        try {
            ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(sql);
            while(rs.next()){
                listpolice.add(new police(rs.getInt(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listpolice;
    }

}
