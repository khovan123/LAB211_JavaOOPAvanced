
package repository.interfaces;
import java.util.ArrayList;
import model.main.Coach;
public interface ICoachRepository extends Repository<Coach, ArrayList<Coach>>{
String coachPath ="/data/coach.csv";

}
