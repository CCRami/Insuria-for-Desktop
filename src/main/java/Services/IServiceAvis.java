package Services;

import Entities.Agence;
import Entities.Avis;

import java.util.List;

public interface IServiceAvis <T> {

    //public  void ajouteravis(T t) ;
   /* public void modifier(T t) ;


    public T getOneById(int id) ;

    public void ajouteravis(Avis avis);*/
    public List<T> getAllavis();
    public List<T> getAllavisbyuser(int id);

    public List<T> getAllavisbyagence(Agence id);

    public void ajouteravis(Avis avis, int id);

    public void supprimerav(int id) ;
}
