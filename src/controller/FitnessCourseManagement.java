package controller;

import exception.*;
import model.Course;
import model.Workout;
import repository.*;
import service.*;
import utils.GlobalUtils;
import view.Menu;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FitnessCourseManagement extends Menu<String> {
    private CourseService courseService;
    private CourseComboService courseComboService;
    private RegistedCourseService registedCourseService;

    static String title = "FITNESS COURSE\nHOME";
    static String[] menuOptions = {
        "Admin",
        "Coach",
        "User",
        "Exit"
    };

    public FitnessCourseManagement() {
        this(title, menuOptions);
    }

    public FitnessCourseManagement(String title, String[] menuOptions) {
        super(title, menuOptions);
    }

    @Override
    public void execute(int selection) {
        switch (selection) {
            case 1 -> {
                this.runAdminMenu();
            }
            case 2 -> {
                try {
                    vetifyCoach("1");
                    this.runCoachMenu();
                } catch (VetifyFailedException e) {
                    System.err.println(e.getMessage());
                }
            }
            case 3 -> {
                try {
                    vetifyUser("1");
                    this.runUserMenu();
                } catch (VetifyFailedException e) {
                    System.err.println(e.getMessage());
                }
            }
            case 4 -> {
                exitMenu();
            }
        }
    }

    public void vetifyCoach(String coachID) throws VetifyFailedException {

    }

    public void vetifyUser(String userID) throws VetifyFailedException {

    }
//----------------------------------------------------------start main menu-----------------------------------------------------

    public void runAdminMenu() {
        String[] adminMenuOptions = {
            "User Management",
            "Coach Management",
            "Course Combo Management",
            "Return home"
        };
        Menu<String> adminMenu = new Menu("HOME >> ADMIN", adminMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        adminMenu.exitMenu();
        continueExecution = true;
    }

    //before run CoachMenu, request enter ID
    public void runCoachMenu() {
        String[] coachMenuOptions = {
            "Personal information",
            "Show all courses",
            "Show all member in courses",
            "Create new course",
            "Update personal infromation",
            "Update course",
            "Return home"
        };
        Menu<String> coachMenu = new Menu("HOME >> COACH", coachMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {
                        try {
                            courseService.display();
                        } catch (EmptyDataException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 3 -> {

                    }
                    case 4 -> {
                    }
                    case 5 -> {

                    }
                    case 6 -> {

                    }
                    case 7 -> {
                        exitMenu();
                    }
                }
            }
        };
        coachMenu.exitMenu();
        continueExecution = true;
    }

    public void addCourseFromConsole() {
        while (true) {
            try {
                System.out.println("Please enter course data follow this format for add: Course ID, Course Name, Addventor (true/false), Generate Date (dd/MM/yyyy), Price, Combo ID, Coach ID, Workouts (format: WorkoutID, WorkoutName, Description, Repetition, Sets, Duration, Done, CourseSegmentID; separate multiple workouts with '|')");
                String input = GlobalUtils.getValue("Enter course: ", "Cannot be left blank");
                String data[] = input.split(",");

                String courseId = data[0].trim();
                String courseName = data[1].trim();
                String addventor = data[2].trim();
                String generateDate = data[3].trim();
                String price = data[4].trim();
                String comboID = data[5].trim();
                String coachId = data[6].trim();

                Course course = new Course(courseId, courseName, addventor, GlobalUtils.getDateString(generateDate), price, comboID, coachId, workouts);
                course.runValidate();
                courseService.add(course);
                System.out.println("Course added successfully");
                break;
            } catch (InvalidDataException | ParseException e) {
                System.err.println(e.getMessage());
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.err.println("Invalid input format. Please check your data.");
            } catch (EventException e) {
                System.err.println(e);
            }
        }
    }



    //before run UserMenu, request enter ID
    public void runUserMenu() {
        String[] userMenuOptions = {
            "Personal information",
            "Show all courses which joined",
            "Show all progresses",
            "Register course",
            "Update personal information",
            "Update schedule",
            "Return home"
        };
        Menu<String> userMenu = new Menu("HOME >> USER", userMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> {
                    }
                    case 5 -> {

                    }
                    case 6 -> {

                    }
                    case 7 -> {
                        exitMenu();
                    }
                }
            }
        };
        userMenu.exitMenu();
        continueExecution = true;
    }
//----------------------------------------------------------end main menu--------------------------------------------------------

//----------------------------------------------------------start admin menu-----------------------------------------------------
    public void runUserManagementMenu() {
        String admin_UserOptions[] = {
            "Show all users",
            "Create new user",
            "Update user",
            "Return admin menu"
        };
        Menu<String> admin_UserMenu = new Menu("HOME >> ADMIN >> USER", admin_UserOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        admin_UserMenu.exitMenu();
        continueExecution = true;
    }

    public void runCoachManagementMenu() {
        String admin_CoachMenuOptions[] = {
            "Display all coach",
            "Create new coach",
            "Update coach",
            "Return admin menu"
        };
        Menu<String> admin_CoachMenu = new Menu("HOME >> ADMIN >> COACH", admin_CoachMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        admin_CoachMenu.exitMenu();
        continueExecution = true;
    }

    public void runCourseComboManagementMenu() {
        String courseComboMenuOptions[] = {
            "Show all combo",
            "Create new combo",
            "Update combo",
            "Update combo for course",
            "Return admin menu"
        };
        Menu<String> courseComboMenu = new Menu("HOME >> ADMIN >> COURSE COMBO", courseComboMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {

                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> {
                    }
                    case 5 -> {
                        exitMenu();
                    }
                }
            }
        };
        courseComboMenu.exitMenu();
        continueExecution = true;
    }

//----------------------------------------------------------end admin menu-----------------------------------------------------
//----------------------------------------------------------start coach menu---------------------------------------------------
//----------------------------------------------------------end coach menu-----------------------------------------------------
//----------------------------------------------------------start user menu----------------------------------------------------
//----------------------------------------------------------end user menu------------------------------------------------------
    public static void main(String[] args) {
        FitnessCourseManagement fitnessCourseManagement = new FitnessCourseManagement();
        fitnessCourseManagement.run();
    }

}
