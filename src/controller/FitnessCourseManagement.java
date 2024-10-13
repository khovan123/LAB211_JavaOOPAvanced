package controller;

import view.Menu;

public class FitnessCourseManagement extends Menu<String> {

    static String title = "FITNESS COURSE";
    static String[] menuOptions = {
    "User",//userService
    "Coach",//coachService
    "Course",//courseService
    "Booking"
    };
    
    public FitnessCourseManagement(){
        this(title,menuOptions);
    }
    
    public FitnessCourseManagement(String title, String [] menuOptions){
        super(title, menuOptions);
    }

    @Override
    public void execute(int selection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static void main(String[] args) {
        FitnessCourseManagement fitnessCourseManagement = new FitnessCourseManagement();
        fitnessCourseManagement.run();
    }

}
