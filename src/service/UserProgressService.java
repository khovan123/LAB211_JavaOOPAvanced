package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import service.interfaces.IUserProgressService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import model.UserProgress;
import model.Workout;
import java.lang.reflect.Field;
import java.util.HashMap;
import repository.UserProgressRepository;
import utils.FieldUtils;

public class UserProgressService implements IUserProgressService {

    List<UserProgress> userProgressList = new ArrayList<>();
    UserProgressRepository userProgressRepository = new UserProgressRepository();

    static {

    }

    public UserProgressService() {
        userProgressList = userProgressRepository.readData();
    }

    @Override
    public void display() throws EmptyDataException {
        if (userProgressList.isEmpty()) {
            throw new EmptyDataException("User Progress is empty.");
        }
        for (UserProgress userProgress : userProgressList) {
            System.out.println(userProgress);
        }
    }

    @Override
    public void add(UserProgress userProgress) throws EventException {
        if (!existID(userProgress)) {
            userProgressList.add(userProgress);
        } else {
            throw new EventException("ID: " + userProgress.getUserProgressId() + " existed.");
        }

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        userProgressList.remove(findById(id));
    }

//    @Override
//    public void update(UserProgress userProgress) throws EventException, NotFoundException {
//        boolean found = false;
//        for (int i = 0; i < userProgressList.size(); i++) {
//            if (userProgressList.get(i).getUserProgressId().equals(userProgress.getUserProgressId())) {
//                userProgressList.set(i, userProgress);
//
//                try {
//                    userProgressRepository.writeFile(userProgressList);
//                } catch (IOException ex) {
//                    System.out.println(ex.getMessage());
//                }
//                found = true;
//                break;
//            }
//        }
//        if (!found) {
//            throw new NotFoundException("UserProgress not found with ID: " + userProgress.getUserId());
//        }
//    }
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
    public UserProgress findById(String id) throws NotFoundException {
        try {
            return this.search(p -> p.getUserProgressId().equals(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    public boolean existID(UserProgress userProgress) {
        try {
            if (findById(userProgress.getUserProgressId()) == null) {
                return true;
            }
        } catch (NotFoundException ex) {
            System.out.println("Can not find " + userProgress);
        }

        return false;
    }

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {
        for (String fieldName : entry.keySet()) {
            UserProgress userProgress = findById(id);
            Field field = FieldUtils.getFieldByName(userProgress.getClass(), fieldName);
            try {
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
            case "userProgressId":
                return UserProgressRepository.UserProgressID_Column;
            case "registedCourseId":
                return UserProgressRepository.RegistedCourseID_Column;
            default:
                throw new NotFoundException("Not found any field");
        }
    }

}
