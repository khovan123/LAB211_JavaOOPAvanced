package service;

import exception.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import model.*;
import repository.CoachRepository;
import service.interfaces.ICoachService;
import utils.FieldUtils;

public class CoachService implements ICoachService {

    private CoachRepository coachRepository;
    private List<Coach> coaches;

    public CoachService() {
        this.coachRepository = new CoachRepository();
        this.coaches = new ArrayList<>();
    }

    public void readFromDatabase() {
        try {
            for (Coach coach : coachRepository.readData()) {
                try {
                    this.add(coach);
                } catch (EventException | InvalidDataException e) {

                }
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (coaches.isEmpty()) {
            throw new EmptyDataException("List of coach is empty");
        }
        System.out.println("Coach ID\tFull Name\tDoB\tPhone\tCertificate");
        for (Coach coach : coaches) {
            System.out.println(coach.getInfo());
        }
    }

    @Override
    public void add(Coach coach) throws EventException, InvalidDataException {
        if (!existed(coach.getPersonId())) {
            try {
                coaches.add(coach);
                coachRepository.insertToDB(coach);
            } catch (SQLException e) {
                throw new EventException(e);
            }
        } else {
            throw new InvalidDataException("Coach with id: " + coach.getPersonId() + "was existed");
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            coachRepository.deleteToDB(id);
            coaches.remove(this.findById(id));
        } catch (NotFoundException | SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public void update(String id, Map<String, Object> entry) throws NotFoundException, EventException {
        for (String fieldName : entry.keySet()) {
            Coach coach = findById(id);
            Field field = FieldUtils.getFieldByName(coach.getClass(), fieldName);
            try {
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                coachRepository.updateToDB(id, updatedMap);
                field.set(coach, entry.get(fieldName));
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        if (fieldName.equalsIgnoreCase("personId")) {
            return CoachRepository.PersonID_Column;
        }
        if (fieldName.equalsIgnoreCase("fullName")) {
            return CoachRepository.FullName_Column;
        }
        if (fieldName.equalsIgnoreCase("DoB")) {
            return CoachRepository.DoB_Column;
        }
        if (fieldName.equalsIgnoreCase("phone")) {
            return CoachRepository.Phone_Column;
        }
        if (fieldName.equalsIgnoreCase("certificate")) {
            return CoachRepository.Certificate_Column;
        }
        if (fieldName.equalsIgnoreCase("active")) {
            return CoachRepository.Active_Column;
        }
        throw new NotFoundException("Not found any field");
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
        for (Coach coach : coaches) {
            if (p.test(coach)) {
                return coach;
            }
        }
        throw new NotFoundException("Not found any coach");
    }

    @Override
    public Coach findById(String id) throws NotFoundException {
        return this.search(p -> p.getPersonId().equalsIgnoreCase(id));
    }

    public boolean existed(String id) {
        try {
            return this.findById(id) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
