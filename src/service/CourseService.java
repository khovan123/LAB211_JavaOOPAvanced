
package service;

import repository.CourseRepository;
import service.interfaces.ICourseService;

import java.util.ArrayList;
import java.util.function.Predicate;

import model.main.Course;
import model.sub.Workout;

public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final ArrayList<Course> courseList;

    public CourseService() {
        courseRepository = new CourseRepository();
        courseList = (ArrayList<Course>) courseRepository.readFile();
    }

    @Override
    public void display() {
        try {
            if (courseList.isEmpty()) {
                System.out.println("-> The List Is Empty !!");
            } else {
                for (Course course : courseList) {
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Display Course List - " + e.getMessage());
        }
    }

    @Override
    public void add(Course entry) {
        try {
            courseList.add(entry);
            System.out.println("-> Course Add Successfully.");
            courseRepository.writeFile(courseList); // Save
        } catch (Exception e) {
            System.out.println("-> Error While Add Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            boolean removed = courseList.removeIf(course -> course.getCourseId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> Course With ID " + id + " Have Been Remove");
                courseRepository.writeFile(courseList);
            } else {
                System.out.println("-> Course With ID " + id + " Not Found");
            }
        } catch (Exception e) {
            System.out.println("-> Error While Delete Course - " + e.getMessage());
        }
    }

    @Override
    public Course search(Predicate<Course> p) {
        try {
            for (Course course : courseList) {
                if (p.test(course))
                    return course;
            }
        } catch (Exception e) {
            System.out.println("-> Error While Searching Course - " + e.getMessage());
        }
        return null;
    }

    @Override
    public Course filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Course updatedCourse) {
        try {
            for (int i = 0; i < courseList.size(); i++) {
                Course c = courseList.get(i);
                if (c.getCourseId().equalsIgnoreCase(updatedCourse.getCourseId())) {
                    courseList.set(i, updatedCourse);
                    System.out.println("-> Course With Course ID" + updatedCourse.getCourseId() + " Have Been Updated!!");
                    return;
                }
            }
            System.out.println("-> Course With Course ID" + updatedCourse.getCourseId() + " Have Been Updated!!");
        } catch (Exception e) {
            System.out.println("-> Error While Updating Course - " + e.getMessage());
        }
    }

    @Override
    public void addWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
