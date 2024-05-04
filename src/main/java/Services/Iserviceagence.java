package Services;
import Entities.Agence;

import java.util.List;

public interface Iserviceagence<T> {
    void addPST(T t);



    void ajouteragence(Agence agence);

    void modifierage(Agence agence);


    void supprimerage(int id);

    List<T> getAllage();


    Agence getOneById(int id);

    T getbyid(int id);

}
