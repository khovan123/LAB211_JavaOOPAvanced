package repository;

import exception.IOException;
import java.util.List;
import model.Schedule;
import repository.interfaces.IScheduleRepository;

public class ScheduleRepository implements IScheduleRepository {

    //generate Schdule with id of CP-YYYY in practicalDayRepository
    static {

    }

    @Override
    public List<Schedule> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<Schedule> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
