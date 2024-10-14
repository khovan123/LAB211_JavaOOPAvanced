package service;

import java.util.function.Predicate;
import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import model.PracticalDay;
import repository.PracticalDayRepository;
import service.interfaces.IPracticalDayService;

public class PracticalDayService implements IPracticalDayService {

    private static PracticalDayRepository practicalDayRepository = new PracticalDayRepository();

    public PracticalDayService() {
    }

    public void readFromDatabase() throws IOException {
    }

    @Override
    public void display() throws EmptyDataException {
        if (practicalDayRepository.getPracticalDays().isEmpty()) {
            throw new EmptyDataException("Practical days empty!");
        } else {
            for (PracticalDay practicalDay : practicalDayRepository.getPracticalDays()) {
                System.out.println(practicalDay.getInfo());
            }
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        try {
            practicalDayRepository.add(practiceDay);
        } catch (EventException e) {
            throw new EventException("Failed to add practical day: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            practicalDayRepository.delete(id);
            System.out.println("Delete practical day with id: " + id + " successfully!");
        } catch (EventException e) {
            throw new EventException("An error occurred while deleting Practical Day with ID: " + id + ". " + e.getMessage());
        }
    }

    @Override
    public void update(PracticalDay practicalDay) {

    }

    @Override
    public PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException {
        return practicalDayRepository.search(p);
    }

    @Override
    public PracticalDay filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
