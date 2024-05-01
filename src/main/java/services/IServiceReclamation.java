package services;


import entity.Reclamation;

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
}