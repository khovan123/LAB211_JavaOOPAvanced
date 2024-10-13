package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import exception.IOException;
import model.CoursePacket;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;
import utils.GlobalUtils;

public class CoursePacketService implements ICoursePacketService {
    private final CoursePacketRepository coursePacketRepository;
    private final List<CoursePacket> coursePacketList;
    private final GlobalUtils globalUtils;

    public CoursePacketService() throws IOException {
        coursePacketRepository = new CoursePacketRepository();
        coursePacketList = (ArrayList<CoursePacket>) coursePacketRepository.readFile();
        globalUtils = new GlobalUtils();
    }

    @Override
    public void display() {
        try {
            if (coursePacketList.isEmpty()) {
                System.out.println("-> The List Is Empty !!");
            } else {
                for (CoursePacket course : coursePacketList) {
                    System.out.println(course);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Displaying Course List - " + e.getMessage());
        }
    }

    @Override
    public void add(CoursePacket entry) {
        try {
            coursePacketList.add(entry);
            System.out.println("-> Course Added Successfully.");
            coursePacketRepository.writeFile(coursePacketList); // Save
        } catch (Exception e) {
            System.out.println("-> Error While Adding Course - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            boolean removed = coursePacketList.removeIf(course -> course.getCoursePacketId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> Course With ID " + id + " Has Been Removed");
                coursePacketRepository.writeFile(coursePacketList);
            } else {
                System.out.println("-> Course With ID " + id + " Not Found");
            }
        } catch (Exception e) {
            System.out.println("-> Error While Deleting Course - " + e.getMessage());
        }
    }

    public void update(CoursePacket updatedCourse) {
        try {
            Field[] courseField = updatedCourse.getClass().getDeclaredFields();
            int totalField = courseField.length;
            boolean isUpdate = true;

            while (isUpdate) {
                System.out.println("---- CUSTOMIZE COURSE PACKET ----");
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
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Updating Course " + e.getMessage());
        }
    }

    @Override
    public CoursePacket search(Predicate<CoursePacket> p) {
        try {
            for (CoursePacket course : coursePacketList) {
                if (p.test(course)) {
                    return course;
                }
            }
        } catch (Exception e) {
            System.out.println("-> Error While Searching - " + e.getMessage());
        }

        return null;
    }

    @Override
    public CoursePacket filter(String entry, String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
