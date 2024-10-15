
package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import repository.CoachRepository;
import repository.CoursePacketRepository;
import repository.CourseRepository;
import repository.UserRepository;
import service.interfaces.ICoachService;
import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public class CoachService implements ICoachService{
    private static CoachRepository coachRepository = new CoachRepository();
    private static CourseRepository courseRepository = new CourseRepository();
//    private static CoursePacketRepository coursePacketRepository = new CoursePacketRepository();
    private static UserRepository userRepository = new UserRepository();

    public CoachService() {}

    @Override
    public void display() throws EmptyDataException {
     for (Coach coach : coachRepository.getCoachList()){
         System.out.println(coach.getInfo());
     }

    }

    @Override
    public void add(Coach coach) throws EventException, InvalidDataException {
     coachRepository.add(coach);

    }

    @Override
    public void delete(String id) throws EventException , NotFoundException {
     coachRepository.delete(id);
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
     return coachRepository.search(p);
    }

    @Override
    public Coach filter(String entry, String regex)  {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Coach coach) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //---------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void addCourse(Course course) throws EventException {

        courseRepository.add(course);
    }

    @Override
    public void deleteCourse(String id) throws EventException {
        courseRepository.delete(id);
    }

    @Override
    public void updateCourse(Course course) {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Course searchCourse(Predicate<Course> p) throws NotFoundException {
        courseRepository.search(p);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateSchedule(Course course, PracticalDay practiceDay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //---------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void deleteUser(String id) throws EventException {
        userRepository.delete(id);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User searchUser(Predicate<User> user) throws NotFoundException {
        userRepository.search(user);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateUser(User user) {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addUser(User user) throws EventException {
        userRepository.add(user);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
