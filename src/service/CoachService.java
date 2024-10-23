package service;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import repository.CoachRepository;
import service.interfaces.ICoachService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;
import model.Coach;
import model.Course;
import model.PracticalDay;
import model.User;

public class CoachService implements ICoachService{
    private static CoachRepository coachRepository = new CoachRepository();
    private static CoursePacketService coursePacketService;
    private static CourseSegmentService courseSegmentService;
    static {
        try {
            courseSegmentService = new CourseSegmentService();
        } catch (exception.IOException e) {
            throw new RuntimeException(e);
        }
        try {
            coursePacketService = new CoursePacketService();
        } catch (exception.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CoachService() {}

    @Override
    public void display() throws EmptyDataException {
         for (Coach coach : coachRepository.getCoaches()){
             System.out.println(coach.getInfo());
         }
    }

    @Override
    public void add(Coach coach) throws EventException, InvalidDataException {

    CoachRepository coachRepository = new CoachRepository();
        //String coachId, String coachName, Date dateOfBirth, String phoneNumber, String coachEmail
        try{
            String id = checkString("Enter coach ID:","Invalid ID. Must be in the format CO-XXXX.","CO-\\d{4}$");
            String name = checkString("Enter coach name:", "Name should contain letters only!","^[a-zA-Z]+$");
            String date = checkString("Enter coach date of birth:","Invalid date of birth. Must be in the format dd/MM/yyyy.","^\\\\d{2}/\\\\d{2}/\\\\d{4}$");
            String phoneNumber = checkString("Enter coach phone number:","Phone should contains numbers only!","[0-9]+$");
            String email = checkString("Enter coach email","Invalid email!","^[a-zA-Z0-9]+@[a-zA-Z]+(\\\\.[a-zA-Z]+){1,2}$");

            String confirm = continueConfirm("Do you want to add this customer? (Y/N): ","Y/N only!");
            if(confirm.equalsIgnoreCase("N")){
                System.out.println("Coach not added!");
                return ;
            }
            else {
                coach = new Coach(id ,name,convertStringToDate(date),phoneNumber,email);
                coachRepository.getCoaches().add(coach);
                System.out.println("Coach added successfully");
            }
        }catch(Exception e){
            throw new EventException("Coach could not be added!");
        }

    }


    @Override
    public void delete(String id) throws EventException , NotFoundException {
        boolean remove = coachRepository.getCoaches().removeIf(c->c.getCoachId().equals(id));
        if(!remove){
            throw new EventException("Coach ID"+ id +" does not exist");
        }
        try{
            coachRepository.writeFile(coachRepository.getCoaches());
        }catch(IOException | exception.IOException e){
            throw new EventException("Coach ID"+ id +" could not be deleted");
        }
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
        return coachRepository.getCoaches().stream().filter(p).findFirst().orElseThrow(()->new NotFoundException("Coach not found")) ;
    }

    @Override
    public void update(Coach coach) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //---------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void addCourse(Course course) throws EventException {

    }

    @Override
    public void deleteCourse(String id) throws EventException, NotFoundException {
  courseSegmentService.delete(id);
    }

    @Override
    public void updateCourse(Course course) {
//courseSegmentService.update();
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Course searchCourse(Predicate<Course> p) throws NotFoundException {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateSchedule(Course course, PracticalDay practiceDay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //---------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void deleteUser(String id) throws EventException {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User searchUser(Predicate<User> user) throws NotFoundException {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateUser(User user) {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addUser(User user) throws EventException {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
