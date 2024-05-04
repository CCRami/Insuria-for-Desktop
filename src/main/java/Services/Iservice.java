package Services;

import java.util.List;

public interface Iservice<T>{
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    List<T> getAll();


}
