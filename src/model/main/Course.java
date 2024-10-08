
package model.main;

import service.UserProgressService;
import service.WorkoutService;

public class Course {
    String courseId;
    String courseName;
    String coachId;
    String UserId;
    WorkoutService workoutService; //all workout, to know what you do in course
    UserProgressService userProgressService;
    

}
