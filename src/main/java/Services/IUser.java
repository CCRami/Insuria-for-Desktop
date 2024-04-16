package Services;
import java.util.List;
public interface IUser<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    List<T> displayAll();
    T displayByid(int id);

}
