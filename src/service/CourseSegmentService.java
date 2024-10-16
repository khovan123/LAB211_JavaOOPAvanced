package service;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;

import java.util.function.Predicate;

import model.CourseSegment;
import model.Workout;
import repository.CourseSegmentRepository;
import service.interfaces.ICourseSegmentService;

public class CourseSegmentService implements ICourseSegmentService {

    private static CourseSegmentRepository courseSegmentRepository;

    public CourseSegmentService() throws IOException {
        courseSegmentRepository = new CourseSegmentRepository();
    }

    @Override
    public void display() {
        try {
            for (CourseSegment courseSegment : courseSegmentRepository.getCourseSegments()) {
                System.out.println(courseSegment.getInfo());
            }
        } catch (Exception e) {
            throw new RuntimeException("-> Error Occur While Displaying Course Segment: " + e.getMessage());
        }

    }

    @Override
    public void add(CourseSegment courseSegment) throws EventException {
        if (!courseSegment.getCourseId().matches("CS-\\d{4}")) {
            throw new EventException("-> Invalid Course Segment ID Format: " + courseSegment.getCourseId() + " - Must Be CS-YYYY");
        }
        try {
            courseSegmentRepository.getCourseSegments().add(courseSegment);
            System.out.println("-> Course Added Successfully.");
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Segment - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            boolean removed = courseSegmentRepository.getCourseSegments().removeIf(courseSegment -> courseSegment.getCourseId().equalsIgnoreCase(id));

            if (!removed) {
                throw new NotFoundException("-> Course Segment with ID " + id + " not found.");
            }
            System.out.println("-> Course Segment with ID " + id + " has been removed.");

        } catch (Exception e) {
            throw new EventException("-> An error occurred while deleting the Course Segment " + e.getMessage());
        }
    }


    @Override
    public void update(CourseSegment courseSegment) throws EventException, NotFoundException {
//        --do not write this code--

//        try {
//
//            Field[] courseFields = updatedCourse.getClass().getDeclaredFields();
//            boolean isUpdate = true;
//
//            while (isUpdate) {
//                System.out.println("---- CUSTOMIZE COURSE ----");
//                for (int i = 0; i < courseFields.length; i++) {
//                    System.out.println((i + 1) + ". " + courseFields[i].getName());
//                }
//                System.out.println((courseFields.length + 1) + ". Finish Customize");
//
//                int choice = GlobalUtils.getInteger("Enter Your Choice", "-> Invalid Input, Try Again");
//
//                if (choice == courseFields.length + 1) {
//                    isUpdate = false;
//                    System.out.println("-> Finish Customization");
//                    continue;
//                }
//
//                if (choice < 1 || choice > courseFields.length) {
//                    System.out.println("-> Invalid Option, Please Try Again");
//                    continue;
//                }
//
//                Field selectedField = courseFields[choice - 1];
//                selectedField.setAccessible(true);
//
//                try {
//                    Object currentValue = selectedField.get(updatedCourse);
//                    System.out.println("Current Value Of - " + selectedField.getName() + " - " + currentValue);
//                    String newValue = GlobalUtils.getValue("Enter New Value for " + selectedField.getName(), "Invalid Input, Try Again.");
//
//                    if (selectedField.getType() == String.class) {
//                        selectedField.set(updatedCourse, newValue);
//                    } else if (selectedField.getType() == int.class) {
//                        selectedField.set(updatedCourse, Integer.parseInt(newValue));
//                    } else if (selectedField.getType() == boolean.class) {
//                        selectedField.set(updatedCourse, Boolean.parseBoolean(newValue));
//                    } else if (selectedField.getType() == double.class) {
//                        selectedField.set(updatedCourse, Double.parseDouble(newValue));
//                    } else {
//                        System.out.println("Unsupported field type: " + selectedField.getType());
//                    }
//                    System.out.println("Updated " + selectedField.getName() + " to " + newValue + " successfully.");
//                } catch (IllegalAccessException e) {
//                    System.out.println("-> Error Accessing Field - " + e.getMessage());
//                } catch (NumberFormatException e) {
//                    System.out.println("-> Invalid Number Format for Field - " + selectedField.getName());
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("-> Error While Updating Course - " + e.getMessage());
//        }
    }

    @Override
    public CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException {
        for (CourseSegment courseSegment : courseSegmentRepository.getCourseSegments()) {
            if (p.test(courseSegment)) {
                return courseSegment;
            }
        }
        throw new NotFoundException("-> Course Segment not found matching the given criteria.");
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
