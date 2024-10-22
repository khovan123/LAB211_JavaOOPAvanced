package service;

import java.util.TreeSet;
import java.util.function.Predicate;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import model.PracticalDay;
import repository.PracticalDayRepository;
import service.interfaces.IPracticalDayService;

public class PracticalDayService implements IPracticalDayService {

    private final PracticalDayRepository practicalDayRepository = new PracticalDayRepository();
    private final TreeSet<PracticalDay> practicalDayTreeSet = new TreeSet<>();

    public PracticalDayService() throws IOException {
        readFromDatabase();
    }

    public void readFromDatabase() throws exception.IOException {
        practicalDayTreeSet.addAll(practicalDayRepository.readFile());
    }

    @Override
    public void display() throws EmptyDataException {
        if (practicalDayTreeSet.isEmpty()) {
            throw new EmptyDataException("No practice day found!!!");
        } else {
            for (PracticalDay practicalDay : practicalDayTreeSet) {
                System.out.println(practicalDay);
            }
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        try {
            practicalDayTreeSet.add(practiceDay);
//            practicalDayRepository.writeFile(practicalDayTreeSet);
            System.out.println("Practical Day added successfully!");
        } catch (Exception e) {
            throw new EventException("Failed to add Practical Day: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            practicalDayTreeSet.remove(this.search(p -> p.getPracticeDayId().equalsIgnoreCase(id)));
            System.out.println("Deleted Practical Day with ID: " + id + " successfully!");
        } catch (Exception e) {
            throw new EventException("An error occurred while deleting Practical Day with ID: " + id + ". " + e.getMessage());
        }
    }

    @Override
    public void update(PracticalDay practicalDay) throws EventException, NotFoundException {
        try {
            practicalDayTreeSet.remove(this.search(p -> p.getPracticeDayId().equalsIgnoreCase(practicalDay.getPracticeDayId())));
            practicalDayTreeSet.add(practicalDay);
//            practicalDayRepository.writeFile(practicalDayTreeSet);
            System.out.println("Practical Day updated successfully!");
        } catch (Exception e){
            throw new EventException("An error occurred while updating Practical Day with ID: " + practicalDay.getPracticeDayId() + e.getMessage());
        }
    }

    @Override
    public PracticalDay search(Predicate<PracticalDay> p) throws NotFoundException {
        for (PracticalDay practicalDay : practicalDayTreeSet) {
            if (p.test(practicalDay)) {
                return practicalDay;
            }
        }
        throw new NotFoundException("Not found any practice day!!!");
    }

    @Override
    public PracticalDay findById(String id) throws NotFoundException {
        for (PracticalDay practicalDay : practicalDayTreeSet) {
            if (practicalDay.getPracticeDayId().equals(id)) {
                return practicalDay;
            }
        }
        return null;
    }


}
