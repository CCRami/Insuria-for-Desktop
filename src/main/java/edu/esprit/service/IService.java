package edu.esprit.service;

import edu.esprit.entities.Agence;

import java.util.List;

public interface IService<T> {
    void addPST(T t);



    void ajouteragence(Agence agence);

    void modifierage(Agence agence);


    void supprimerage(int id);

    List<T> getAllage();


    Agence getOneById(int id);

    T getbyid(int id);

}
