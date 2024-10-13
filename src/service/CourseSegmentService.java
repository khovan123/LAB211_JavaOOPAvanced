
package service;

import repository.CourseRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.main.Course;


import model.Workout;
import service.interfaces.ICourseSegmentService;
import utils.GlobalUtils;

public class CourseSegmentService implements ICourseSegmentService {
    private final CourseRepository courseRepository;
    private final ArrayList<Course> courseList;
    private final GlobalUtils globalUtils;

    public CourseSegmentService() {
        courseRepository = new CourseRepository();
        courseList = (ArrayList<Course>) courseRepository.readFile();
        globalUtils = new GlobalUtils();
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

    public void update(Course updatedCourse) {
        try {
            Field[] courseField = updatedCourse.getClass().getDeclaredFields();
            int totalField = courseField.length;
            boolean isUpdate = true;

            while (isUpdate) {
                System.out.println("---- CUSTOMIZE COURSE ----");
                for (int i = 0; i < courseField.length; i++) {
                    System.out.println((i + 1) + ". " + courseField[i].getName());
                }
                System.out.println((courseField.length + 1) + ". Finish Customize");

                int choice = globalUtils.getInteger("Enter Your Choice", "-> Invalid Input, Try Again");

                if (choice == totalField) {
                    isUpdate = false;
                    System.out.println("-> Finish Customize");
                    continue;
                }

                if (choice < 1 || choice > totalField) {
                    System.out.println("-> Invalid Option, Please Try Again");
                    continue;
                }

                Field selectedField = courseField[choice - 1];
                selectedField.setAccessible(true);

                try {
                    Object currentValue = selectedField.get(updatedCourse);
                    System.out.println("Current Value Of - " + selectedField.getName() + " - " + currentValue);
                    String newValue = globalUtils.getValue("Enter New Value" + selectedField.getName(), "Invalid Input, Try Again.");

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
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Updating Course " + e.getMessage());
        }
    }

    @Override
    public Course search(Predicate<Course> p) {
        try {
            for (Course course : courseList) {
                if (p.test(course)) {
                    return course;
                }
            }
        } catch (Exception e){
            System.out.println("-> Error While Searching - " + e.getMessage());
        }

        return null;
    }

    @Override
    public Course filter(String entry, String regex) {
        return null;
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
