package service;

import exception.*;
import java.util.*;
import service.interfaces.IUserProgressService;
import java.util.function.Predicate;
import java.sql.SQLException;
import model.UserProgress;
import java.lang.reflect.Field;
import java.util.HashMap;
import model.RegistedCourse;
import repository.UserProgressRepository;
import utils.FieldUtils;

public class UserProgressService implements IUserProgressService {

    private List<UserProgress> userProgressList = new ArrayList<>();
    private UserProgressRepository userProgressRepository = new UserProgressRepository();
    private ScheduleService scheduleService;
    private RegistedCourseService registedCourseService;

    public UserProgressService(RegistedCourseService registedCourseService, ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
        this.registedCourseService = registedCourseService;
        this.readFromDatabase();
    }

    public void readFromDatabase() {
        try {
            for (UserProgress userProgress : userProgressRepository.readData()) {
                try {
                    if (!existID(userProgress.getUserProgressID())) {
                        this.userProgressList.add(userProgress);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (this.userProgressList.isEmpty()) {
            throw new EmptyDataException("User Progress is empty.");
        }
        for (UserProgress userProgress : userProgressList) {
            System.out.println(userProgress.getInfo());
        }
    }

    @Override
    public void add(UserProgress userProgress) throws EventException {
        if (!existID(userProgress.getUserProgressID())) {
            try {
                userProgressList.add(userProgress);
                userProgressRepository.insertToDB(userProgress);
            } catch (SQLException e) {
                throw new EventException(e);
            }
        } else {
            throw new EventException("User Progress with ID: " + userProgress.getUserProgressID() + " existed.");
        }

    }

    @Override
    public void delete(int id) throws EventException, NotFoundException {
        try {
            userProgressList.remove(findById(id));
            userProgressRepository.deleteToDB(id);
        } catch (SQLException e) {
            throw new EventException(e);
        }
    }

    @Override
    public UserProgress search(Predicate<UserProgress> p) throws NotFoundException {
        for (UserProgress up : userProgressList) {
            if (p.test(up)) {
                return up;
            }
        }
        throw new NotFoundException("No UserProgress found matching the criteria.");
    }

    @Override
    public UserProgress findById(int id) throws NotFoundException {
        try {
            return this.search(p -> p.getUserProgressID() == (id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existID(int userProgressId) {
        try {
            return findById(userProgressId) != null;
        } catch (NotFoundException ex) {
            return false;
        }

    }

    @Override
    public void update(int id, Map<String, Object> entry) throws EventException, NotFoundException {
        for (String fieldName : entry.keySet()) {
            UserProgress userProgress = findById(id);
            Field field = FieldUtils.getFieldByName(userProgress.getClass(), fieldName);
            try {
                field.setAccessible(true);
                field.set(userProgress, entry.get(fieldName));
                Map<String, Object> updatedMap = new HashMap<>();
                updatedMap.putIfAbsent(getColumnByFieldName(fieldName), entry.get(fieldName));
                userProgressRepository.updateToDB(id, updatedMap);
            } catch (IllegalAccessException | IllegalArgumentException | SQLException e) {
                throw new EventException(e);
            }
        }
    }

    private String getColumnByFieldName(String fieldName) throws NotFoundException {
        switch (fieldName) {
            case "userProgressId" -> {
                return UserProgressRepository.UserProgressID_Column;
            }
            case "registedCourseId" -> {
                return UserProgressRepository.RegistedCourseID_Column;
            }
            default ->
                throw new NotFoundException("Not found any field");
        }
    }

    public void setAllCompletedUserProgress() {
        for (UserProgress userProgress : this.userProgressList) {
            try {
                this.setCompletedUserProgress(userProgress.getUserProgressID());
            } catch (Exception e) {

            }
        }
    }

    public void setCompletedUserProgress(int registedCourseID) {
        try {
            int progressUserID = this.search(p -> p.getUserProgressID() == registedCourseID).getUserProgressID();
            int scheduleID = scheduleService.search(p -> Integer.compare(p.getUserProgressId(), progressUserID) == 0).getScheduleId();
            this.findById(progressUserID).setCompleted(scheduleService.getCompletedUserProgress(scheduleID));
        } catch (EmptyDataException | NotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public double getCompletedUserProgress(int registedCourseID) {
        try {
            int progressUserID = this.search(p -> p.getUserProgressID() == registedCourseID).getUserProgressID();
            int scheduleID = scheduleService.search(p -> Integer.compare(p.getUserProgressId(), progressUserID) == 0).getScheduleId();
            this.findById(progressUserID).setCompleted(scheduleService.getCompletedUserProgress(scheduleID));
            return scheduleService.getCompletedUserProgress(scheduleID);
        } catch (EmptyDataException | NotFoundException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public void displayUserProgress(int userID) throws EmptyDataException {

        for (RegistedCourse registedCourse : this.registedCourseService.searchRegistedCourseByUser(userID)) {
            try {
                UserProgress up = this.search(p -> Integer.compare(p.getRegistedCourseID(), registedCourse.getRegisteredCourseID()) == 0);
                System.out.println(registedCourse.getRegistedCourse().getCourseName() + " " + up.getCompleted() * 100 + " %");
            } catch (NotFoundException e) {

            }
        }

    }

}
