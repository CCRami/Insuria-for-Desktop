package services;


import Entity.Commande;
import Entity.Indemnissation;
import Entity.Reclamation;

import java.sql.SQLException;
import java.util.List;

public interface IServiceReclamation<T> {
    void addReclamation(T t) throws SQLException;

    void modifierReclamation(T t) throws SQLException;


    void modifierReclamationReponse(Reclamation reclamation) throws SQLException;

    void modifierReclamationIndemnisation(Reclamation reclamation) throws SQLException;

    void supprimerReclamation(int idRec) throws SQLException;

    List<T> afficherReclamations() throws SQLException;


    int selectId(Reclamation reclamation) throws SQLException;

    List<Reclamation> afficherReclamationsRefusees() throws SQLException;




    double afficherUneCommande(int id_cmd) throws SQLException;

    Commande afficher(int id) throws SQLException;

    int select_id_cmd(Reclamation reclamation) throws SQLException;
}