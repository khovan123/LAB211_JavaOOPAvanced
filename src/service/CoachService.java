package service;

import exception.EmptyDataException;
import exception.EventException;

import exception.NotFoundException;
import repository.CoachRepository;
import service.interfaces.ICoachService;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public class CoachService implements ICoachService {
    private final CoachRepository coachRepository = new CoachRepository();
    private final TreeSet<Coach> coachTreeSet;

    public CoachService() {
        coachTreeSet = new TreeSet<>();
        readFromDatabase();
    }

    private void readFromDatabase() {
        try {
            coachTreeSet.addAll(coachRepository.readFile());
        } catch (exception.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (coachTreeSet.isEmpty()) {
            throw new EmptyDataException("No coaches found!!!");
        } else {
            for (Coach coach : coachTreeSet) {
                System.out.println(coach.getInfo());
            }
        }
    }

    @Override
    public void add(Coach coach) throws EventException {
        try {
            coachTreeSet.add(coach);
            coachRepository.writeFile(new ArrayList<>(coachTreeSet));
            System.out.println("Coach added successfully!");
        } catch (Exception e) {
            throw new EventException("Failed to add Coach: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        Coach coach = this.findById(id);
        if (coachTreeSet.remove(coach)) {
            try {
                coachRepository.writeFile(new ArrayList<>(coachTreeSet));
                System.out.println("Deleted Coach with ID: " + id + " successfully!");
            } catch (exception.IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NotFoundException("Coach ID " + id + " does not exist.");
        }
    }

    @Override
    public Coach search(Predicate<Coach> predicate) throws NotFoundException {
        return coachTreeSet.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Coach not found!!!"));
    }

    @Override
    public Coach findById(String id) throws NotFoundException {
        return this.search(c -> c.getCoachId().equalsIgnoreCase(id));
    }

    @Override
    public void update(Coach coach) throws EventException, NotFoundException {
        delete(coach.getCoachId());
        add(coach);
        System.out.println("Coach updated successfully!");
    }

    @Override
    public void addCourse(Course course) throws EventException {

    }

    @Override
    public void deleteCourse(String id) throws EventException, NotFoundException {

    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public Course searchCourse(Predicate<Course> p) throws NotFoundException {
        return null;
    }

    @Override
    public void updateSchedule(Course course, PracticalDay practiceDay) {

    }

    @Override
    public void deleteUser(String id) throws EventException {

    }

    @Override
    public User searchUser(Predicate<User> user) throws NotFoundException {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void addUser(User user) throws EventException {

    }


}
