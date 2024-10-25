package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.IOException;
import exception.NotFoundException;
import repository.UserRepository;
import service.interfaces.IUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;
import model.Course;
import model.PracticalDay;
import model.User;

public class UserService implements IUserService {
    private static UserRepository userRepository = new UserRepository();
    private static CourseSegmentService courseSegmentService;
    private static PracticalDayService practicalDayService;
    private static CoursePacketService coursePacketService;
    static {
        try {
            courseSegmentService = new CourseSegmentService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void display() throws EmptyDataException {
        for (User user : userRepository.getUsers()) {
            System.out.println(user.getInfo());
        }
    }

    @Override
    public void add(User user) throws EventException {
        //String userId, String userName,Boolean gender ,Date dateOfBirth, String phoneNumber, String email
        try {
            String id =checkString("Enter user ID","Invalid ID.Must be in the format US-XXXX", "US-\\d{4}$");
            String name = checkString("Enter user name:", "Name should contain letters only!","^[a-zA-Z]+$");
            String gender = checkString("Male or Female(Y/N)","Y/N only", "^[Y|N]+$");
            String date = checkString("Enter user date of birth:", "Invalid date of birth. Must be in the format dd/MM/yyyy.","\\\\d{2}/\\\\d{2}/\\\\d{4}$");
            String phoneNumber = checkString("Enter user phone number:","Phone should contains numbers only!", "[0-9]+$");
            String email = checkString("Enter user email:", "Invalid email","^[a-zA-Z0-9]+@[a-zA-Z]+(\\\\.[a-zA-Z]+){1,2}$");

            String confirm = continueConfirm("Do you want to add this user? (Y/N):","Y/N only!" );
            if (confirm.equalsIgnoreCase("N")){
                System.out.println("User not added");
                return ;
            }
            else{
                user = new User(id,name,convertStringToBoolean(gender),convertStringToDate(date),phoneNumber,email);
                userRepository.getUsers().add(user);
                System.out.println("User added");
            }
        }catch(Exception e){
            throw new EventException("User could not be added");
        }

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
boolean remove = userRepository.getUsers().removeIf(c->c.getUserId().equals(id));
if(!remove){
    throw new NotFoundException("User ID" + id + "not found");
}
try{
    userRepository.writeFile(userRepository.getUsers());
}catch(Exception e){
    throw new EventException("User ID"+ id +"could not be deleted");
}
    }

    @Override
    public User search(Predicate<User> p) throws NotFoundException {
        return userRepository.getUsers().stream().filter(p).findFirst().orElseThrow(()->new NotFoundException("User could not be found"));
    }

    @Override
    public User findById(String id) throws NotFoundException {
        return null;
    }


    @Override
    public void update(User user) throws EventException, NotFoundException {

    }


    //---------------------------------------------------------------------------------------------------------------------------------//


    @Override
    public void searchCourse(Predicate<Course> p) {

    }

    @Override
    public void buyCourse(Course course) {

    }

    @Override
    public void displaySchedule(Course course) {

    }

    @Override
    public void displayCourse(Course course) {

    }

    @Override
    public void updateSchedule(Course course, PracticalDay practiceDay) {

    }
    //------------------------------------------------------------------------------------------------//

    // Enable users enter value:
    private static final Scanner sc = new Scanner(System.in);
    public static String getValue(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    // Check integer numbers:
    public static int checkInt(String msg, String errMsg) {
        int result = 0;
        while (true) {
            try {
                result = Integer.parseInt(getValue(msg));
                if (result <= 0) {
                    System.err.println("Enter a number larger than 0!");
                } else {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    // Check double numbers:
    public static double checkDouble(String msg, String errMsg) {
        double result = 0;
        while (true) {
            try {
                result = Double.parseDouble(getValue(msg));
                if (result <= 0) {
                    System.err.println("Enter a number larger than 0!");
                } else {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    // Check string input:
    public static String checkString(String msg, String errMsg, String regex) {
        while (true) {
            try {
                String result = getValue(msg);
                if (result.matches(regex)) return result;
                else System.err.println(errMsg);
            } catch (Exception e) {
                System.err.println(errMsg);
            }
        }
    }

    // Check if user confirms:
    public static String continueConfirm(String msg, String errMsg) {
        while (true) {
            try {
                String result = getValue(msg);
                if (result.equalsIgnoreCase("Y")) {
                    return "Y";
                } else if (result.equalsIgnoreCase("N")) {
                    return "N";
                }
            } catch (Exception e) {
                System.err.println(errMsg);
            }
        }
    }

    public static boolean convertStringToBoolean(String msg) {
        if (msg.equalsIgnoreCase("yes") || msg.equalsIgnoreCase("y")) {
            return true;
        } else if (msg.equalsIgnoreCase("no") || msg.equalsIgnoreCase("n")) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid input for boolean conversion");
        }
    }

    public static Date convertStringToDate(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(dob);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please enter date in format dd/MM/yyyy.");
            return null;
        }
    }
}
