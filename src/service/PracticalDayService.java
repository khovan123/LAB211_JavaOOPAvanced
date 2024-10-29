package service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Predicate;

import exception.EmptyDataException;
import exception.EventException;
import exception.NotFoundException;
import model.PracticalDay;
import repository.PracticalDayRepository;
import service.interfaces.IPracticalDayService;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;

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

    public TreeSet<PracticalDay> getPractialDayTreeSet(){
        return practicalDayTreeSet;
    }

    public void readFromDatabase() {
        try {
            practicalDayTreeSet.addAll(practicalDayRepository.readData());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (practicalDayTreeSet.isEmpty()) {
            throw new EmptyDataException("No practice day found!!!");
        } else {
            for (PracticalDay practicalDay : practicalDayTreeSet) {
                System.out.println(practicalDay.getInfo());
            }
        }
    }

    @Override
    public void add(PracticalDay practiceDay) throws EventException {
        try {
            if (!existID(practiceDay)) {
                practicalDayTreeSet.add(practiceDay);
                practicalDayRepository.insertToDB(practiceDay);
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
            practicalDayRepository.deleteToDB(id);
        } catch (Exception e) {
            throw new EventException("An error occurred while deleting Practical Day with ID: " + id + ". " + e.getMessage());
        }
    }

    @Override
    public void update(String id, Map<String, Object> entry) throws NotFoundException, EventException {
        PracticalDay practicalDay = findById(id);
        for (String fieldName : entry.keySet()) {
            Field field = FieldUtils.getFieldByName(practicalDay.getClass(), fieldName);
            try {
                field.set(practicalDay, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                practicalDayRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
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

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("practiceDate")) {
            return PracticalDayRepository.PracticeDate_Column;
        } else if (fieldName.equalsIgnoreCase("scheduleId")) {
            return PracticalDayRepository.ScheduleID_Column;
        } else if (fieldName.equalsIgnoreCase("practicalDayId")) {
            return PracticalDayRepository.PracticalDayID_Column;
        }

        throw new NotFoundException("Not found any field for: " + fieldName);
    }
}
