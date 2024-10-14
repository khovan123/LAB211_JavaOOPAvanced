package service;

import exception.EventException;
import exception.IOException;
import exception.NotFoundException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.Course;
import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;
import utils.GlobalUtils;

public class CourseSegmentService implements ICourseSegmentService {
    private final CourseSegmentRepository courseSegmentRepository;
    private final List<CourseSegment> courses;
    private final GlobalUtils globalUtils;

    public CourseSegmentService() throws IOException {
        courseSegmentRepository = new CourseSegmentRepository();
        courses = courseSegmentRepository.readFile();
        globalUtils = new GlobalUtils();
    }

    @Override
    public void display() {
        try {
            if (courses.isEmpty()) {
                System.out.println("-> The List Is Empty !!");
            } else {
                for (Course course : courses) {
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Displaying Course List - " + e.getMessage());
        }
    }

    @Override
    public void add(Course course) throws EventException {
        try {
            courses.add((CourseSegment) course);
            System.out.println("-> Course Added Successfully.");
            courseSegmentRepository.writeFile(courses); // Save changes
        } catch (Exception e) {
            System.out.println("-> Error While Adding Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            boolean removed = courses.removeIf(course -> course.getCourseId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> Course With ID " + id + " Has Been Removed");
                courseSegmentRepository.writeFile(courses);
            } else {
                throw new NotFoundException("Course with ID " + id + " Not Found");
            }
        } catch (NotFoundException e) {
            System.out.println("-> " + e.getMessage());
        } catch (Exception e) {
            System.out.println("-> Error While Deleting Course - " + e.getMessage());
        }
    }

    @Override
    public void update(Course updatedCourse) throws EventException, NotFoundException {
        try {

            Field[] courseFields = updatedCourse.getClass().getDeclaredFields();
            boolean isUpdate = true;

            while (isUpdate) {
                System.out.println("---- CUSTOMIZE COURSE ----");
                for (int i = 0; i < courseFields.length; i++) {
                    System.out.println((i + 1) + ". " + courseFields[i].getName());
                }
                System.out.println((courseFields.length + 1) + ". Finish Customize");

                int choice = globalUtils.getInteger("Enter Your Choice", "-> Invalid Input, Try Again");

                if (choice == courseFields.length + 1) {
                    isUpdate = false;
                    System.out.println("-> Finish Customization");
                    continue;
                }

                if (choice < 1 || choice > courseFields.length) {
                    System.out.println("-> Invalid Option, Please Try Again");
                    continue;
                }

                Field selectedField = courseFields[choice - 1];
                selectedField.setAccessible(true);

                try {
                    Object currentValue = selectedField.get(updatedCourse);
                    System.out.println("Current Value Of - " + selectedField.getName() + " - " + currentValue);
                    String newValue = globalUtils.getValue("Enter New Value for " + selectedField.getName(), "Invalid Input, Try Again.");

                    if (selectedField.getType() == String.class) {
                        selectedField.set(updatedCourse, newValue);
                    } else if (selectedField.getType() == int.class) {
                        selectedField.set(updatedCourse, Integer.parseInt(newValue));
                    } else if (selectedField.getType() == boolean.class) {
                        selectedField.set(updatedCourse, Boolean.parseBoolean(newValue));
                    } else if (selectedField.getType() == double.class) {
                        selectedField.set(updatedCourse, Double.parseDouble(newValue));
                    } else {
                        System.out.println("Unsupported field type: " + selectedField.getType());
                    }
                    System.out.println("Updated " + selectedField.getName() + " to " + newValue + " successfully.");
                } catch (IllegalAccessException e) {
                    System.out.println("-> Error Accessing Field - " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("-> Invalid Number Format for Field - " + selectedField.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Updating Course - " + e.getMessage());
        }
    }

    @Override
    public Course search(Predicate<Course> p) throws NotFoundException {
        try {
            for (Course course : courses) {
                if (p.test(course)) {
                    return course;
                }
            }
            throw new NotFoundException("Course Not Found");
        } catch (NotFoundException e) {
            System.out.println("-> " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("-> Error While Searching - " + e.getMessage());
            return null;
        }
    }

    @Override
    public Course filter(String entry, String regex) {
        return null;
    }

    @Override
    public void addWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteWorkout(Workout workout) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Workout searchWorkout(Predicate<Workout> p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
