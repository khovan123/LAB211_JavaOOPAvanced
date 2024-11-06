package controller;

import exception.*;
import java.text.ParseException;
import model.*;
import service.*;
import utils.FieldUtils;
import utils.*;
import view.Menu;
import java.util.*;
import view.Printer;

public class FitnessCourseManagement extends Menu<String> {

    static String title = "FITNESS COURSE\nHOME";
    static String[] menuOptions = {
        "Admin",
        "Coach",
        "User",
        "Exit"
    };

    private UserService userService;
    private CoachService coachService;
    private PracticalDayService practicalDayService;
    private WorkoutService workoutService;
    private RegistedCourseService registedCourseService;
    private CourseService courseService;
    private CourseComboService courseComboService;
    private UserProgressService userProgressService;
    private RegistedWorkoutService registedWorkoutService;
    private NutritionService nutritionService;
    private ScheduleService scheduleService;

    public FitnessCourseManagement() {
        this(title, menuOptions);
    }

    public FitnessCourseManagement(String title, String[] menuOptions) {
        super(title, menuOptions);
        this.courseComboService = new CourseComboService();
        this.workoutService = new WorkoutService();
        this.nutritionService = new NutritionService();
        this.courseService = new CourseService(workoutService);
        this.registedCourseService = new RegistedCourseService(courseService);
        this.registedWorkoutService = new RegistedWorkoutService(workoutService);
        this.practicalDayService = new PracticalDayService(registedWorkoutService, nutritionService);
        this.scheduleService = new ScheduleService(practicalDayService);
        this.userProgressService = new UserProgressService(registedCourseService, scheduleService);
        this.userService = new UserService(registedCourseService, userProgressService);
        this.coachService = new CoachService(registedCourseService, userService, courseService, courseComboService);

    }

