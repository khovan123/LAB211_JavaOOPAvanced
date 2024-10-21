package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import service.interfaces.IScheduleService;
import java.util.function.Predicate;
import model.Schedule;

public class ScheduleService implements IScheduleService {

    List<Schedule> scheduleList = new ArrayList<>();

    @Override
    public void display() throws EmptyDataException {
        if (scheduleList.isEmpty()) {
            throw new EmptyDataException("Schedule is empty.");
        } else {
            for (Schedule s : scheduleList) {
                System.out.println(s);
            }
        }
    }

    @Override
    public void add(Schedule schedule) throws EventException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Schedule schedule) throws EventException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Schedule search(Predicate<Schedule> p) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
