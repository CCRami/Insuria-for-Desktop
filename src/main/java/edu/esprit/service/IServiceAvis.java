package edu.esprit.service;

import edu.esprit.entities.Agence;
import edu.esprit.entities.Avis;

import java.util.List;

public interface IServiceAvis <T> {

    //public  void ajouteravis(T t) ;
   /* public void modifier(T t) ;


    public T getOneById(int id) ;

    public void ajouteravis(Avis avis);*/
    public List<T> getAllavis();
    public List<T> getAllavisbyagence(Agence id);

    public void ajouteravis(Avis avis, Agence agence);

    public void supprimerav(int id) ;
}
