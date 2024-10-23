
package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;


public class UserProgressRepository implements IUserProgressRepository{

    private static List<UserProgress> userProgresses = new ArrayList<>();
    private static ScheduleRepository scheduleRepository = new ScheduleRepository();
    //generate with id: CP-YYYY in scheduleRepository
    static{
        
    }
    
    public List<UserProgress> getUserProgresses(){
        return userProgresses;
    }


    @Override
    public List<UserProgress> readFile() throws IOException {
        return List.of();
    }

    @Override
    public void writeFile(List<UserProgress> entry) throws IOException {

    }
}
