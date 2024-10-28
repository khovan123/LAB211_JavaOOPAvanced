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
import utils.GlobalUtils;

public class PracticalDayService implements IPracticalDayService {

    private final PracticalDayRepository practicalDayRepository = new PracticalDayRepository();
    private final TreeSet<PracticalDay> practicalDayTreeSet;

    public PracticalDayService() {
        practicalDayTreeSet = new TreeSet<>();
        readFromDatabase();
    }

    public PracticalDayService(TreeSet<PracticalDay> practicalDayTreeSet) {
        this.practicalDayTreeSet = practicalDayTreeSet;
        readFromDatabase();
    }

    public void readFromDatabase() {
        try {
            practicalDayTreeSet.addAll(practicalDayRepository.readFile());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
            if (!existID(practiceDay)) {
                practicalDayTreeSet.add(practiceDay);
            } else {
                throw new EventException(practiceDay.getPracticalDayId() + " already exist.");
            }
        } catch (Exception e) {
            throw new EventException("Failed to add Practical Day: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            PracticalDay practicalDay = this.findById(id);
            practicalDayTreeSet.remove(practicalDay);
        } catch (Exception e) {
            throw new EventException("An error occurred while deleting Practical Day with ID: " + id + ". " + e.getMessage());
        }
    }

    @Override
    public void update(PracticalDay practicalDay) throws EventException, NotFoundException {
        try {
            PracticalDay existingPracticalDay = this.findById(practicalDay.getPracticalDayId());
            if (practicalDay.getPracticeDate() != null) {
                existingPracticalDay.setPracticeDate(GlobalUtils.getDateString(practicalDay.getPracticeDate()));
            }
            if (practicalDay.getScheduleId() != null) {
                existingPracticalDay.setScheduleId(practicalDay.getScheduleId());
            }
        } catch (Exception e) {
            throw new EventException("An error occurred while updating Practical Day with ID: " + practicalDay.getPracticalDayId() + e.getMessage());
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
        try {
            return this.search(p -> p.getPracticalDayId().equalsIgnoreCase(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existID(PracticalDay practicalDay) {
        try {
            findById(practicalDay.getPracticalDayId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
