package services;

import entity.Indemnissation;

import java.sql.SQLException;
import java.util.List;

public interface IServiceIndemnisation <K,T> {
    int addIndemnisation(T t) throws SQLException;
    void modifierIndemnisation(T t) throws SQLException;


    void modifierIndemnisation(Indemnissation indemnisation) throws SQLException;

    void supprimerIndemnisation(int idRec) throws SQLException;

    List<T> afficherIndemnisation() throws SQLException;




    Indemnissation afficherUneIndemnisation(int id) throws SQLException;
}