    @Override
    public void execute(int selection) {
        switch (selection) {
            case 1 -> {
                runAdminMenu();
            }
            case 2 -> {
                while (true) {
                    try {
                        int coachId = GettingUtils.getInteger("Enter CoachID for verify: ",
                                "CoachID must be valid");
                        verifyCoach(coachId);
                        this.runCoachMenu(coachId);
                        break;
                    } catch (VerifyFailedException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            case 3 -> {
                while (true) {
                    try {
                        int userId = GettingUtils.getInteger("Enter UserID for verify: ",
                                "UserID must be valid");
                        verifyUser(userId);
                        this.runUserMenu(userId);
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

    public void verifyCoach(int coachID) throws VerifyFailedException {
        try {
            Coach coach = coachService.findById(coachID);
            if (!coach.isActive()) {
                System.out.println("Active your account successfully");
                coach.setActive("true");
            }
        } catch (NotFoundException | InvalidDataException e) {
            throw new VerifyFailedException("Verify failed!!!");
        }
    }

    public void verifyUser(int userID) throws VerifyFailedException {
        try {
            User user = userService.findById(userID);
            if (!user.isActive()) {
                System.out.println("Active your account successfully");
                user.setActive("true");
            }
        } catch (NotFoundException | InvalidDataException e) {
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
                        runUserManagementMenu();
                    }
                    case 2 -> {
                        runCoachManagementMenu();
                    }
                    case 3 -> {
                        runCourseComboManagementMenu();
                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        adminMenu.run();
        continueExecution = true;
    }

    //before run CoachMenu, request enter ID
    public void runCoachMenu(int coachID) {
        String[] coachMenuOptions = {
            "Personal information",
            "Show all courses",
            "Show all member in registed courses",
            "Create new course",
            "Update personal infromation",
            "Update course",
            "Return home"
        };
        NotificationService.display(coachID);
        Menu<String> coachMenu = new Menu("HOME >> COACH", coachMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {
                        try {
                            String[] options = new String[1];
                            options[0] = coachService.findById(coachID).getInfo();
                            Printer.printTable("Personal Infomation", "Information", options);
                        } catch (NotFoundException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            coachService.displayCoursesByCoach(coachID);
                        } catch (NotFoundException e) {
                            System.err.println(e.getMessage());
                        }
                    }

                    case 3 -> {
                        try {
                            coachService.displayUserInCoursesByCoach(coachID);
                        } catch (EmptyDataException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        addCourseFromConsole(coachID);
                    }
                    case 5 -> {
                        updateOrDeleteCoachFromConsoleCustomize(coachID);
                    }
                    case 6 -> {
                        try {
                            updateOrDeleteCourseFromConsoleCustomize(coachID);
                        } catch (EmptyDataException | NotFoundException e) {

                        }
                    }
                    case 7 -> {
                        exitMenu();
                    }
                }
            }
        };
        coachMenu.run();
        continueExecution = true;
    }

    public void updateOrDeleteCoachFromConsoleCustomize(int id) {
        if (coachService.isEmpty()) {
            System.out.println("Please create new coach ^^");
            return;
        }
        try {
            Coach coach;
            if ((coach = coachService.findById(id)) != null) {
                System.out.println(coach.getInfo());
                String[] editMenuOptions = FieldUtils.getEditOptions(coach.getClass());
                for (int i = 0; i < editMenuOptions.length; i++) {
                    System.out.println((i + 1) + ". " + editMenuOptions[i]);
                }
                while (true) {
                    int selection = GettingUtils.getInteger("Enter selection: ", "Invalid option!");
                    if (selection == editMenuOptions.length - 1) {
                        try {
                            coachService.delete(id);
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
                            String newValue = GlobalUtils.getValue("Enter new value: ", "Invalid value!");
                            Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(coach, editMenuOptions[selection - 1], newValue);
                            coachService.update(id, fieldUpdateMap);
                            System.out.println("Update successfully");
                            break;
                        } catch (Exception ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            }
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void addCourseFromConsole(int coachId) {
        try {
            System.out.println("Create new course");
            int courseId = courseService.size() + 1;
            String courseName = GettingUtils.getName("Course Name: ", "Course Name must be letters");
            boolean addventor = GettingUtils.getBoolean("Weight gain (true/false): ", "Addventor must be true/false");
            String generateDate = GlobalUtils.dateFormat(new Date());
            double price = GettingUtils.getDouble("Price: ", "Price must be a positive number");
            try {
                courseComboService.display();
            } catch (EmptyDataException e) {
                System.out.println(e.getMessage());
            }
            int comboID;
            while (true) {
                comboID = GettingUtils.getInteger("Course Combo ID: ", "Course ID must be valid");
                if (courseComboService.existID(comboID)) {
                    break;
                } else {
                    System.err.println("Not found have any course combo with ID: " + comboID);
                }
            }

            List<Workout> workouts = new ArrayList<>();
            int i = workoutService.getWorkoutList().size();
            System.out.println("Create new workouts");
            while (true) {
                int workoutId = i++;
                String workoutName = GettingUtils.getName("Workout Name: ", "Workout Name must be letters");
                int repetition = GettingUtils.getInteger("Repetition: ", "Repetition must be a positive number");
                int sets = GettingUtils.getInteger("Sets: ", "Sets must be a positive number");
                int duration = GettingUtils.getInteger("Duration: ", "Duration must be a positive number");
                try {
                    workouts.add(new Workout(String.valueOf(workoutId), workoutName, GlobalUtils.convertToString(repetition), GlobalUtils.convertToString(sets), GlobalUtils.convertToString(duration), String.valueOf(courseId)));
                } catch (InvalidDataException | ParseException e) {
                    System.err.println(e.getMessage());
                }
                String input = GettingUtils.getString("Enter Y to continue or N (/any) to finish (Y/N) add workout: ", "Invalid input!");
                if (input.trim().toUpperCase().equalsIgnoreCase("Y")) {

                } else {
                    Course course = new Course(String.valueOf(courseId), courseName, GlobalUtils.convertToString(addventor), generateDate, GlobalUtils.convertToString(price), String.valueOf(comboID), String.valueOf(coachId));
                    course.setWorkoutService(workouts);
                    courseService.add(course);
                    System.out.println("Course added successfully.");
                    break;
                }
            }
        } catch (InvalidDataException | EventException | ParseException e) {
            System.err.println(e.getMessage());
        }

    }

    //before run UserMenu, request enter ID
    public void runUserMenu(int userID) {
        String[] userMenuOptions = {
            "Personal information",
            "Show all courses",
            "Let's practice!!!",
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
                        try {
                            String[] options = new String[1];
                            options[0] = userService.findById(userID).getInfo();
                            Printer.printTable("User Infomation", "Information", options);
                        } catch (NotFoundException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            userService.displayJoinedCourse(userID);
                        } catch (EmptyDataException e) {
                            System.err.println(e.getMessage());
                        }

                    }
                    case 3 -> {
                        try {
                            System.out.println("0. Return User Menu");
                            List<RegistedCourse> registedCourses;

                            while (true) {
                                registedCourses = registedCourseService.searchRegistedCourseByUser(userID);
                                for (int i = 0; i < registedCourses.size(); i++) {
                                    System.out.println((i + 1) + ". " + registedCourses.get(i).getInfo() + " " + GlobalUtils.decimalFormat(userProgressService.getCompletedUserProgress(registedCourses.get(i).getRegisteredCourseID()) * 100) + "%");
                                }
                                int option = GettingUtils.getPositiveInteger("Choose course: ", "Invalid selection");
                                if (option == 0) {
                                    break;
                                } else if (option < 0 || option > registedCourses.size()) {
                                    System.out.println("Invalid data input!");
                                } else {
                                    int scheduleID;
                                    try {
                                        scheduleID = getScheduleID(userID, registedCourses.get(option - 1).getRegisteredCourseID());
                                        try {
                                            PracticalDay practicalDay = practicalDayService.search(p
                                                    -> p.getScheduleId() == (scheduleID)
                                                    && GlobalUtils.dateFormat(p.getPracticeDate()).equalsIgnoreCase(GlobalUtils.dateFormat(new Date())));
                                            System.out.println(practicalDayService.practicalDayToString(practicalDay.getPracticalDayId()));
                                            if (practicalDay.isDone()) {
                                                System.out.println("Today, you have been completed exercise in this course");
                                            } else {
                                                int done = GettingUtils.getInteger("Done (1) ? : ", "Invalid data input!");
                                                if (done == 1) {
                                                    try {
                                                        Map<String, Object> map = new HashMap<>();
                                                        map.put("done", true);
                                                        practicalDayService.update(practicalDay.getPracticalDayId(), map);
                                                    } catch (EventException | NotFoundException e) {
                                                        System.err.println(e.getMessage());
                                                    }
                                                }
                                            }
                                        } catch (NotFoundException e) {
                                            System.out.println("Today, you dont have exercise in this course");
                                        }
                                    } catch (NotFoundException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        } catch (EmptyDataException e) {
                            System.err.println(e.getMessage());
                        }

                    }
                    case 4 -> {
                        registerCourse(userID);
                    }
                    case 5 -> {
                        updateOrDeleteUserFromConsoleCustomize(userID);
                    }
                    case 6 -> {

                    }
                    case 7 -> {
                        exitMenu();
                    }
                }
            }
        };
        userMenu.run();
        continueExecution = true;
    }

    public int getScheduleID(int userID, int registedCourseID) throws NotFoundException {
        UserProgress userProgress = userProgressService.search(p -> p.getRegistedCourseID() == (registedCourseID));
        Schedule schedule = scheduleService.search(p -> p.getUserProgressId() == (userProgress.getUserProgressID()));
        return schedule.getScheduleId();
    }

    public void updateOrDeleteUserFromConsoleCustomize(int id) {
        while (true) {
            try {
                User user;
                if ((user = userService.findById(id)) != null) {
                    System.out.println(user.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(user.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Invalid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                userService.delete(id);
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
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Invalid value!");
                                Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(user, editMenuOptions[selection - 1], newValue);
                                userService.update(id, fieldUpdateMap);
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void registerCourse(int userID) {
        try {
            courseService.display();
            while (true) {
                int courseID = GettingUtils.getInteger("Course ID: ", "Course ID must be valid");
                try {
                    registedCourseService.search(p -> p.getCourseID() == (courseID) && p.getUserID() == (userID));
                    System.err.println("User with ID: " + userID + " already joined in course with ID: " + courseID);
                } catch (NotFoundException e) {
                    if (courseService.existsID(courseID)) {
                        Course course = courseService.findById(courseID);
                        String option = GettingUtils.getString("Summit for register this course (Y/N)?: ", "Must be Y/N");
                        if (option.toUpperCase().equalsIgnoreCase("Y")) {
                            int registerCourseID = registedCourseService.size() + 1;
                            Date current = new Date();
                            String registeredDate = GlobalUtils.dateFormat(current);
                            int months = GettingUtils.getInteger("Number of months: ", "Number of month must be a positive number");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(current);
                            calendar.add(Calendar.MONTH, months);
                            String finishRegisteredDate = GlobalUtils.dateFormat(calendar.getTime());
                            try {
                                registedCourseService.add(new RegistedCourse(String.valueOf(registerCourseID), registeredDate, finishRegisteredDate, String.valueOf(courseID), String.valueOf(userID)));
                                System.out.println("Make contract successfully");
                                NotificationService.addMessage(course.getCoachId(), userService.findById(userID).getFullName() + " enrolled course " + course.getCourseName());
                                return;
                            } catch (EventException | InvalidDataException | ParseException ex) {
                                System.err.println(ex.getMessage());
                            }
                        }

                    } else {
                        System.err.println("Course with ID: " + courseID + " was not existed");
                    }
                }
            }
        } catch (EmptyDataException | NotFoundException e) {
            System.err.println(e.getMessage());
        }
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
                            int userID = userService.size() + 1 + coachService.size();
                            String fullName = GettingUtils.getName("Enter full name: ", "Full Name must be letters");
                            String DoB = GlobalUtils.dateFormat(GettingUtils.getDate("Enter Date of Birth: ", "Date of birth must be yyyy-MM-dd"));
                            String phone = GettingUtils.getPhone("Enter phone number: ", "Phone number must be start with 0 and have 10 digits");
                            String addventor = Boolean.toString(GettingUtils.getBoolean("Enter target with weight gain or weight lost (true/false): ", "Target must be true or false"));
                            User user = new User(String.valueOf(userID), fullName, DoB, phone, GlobalUtils.convertToString(true), addventor);
                            try {
                                userService.add(user);
                            } catch (EventException | InvalidDataException e) {
                                System.err.println(e.getMessage());
                            }
                        } catch (InvalidDataException | ParseException e) {
                            System.err.println(e);
                        }
                    }
                    case 3 -> {
                        try {
                            updateOrDeleteUserFromConsoleCustomize();
                        } catch (EmptyDataException e) {

                        }
                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        admin_UserMenu.run();
        continueExecution = true;
    }

    public void updateOrDeleteUserFromConsoleCustomize() throws EmptyDataException {
        userService.display();
        while (true) {
            try {
                int id = GettingUtils.getInteger("Enter ID for update: ", "User ID must be valid");
                User user;
                if ((user = userService.findById(id)) != null) {
                    System.out.println(user.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(user.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Invalid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                userService.delete(id);
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
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Invalid value!");
                                Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(user, editMenuOptions[selection - 1], newValue);
                                userService.update(id, fieldUpdateMap);
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
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
                            coachService.display();
                        } catch (EmptyDataException e) {
                            System.err.println(e);
                        }
                    }
                    case 2 -> {
                        try {
                            System.out.println("Create new coach");
                            int coachID = coachService.size() + 1 + userService.size();
                            String fullName = GettingUtils.getName("Enter full name: ", "Full Name must be letters");
                            String DoB = GlobalUtils.dateFormat(GettingUtils.getDate("Enter Date of Birth: ", "Date of birth must be yyyy-MM-dd"));
                            String phone = GettingUtils.getPhone("Enter phone number: ", "Phone number must be start with 0 and have 10 digits");
                            String certificate = (GettingUtils.getString("Enter certificate: ", "Certificate must be letters"));
                            Coach coach = new Coach(String.valueOf(coachID), fullName, DoB, phone, GlobalUtils.convertToString(true), certificate);
                            try {
                                coachService.add(coach);
                            } catch (EventException | InvalidDataException e) {
                                System.err.println(e.getMessage());
                            }
                        } catch (InvalidDataException | ParseException e) {
                            System.err.println(e);
                        }
                    }
                    case 3 -> {
                        try {
                            updateOrDeleteCoachFromConsoleCustomize();
                        } catch (EmptyDataException e) {

                        }
                    }
                    case 4 -> {
                        exitMenu();
                    }
                }
            }
        };
        admin_CoachMenu.run();
        continueExecution = true;
    }

    public void updateOrDeleteCoachFromConsoleCustomize() throws EmptyDataException {
        coachService.display();
        while (true) {
            try {
                int id = GettingUtils.getInteger("Enter ID for update: ", "Coach ID must be valid");
                Coach coach;
                if ((coach = coachService.findById(id)) != null) {
                    System.out.println(coach.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(coach.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Invalid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                coachService.delete(id);
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
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Invalid value!");
                                Map<String, Object> fieldUpdateMap = FieldUtils.getFieldValueByName(coach, editMenuOptions[selection - 1], newValue);
                                coachService.update(id, fieldUpdateMap);
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
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
                        try {
                            updateOrDeleteCourseComboFromConsoleCustomize();
                        } catch (EmptyDataException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            assignCourseCombo();
                        } catch (EmptyDataException e) {

                        }
                    }
                    case 5 -> {
                        exitMenu();
                    }
                }
            }
        };
        courseComboMenu.run();
        continueExecution = true;
    }

    public void updateOrDeleteCourseComboFromConsoleCustomize() throws EmptyDataException {
        courseComboService.display();
        while (true) {
            try {
                int id = GettingUtils.getInteger("Enter ID for update: ", "Course Combo ID must be valid");
                CourseCombo courseCombo;
                if ((courseCombo = courseComboService.findById(id)) != null) {
                    System.out.println(courseCombo.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(courseCombo.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Invalid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                courseComboService.delete(courseCombo.getComboId());
                            } catch (EventException e) {
                                System.err.println(e.getMessage());
                            }
                            System.out.println("Delete successfully");
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GettingUtils.getString("Enter new value: ", "Invalid value");
                                courseComboService.update(id, FieldUtils.getFieldValueByName(courseCombo, editMenuOptions[selection - 1], newValue));
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private void createNewComboFromConsole() {
        System.out.println("Create course combo");
        int comboId = courseComboService.size() + 1;
        String comboName = GettingUtils.getString("Enter course combo name: ", "Course combo name must be letters");
        String sales = String.valueOf(GettingUtils.getSales("Enter Sales (percentage between 0 and 1): ", "Sales must be a positive number (0-1)"));
        try {
            CourseCombo courseCombo = new CourseCombo(String.valueOf(comboId), comboName, sales);
            courseComboService.add(courseCombo);
            System.out.println("Combo added successfully.");
        } catch (EventException | InvalidDataException | ParseException e) {
            System.err.println(e.getMessage());
        }

    }

    private void assignCourseCombo() throws EmptyDataException {
        System.out.println("Assign Course Combo");
        courseComboService.display();
        while (true) {
            int courseComboId = GettingUtils.getInteger("Enter course combo ID which assign: ", "Course combo ID must be valid");
            if (courseComboService.existID(courseComboId)) {
                courseService.display();
                while (true) {
                    int courseId = GettingUtils.getInteger("Enter course ID to assign a new combo: ", "Course ID must be valid");
                    try {
                        Course course = courseService.findById(courseId);
                        course.setComboID(String.valueOf(courseComboId));
                        System.err.println("Assign sucessfully");
                        String conn = GettingUtils.getString("Continue ? Y/N: ", "Must be Y/N");
                        if (conn.toUpperCase().equalsIgnoreCase("Y")) {

                        } else {
                            return;
                        }
                    } catch (NotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                }

            } else {
                System.err.println("Course Combo with ID: " + courseComboId + " was not existed");
            }
        }
    }

    public void updateOrDeleteCourseFromConsoleCustomize(int coachID) throws EmptyDataException, NotFoundException {
        coachService.displayCoursesByCoach(coachID);
        while (true) {
            try {
                int id = GettingUtils.getInteger("Course ID: ", "Course ID must be valid");
                Course course;
                if ((course = courseService.search(p -> p.getCoachId() == (coachID) & p.getCourseId() == (id))) != null) {
                    System.out.println(course.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(course.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                courseService.delete(course.getCourseId());
                            } catch (EventException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Delete successfully");
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Cannot be blank");
                                courseService.update(id, FieldUtils.getFieldValueByName(course, editMenuOptions[selection - 1], newValue));
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    public void updateOrDeleteWorkoutFromConsoleCustomize(int courseId) throws EmptyDataException {
        List<String> list = new ArrayList<>();
        for (Workout workout : workoutService.searchWorkoutByCourse(courseId)) {
            list.add(workout.getInfo());
        }
        Printer.printTable("List Of Workout", "Workout", list);
        while (true) {
            try {
                int id = GettingUtils.getInteger("Workout ID: ", "Workout ID must be valid");
                Workout workout;
                if ((workout = workoutService.findById(id)) != null) {
                    System.out.println(workout.getInfo());
                    String[] editMenuOptions = FieldUtils.getEditOptions(workout.getClass());
                    for (int i = 0; i < editMenuOptions.length; i++) {
                        System.out.println((i + 1) + ". " + editMenuOptions[i]);
                    }
                    while (true) {
                        int selection = GettingUtils.getInteger("Enter selection: ", "Please enter a valid option!");
                        if (selection == editMenuOptions.length - 1) {
                            try {
                                courseService.delete(workout.getCourseId());
                            } catch (EventException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Delete successfully");
                            return;
                        } else if (selection == editMenuOptions.length) {
                            return;
                        }
                        while (true) {
                            try {
                                String newValue = GlobalUtils.getValue("Enter new value: ", "Cannot be blank");
                                courseService.update(id, FieldUtils.getFieldValueByName(workout, editMenuOptions[selection - 1], newValue));
                                System.out.println("Update successfully");
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    }
                }
            } catch (NotFoundException ex) {
                System.err.println(ex.getMessage());
            }
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
}
