package repository;

import exception.IOException;
import java.util.List;
import model.Workout;
import repository.interfaces.IWorkoutRepository;

public class WorkoutRepository implements IWorkoutRepository {

    //data sample: WK-YYYY, Leg Day, Do not off, 2, 4, 2, true, CS-YYYY
    static {

    }

    @Override
    public List<Workout> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<Workout> workouts) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
