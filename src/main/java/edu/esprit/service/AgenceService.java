package edu.esprit.service;

import edu.esprit.entities.Agence;
import edu.esprit.util.DataSource;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenceService implements IService<Agence>{
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet result;
    private Label home_totalEmployees;

    public AgenceService(){
        cnx = DataSource.getInstance().getConnection();
    }
    public void add(Agence A){
        String sql="insert into Agence(nomage,addresse,email,tel,create_at) value ('"+A.getNomage()+"','"+A.getAddresse()+"','"+A.getEmail()+"','"+A.getTel()+"','"+A.getCreate_at()+"')";
        try {
            ste=cnx.createStatement();
            ste.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    @Override
    public void ajouteragence(Agence agence) {
        String req = "INSERT INTO agence(nomage, addresse,email,tel,create_at) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, agence.getNomage());
            ps.setString(2, agence.getAddresse());
            ps.setString(3, agence.getEmail());
            ps.setInt(4, agence.getTel());
            ps.setString(5, agence.getCreate_at());

            ps.executeUpdate();
            System.out.println("Agency added !");

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();

        }
    }

    @Override
    public void modifierage(Agence agence) {

        String req = "UPDATE agence SET nomage = ?, addresse = ?, email = ?, tel = ? ,create_at=? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, agence.getNomage());
            ps.setString(2, agence.getAddresse());
            ps.setString(3, agence.getEmail());
            ps.setInt(4, agence.getTel());
            ps.setString(5, agence.getCreate_at());
            ps.setInt(6, agence.getIdage());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("agence with ID " + agence.getIdage() + " updated successfully!");
            } else {
                System.out.println("No record found with ID " + agence.getIdage() + ". Update failed.");
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

    }

    @Override
    public void supprimerage(int id) {
        String req = "DELETE FROM Agence WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {



                System.out.println(" deleted successfully!");

            } else {
                System.out.println(" Deletion failed.");
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

    }

    @Override
    public List<Agence> getAll() {
        List<Agence> list=new ArrayList<>();
        String sql="select * from Agence ";
        try{ste=cnx.createStatement();
            ResultSet res=ste.executeQuery(sql);
            while (res.next()){
                int id = res.getInt(1);
                String nomage = res.getString(2);
                String addresse = res.getString(3);
                String email = res.getString(4);
                String create= res.getString(6);

                int tel = res.getInt(5);

                list.add(new Agence(id,nomage, addresse, email, tel,create ));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    @Override
    public Agence getOneById(int id) {
        return null;
    }
    @Override
    public Agence getbyid(int id) {
        return null;
    }

    public void addPST(Agence A){
        String sql="insert into Agence(nomage,addresse,email,create_at,tel) values (?,?,?,?,?)";
        try {
            pst=cnx.prepareStatement(sql);
            pst.setString(1,A.getNomage());
            pst.setString(2,A.getAddresse());
            pst.setString(3,A.getEmail());
            pst.setString(4,A.getCreate_at());
            pst.setInt(5,A.getTel());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
