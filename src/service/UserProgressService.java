package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import service.interfaces.IUserProgressService;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserProgress;
import model.Workout;
import repository.UserProgressRepository;

public class UserProgressService implements IUserProgressService {

    List<UserProgress> userProgressList = new ArrayList<>();
    UserProgressRepository userProgressRepository = new UserProgressRepository();

    static {

    }

    public UserProgressService() throws IOException {
        try {
            userProgressList = userProgressRepository.readFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
        try {
            existID(userProgress);
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage()+ ". Add failed.");
        }
        if (userProgress == null) {
            throw new EventException("UserProgress is null");
        }
        userProgressList.add(userProgress);
        System.out.println("Added successfully.");

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        UserProgress userProgress = findById(id);
        if (userProgress == null) {
            throw new NotFoundException("Can not found user progress.");
        }
        userProgressList.remove(userProgress);
        System.out.println("Deleted successfully.");
    }

    @Override
    public void update(UserProgress userProgress) throws EventException, NotFoundException {
        boolean found = false;
        for (int i = 0; i < userProgressList.size(); i++) {
            if (userProgressList.get(i).getUserId().equals(userProgress.getUserId())) {
                userProgressList.set(i, userProgress);

                try {
                    userProgressRepository.writeFile(userProgressList);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("UserProgress updated successfully!!!");
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NotFoundException("UserProgress not found with ID: " + userProgress.getUserId());
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
    public UserProgress findById(String id) throws NotFoundException {
        UserProgress exist = findById(id);
        for (UserProgress up : userProgressList) {
            if (up.getUserId().equals(id));
            return up;
        }
        throw new NotFoundException("Can not found user progress.");
    }

    public boolean existID(UserProgress userProgress) throws NotFoundException {
        try {
            if (findById(userProgress.getUserId()) == null) {
                return true;
            }
        } catch (Exception e) {
            throw new NotFoundException(userProgress.getUserId() + " not found.");
        }
        return false;
    }

    public void updateProgress(Workout workout) {

    }

}
