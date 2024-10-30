package controller;

import exception.*;
import jdk.jfr.Event;
import model.Coach;
import model.PracticalDay;
import model.User;
import model.Workout;
import repository.*;
import service.*;
import utils.FieldUtils;
import utils.GettingUtils;
import utils.GlobalUtils;
import utils.ObjectUtils;
import view.Menu;

import java.util.Map;
import utils.GettingUtils;

public class FitnessCourseManagement extends Menu<String> {

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
                while (true) {
                    try {
                        String coachId = GlobalUtils.getValidatedInput("Enter CoachID for update or delete: ",
                                "CoachID cannot be null, empty, or whitespace.",
                                input -> input != null && !input.trim().isEmpty());
                        verifyCoach(coachId);
                        this.runCoachMenu();
                        break;
                    } catch (VerifyFailedException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            case 3 -> {
                while (true) {
                    try {
                        String userId = GlobalUtils.getValidatedInput("Enter UserID for update or delete: ",
                                "UserID cannot be null, empty, or whitespace.",
                                input -> input != null && !input.trim().isEmpty());
                        verifyUser(userId);
                        this.runUserMenu();
                        break;
                    } catch (VerifyFailedException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            case 4 -> {
                exitMenu();
            }
        }
    }

    public void verifyCoach(String coachID) throws VerifyFailedException {
        try {
            coachService.findById(coachID);
        } catch (NotFoundException e) {
            throw new VerifyFailedException("Verify failed!!!");
        }
    }

    public void verifyUser(String userID) throws VerifyFailedException {
        try {
            userService.findById(userID);
        } catch (NotFoundException e) {
            throw new VerifyFailedException("Verify failed!!!");
        }
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
                            System.out.println("Create new user");
                            String userID = GettingUtils.getID("Enter user ID: ", "ID must be UXXXX", "U[0-9]{4}");
                            String fullName = GettingUtils.getName("Enter full name: ", "Full Name must be letters");
                            User user = new User();
                        } catch (Exception e) {
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
                        } catch (EmptyDataException e) {
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

    public void updateOrDeletePracticalDayFromConsoleCustomize() {
        if (practicalDayService.getPractialDayTreeSet().isEmpty()) {
            System.out.println("Please create new practical day ^^");
            return;
        }
        while (true) {
            try {
                String id = GlobalUtils.getValue("Enter id for update: ", "Cannot leave blank");
                PracticalDay practicalDay;
                if (!ObjectUtils.validCodePracticalDay(id)) {
                    System.out.println("Id must be in correct form.");
                } else if ((practicalDay = practicalDayService.findById(id)) != null) {
                    System.out.println(practicalDay.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(practicalDay.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Please enter a valid option!");
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
                if (!ObjectUtils.validCodeWorkout(id)) {
                    System.out.println("Id must be in correct form.");
                } else if ((workout = workoutService.findById(id)) != null) {
                    System.out.println(workout.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(workout.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Please enter a valid option!");
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
