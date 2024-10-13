
package service;

import exception.IOException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Predicate;

import model.CourseSegment;


import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;
import utils.GlobalUtils;

public class CourseSegmentService implements ICourseSegmentService {
    private final CourseSegmentRepository courseSegmentRepository;

    private final ArrayList<CourseSegment> courseSegments;
    private final GlobalUtils globalUtils;

    public CourseSegmentService() throws IOException {
        courseSegmentRepository = new CourseSegmentRepository();
        courseSegments = (ArrayList<CourseSegment>) courseSegmentRepository.readFile();
        globalUtils = new GlobalUtils();
    }

    @Override
    public void display() {
        try {
            if (courseSegments.isEmpty()) {
                System.out.println("-> The List Is Empty !!");
            } else {
                for (CourseSegment course : courseSegments) {
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Display CourseSegment List - " + e.getMessage());
        }
    }

    @Override
    public void add(CourseSegment entry) {
        try {
            courseSegments.add(entry);
            System.out.println("-> CourseSegment Add Successfully.");
            courseSegmentRepository.writeFile(courseSegments); // Save
        } catch (Exception e) {
            System.out.println("-> Error While Add CourseSegment - " + e.getMessage());
        }
    }


    @Override
    public void delete(String id) {
        try {
            boolean removed = courseSegments.removeIf(course -> course.getCourseId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> CourseSegment With ID " + id + " Have Been Remove");
                courseSegmentRepository.writeFile(courseSegments);
            } else {
                System.out.println("-> CourseSegment With ID " + id + " Not Found");
            }
        } catch (Exception e) {
            System.out.println("-> Error While Delete CourseSegment - " + e.getMessage());
        }
    }

    public void update(CourseSegment updatedCourse) {
        try {
            Field[] courseField = updatedCourse.getClass().getDeclaredFields();
            int totalField = courseField.length;
            boolean isUpdate = true;

            while (isUpdate) {
                System.out.println("---- CUSTOMIZE COURSE SEGMENT ----");
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
            System.out.println("-> Error While Updating CourseSegment " + e.getMessage());
        }
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) {
        try {
            for (CourseSegment course : courseSegments) {
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
    public CourseSegment filter(String entry, String regex) {
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
