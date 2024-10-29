package controller;

import exception.*;
import model.Coach;
import model.PracticalDay;
import model.User;
import model.Workout;
import model.Course;
import model.CourseCombo;
import model.Workout;
import repository.*;
import service.*;
import utils.FieldUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;
import utils.GlobalUtils;
import view.Menu;

import java.util.Map;

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

    private final UserService userService;
    private final CoachService coachService;
    private final PracticalDayService practicalDayService;
    private final WorkoutService workoutService;

    public FitnessCourseManagement() {
        this(title, menuOptions);
    }

    public FitnessCourseManagement(String title, String[] menuOptions) {
        super(title, menuOptions);
        userService = new UserService();
        coachService = new CoachService();
        practicalDayService = new PracticalDayService();
        workoutService = new WorkoutService();
    }

    @Override
    public void execute(int selection) {
        switch (selection) {
            case 1 -> {
                this.runAdminMenu();
            }
            case 2 -> {
                try {
                    verifyCoach("1");
                    this.runCoachMenu();
                } catch (VerifyFailedException e) {
                    System.err.println(e.getMessage());
                }
            }
            case 3 -> {
                try {
                    verifyUser("1");
                    this.runUserMenu();
                } catch (VerifyFailedException e) {
                    System.err.println(e.getMessage());
                }
            }
            case 4 -> {
                exitMenu();
            }
        }
    }

    public void verifyCoach(String coachID) throws VerifyFailedException {

    }

    public void verifyUser(String userID) throws VerifyFailedException {

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
                            System.err.println(e);
                        }
                    }
                    case 3 -> {

                    }
                    case 4 -> {
                        addCourseFromConsole();
                    }
                    case 5 -> {

                    }
                    case 6 -> {
                        courseService.updateOrDeleteCourseFromConsoleCustomize();
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

    private void addCourseFromConsole() {
        while (true) {
            try {
                System.out.println("Please enter course data as follows:");

                String courseId = GlobalUtils.getValue("Course ID: ", "Cannot be left blank");
                String courseName = GlobalUtils.getValue("Course Name: ", "Cannot be left blank");
                String addventor = GlobalUtils.getValue("Addventor (true/false): ", "Cannot be left blank");
                String generateDate = GlobalUtils.getValue("Generate Date (dd/MM/yyyy): ", "Cannot be left blank");
                String price = GlobalUtils.getValue("Price: ", "Cannot be left blank");
                String comboID = GlobalUtils.getValue("Combo ID: ", "Cannot be left blank");
                String coachId = GlobalUtils.getValue("Coach ID: ", "Cannot be left blank");

                List<Workout> workouts = new ArrayList<>();
                System.out.println("Please enter workout data as follows:");

                while (true) {
                    String input = GlobalUtils.getValue("Enter workout details (or 'done' to finish adding workouts): ", "Cannot be left blank");
                    if (input.trim().equalsIgnoreCase("done")) {
                        break;
                    }
                    Workout workout = getWorkoutDetails();
                    workout.runValidate();
                    workouts.add(workout);
                }

                Course course = new Course(courseId, courseName, addventor, generateDate, price, comboID, coachId, workouts);
                course.runValidate();
                courseService.add(course);
                System.out.println("Course added successfully.");
                break;

            } catch (InvalidDataException | IllegalArgumentException e) {
                System.err.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Invalid input format. Please check your data.");
            } catch (EventException e) {
                System.err.println(e);
            }
        }
    }

    private Workout getWorkoutDetails() {
        String workoutId = GlobalUtils.getValue("Workout ID: ", "Cannot be left blank");
        String workoutName = GlobalUtils.getValue("Workout Name: ", "Cannot be left blank");
        String description = GlobalUtils.getValue("Description: ", "Cannot be left blank");
        String repetition = GlobalUtils.getValue("Repetition: ", "Cannot be left blank");
        String sets = GlobalUtils.getValue("Sets: ", "Cannot be left blank");
        String duration = GlobalUtils.getValue("Duration: ", "Cannot be left blank");
        String done = GlobalUtils.getValue("Done (true/false): ", "Cannot be left blank");
        String courseId = GlobalUtils.getValue("Course Segment ID: ", "Cannot be left blank");

        return new Workout(workoutId, workoutName, description, repetition, sets, duration, done, courseId);
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
                        try {
                            userService.display();
                        } catch (EmptyDataException e) {
                            System.err.println(e);
                        }
                    }
                    case 2 -> {
                        try {
                            User user = new User();
                            userService.add(user);
                        } catch (EventException | InvalidDataException e) {
                            System.err.println(e);
                        }
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
                        try {
                            Coach coach = new Coach();
                            coachService.display();
                        } catch (EmptyDataException e){
                            System.err.println(e);
                        }
                    }
                    case 2 -> {
                        try {
                            Coach coach = new Coach();
                            coachService.add(coach);
                        } catch (EventException | InvalidDataException e) {
                            System.err.println(e);
                        }
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
                        try {
                            courseComboService.display();
                        } catch (EmptyDataException e) {
                            System.err.println(e);
                        }
                    }
                    case 2 -> {
                        createNewComboFromConsole();
                    }
                    case 3 -> {
                        courseComboService.updateOrDeleteCourseComboFromConsoleCustomize();
                    }
                    case 4 -> {
                        createNewComboForCourseFromConsole();
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

    private void createNewComboFromConsole() {
        while (true) {
            try {
                System.out.println("Please enter combo data:");

                String comboId = GlobalUtils.getValue("Combo ID: ", "Cannot be left blank");
                String comboName = GlobalUtils.getValue("Combo Name: ", "Cannot be left blank");
                String sales = GlobalUtils.getValue("Sales (percentage between 0 and 1): ", "Cannot be left blank");

                CourseCombo courseCombo = new CourseCombo(comboId, comboName, sales);
                courseCombo.runValidate();
                courseComboService.add(courseCombo);
                System.out.println("Combo added successfully.");
            } catch (InvalidDataException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred. Please check your data.");
            }
        }
    }

    private void createNewComboForCourseFromConsole() {
        try {
            String courseId = GlobalUtils.getValue("Enter the Course ID to assign a new combo: ", "Course ID cannot be left blank");
            Course course = courseService.findById(courseId);
            if (course == null) {
                System.err.println("Course not found with the given ID.");
            }
            String comboId = GlobalUtils.getValue("Enter Combo ID: ", "Combo ID cannot be left blank");
            String comboName = GlobalUtils.getValue("Enter Combo Name: ", "Combo name cannot be left blank");
            String sales = GlobalUtils.getValue("Enter Sales percentage (0 - 1): ", "Sales percentage cannot be left blank");

            CourseCombo newCombo = new CourseCombo(comboId, comboName, sales);
            newCombo.runValidate();
            courseComboService.add(newCombo);
            course.setComboID(comboId);
            courseService.update(course);
            System.out.println("New combo created and assigned to course successfully.");
        } catch (InvalidDataException e) {
            System.err.println("Data validation failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while creating a new combo for the course: " + e.getMessage());
        }
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

    public void updateOrDeletePracticalDayFromConsoleCustomize() {
        if (practicalDayService.getPractialDayTreeSet().isEmpty()) {
            System.out.println("Please create new practical day ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot leave blank");
                PracticalDay practicalDay;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be in correct form.");
                } else if ((practicalDay = practicalDayService.findById(id)) != null) {
                    System.out.println(practicalDay.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(practicalDay.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                practicalDayService.delete(practicalDay.getPracticalDayId());
                                System.out.println("Delete successfully");
                            } catch (EventException | NotFoundException e) {
                                System.err.println(e.getMessage());
                            }
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Can not be blank");
                                Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(practicalDay, editMenuOptions[selection - 1], newValue);
                                practicalDayService.update(id, fieldUpdateMap);
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void updateOrDeleteWorkoutFromConsoleCustomize() {
        if (workoutService.getWorkoutList().isEmpty()) {
            System.out.println("Please create new workout ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot leave blank");
                Workout workout;
                if (!ObjectUtils.validID(id)) {
                    System.out.println("Id must be in correct form.");
                } else if ((workout = workoutService.findById(id)) != null) {
                    System.out.println(workout.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(workout.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GlobalUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                workoutService.delete(workout.getWorkoutId());
                                System.out.println("Delete successfully");
                            } catch (EventException | NotFoundException e) {
                                System.err.println(e.getMessage());
                            }
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Can not be blank");
                                Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(workout, editMenuOptions[selection - 1], newValue);
                                workoutService.update(id, fieldUpdateMap);
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

}
