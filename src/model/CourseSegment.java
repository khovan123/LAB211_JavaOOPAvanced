package model;

import exception.InvalidDataException;
import java.util.List;
import service.WorkoutService;

//this class store default course data, can not be change when coursepacket data change
public class CourseSegment extends Course {

    WorkoutService workoutService; //all workout, to know what you do in course

    public CourseSegment() {
        super();
        this.workoutService = new WorkoutService();
    }

    public CourseSegment(String courseId, String courseName, String coachId) {
        super(courseId, courseName, coachId);
        this.workoutService = new WorkoutService();
    }

    public CourseSegment(String courseId, String courseName, String coachId, List<Workout> workouts) {
        super(courseId, courseName, coachId);
        this.workoutService = new WorkoutService();
        for (Workout workout : workouts) {
            this.workoutService.add(workout);
        }
    }

    public CourseSegment(CourseSegment courseSegment) {

    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public String getInfo() {
        return String.format("", "");
    }

    public void runValidate() throws InvalidDataException {

    }
}
