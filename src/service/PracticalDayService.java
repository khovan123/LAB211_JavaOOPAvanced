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
        for (PracticalDay practicalDay : practicalDayRepository.getPracticalDays()) {
            System.out.println(practicalDay.getInfo());
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        practicalDayRepository.add(practiceDay);
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        practicalDayRepository.delete(id);
    }

    @Override
    public void update(PracticalDay practicalDay) {

    }

    @Override
    public PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException {
        return practicalDayRepository.search(p);
    }

   
}
