package tn.esprit.applicatiopnpi.services;

import tn.esprit.applicatiopnpi.models.Sinistre;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.applicatiopnpi.utils.MyDatabase;
public class SinistreService implements Iservice<Sinistre> {
    private Connection connection;
    public SinistreService(){
        connection = MyDatabase.getInstance().getConnection();
    }
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet result;
    private Label home_totalEmployees;
    public void add(Sinistre S){
        String sql="insert into Sinistre(sin_name,description_sin,image_path) value ('"+S.getSin_name()+"','"+S.getDescription_sin()+"','"+S.getImage_path()+"')";
        try {
            ste=connection.createStatement();
            ste.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void ajouter(Sinistre sinistre) {
        String req = "INSERT INTO Sinistre(sin_name,description_sin,image_path) VALUES (?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, sinistre.getSin_name());
            ps.setString(2, sinistre.getDescription_sin());
            ps.setString(3, sinistre.getImage_path());

            ps.executeUpdate();
            System.out.println("Sinister added !");

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();

        }

    }
    @Override
    public void modifier(Sinistre sinistre) {
        String req = "UPDATE Sinistre SET sin_name = ?, description_sin = ?, image_path = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            // Définition des valeurs pour les paramètres de la requête
            ps.setString(1, sinistre.getSin_name());
            ps.setString(2, sinistre.getDescription_sin());
            ps.setString(3, sinistre.getImage_path());
            ps.setInt(4, sinistre.getId());  // Assurez-vous que sinistre.getId() renvoie bien l'ID correct

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Sinistre with ID " + sinistre.getId() + " updated successfully!");
            } else {
                System.out.println("No record found with ID " + sinistre.getId() + ". Update failed.");
            }
        } catch (SQLException e) {
            // Gestion ou journalisation de l'exception
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM Sinistre WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
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
    public List<Sinistre> getAll() {
        List<Sinistre> list=new ArrayList<>();
        String sql="select * from Sinistre ";
        try{ste=connection.createStatement();
            ResultSet res=ste.executeQuery(sql);
            while (res.next()){
                int id = res.getInt(1);
                String sin_name = res.getString(2);
                String description_sin = res.getString(3);
                String image_path = res.getString(4);



                list.add(new Sinistre(id,sin_name, description_sin, image_path));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
