package Services;

import Entities.Agence;
import Entities.Avis;

import java.util.List;

public interface IServiceAvis <T> {

    void updateav(int id);
    void updateav1(int id);
    //public  void ajouteravis(T t) ;
   /* public void modifier(T t) ;


    public T getOneById(int id) ;

    public void ajouteravis(Avis avis);*/
    public List<T> getAllavis();
    public List<T> getAllavisbyuser(int id);

    public List<T> getAllavisbyagence(Agence id);

    public void ajouteravis(Avis avis);

    public void supprimerav(int id) ;
    public List<T> getAllavisuser();

}
