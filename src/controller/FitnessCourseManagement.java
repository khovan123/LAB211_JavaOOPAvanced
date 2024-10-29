package controller;

import exception.EmptyDataException;
import exception.EventException;
import model.Course;
import model.CourseCombo;
import model.RegisteredCourse;
import service.CourseComboService;
import service.CourseService;
import service.RegistedCourseService;
import utils.GlobalUtils;
import view.Menu;

import java.util.ArrayList;

public class FitnessCourseManagement extends Menu<String> {
    private CourseService courseService;
    private CourseComboService courseComboService;
    private RegistedCourseService registedCourseService;

    static String title = "FITNESS COURSE";
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
        courseService = new CourseService();
        courseComboService = new CourseComboService();
        registedCourseService = new RegistedCourseService();
    }

    @Override
    public void execute(int selection) {
        switch (selection) {
            case 1 -> {
                this.runAdminMenu();
            }
            case 2 -> {
                this.runCoachMenu();
            }
            case 3 -> {
                this.runUserMenu();
            }
            case 4 -> {
                exitMenu();
            }
        }
    }

    //----------------------------------------------------------start main menu-----------------------------------------------------
    public void runAdminMenu() {
        String[] adminMenuOptions = {
                "User Management",
                "Coach Management",
                "Course Management",
                "User Progress Management",
                "Return main menu"
        };
    }

    public void runCoachMenu() {
        String[] coachMenuOptions = {
                "Display personal information",
                "Display all your course",
                "Display all member",
                "Create new course",
                "Create new member",
                "Edit your infromation",
                "Edit course",
                "Edit member",
                "Exit"
        };
    }

    public void runUserMenu() {
        String[] userMenuOptions = {
                "Display personal information",
                "Display all your course",
                "Display all your progress in course",
                "Buy course",
                "Edit your information",
                "Edit schedule in course",
                "Exit"
        };
    }
//----------------------------------------------------------end main menu--------------------------------------------------------

//----------------------------------------------------------start admin menu-----------------------------------------------------

    public void runUserManagementMenu() {
        String userMenuOptions[] = {
                "Display all user",
                "Create new user",
                "Edit user",
                "Return main menu"
        };
    }

    public void runCoachManagementMenu() {
        String coachMenuOptions[] = {
                "Display all coach",
                "Create new coach",
                "Edit coach",
                "Return main menu"
        };
    }

    public void runCourseManagementMenu() {
        String courseMenuOptions[] = {
                "Display all course",
                "Create new course",
                "Edit course",
                "Return main menu"
        };

        Menu menu = new Menu("---- COURSE MANAGEMENT ----", courseMenuOptions) {
            @Override
            public void execute(int selection) {
                switch (selection) {
                    case 1 -> {
                        try {
                            courseService.display();
                        } catch (EmptyDataException e) {
                            System.out.println(e.getMessage());
                            ;
                        }
                    }

                    case 2 -> {
                       while (true){

                       }
                    }
                }
            }
        };
        menu.run();
    }

    public void runUserProgressManagementMenu() {
        String userProgressMenuOptions[] = {
                "Display all user progress",
                "Edit user progress",
                "Create user progress", //mean help user purchase course
                "Return main menu"
        };
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
