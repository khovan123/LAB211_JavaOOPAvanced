
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

    public Course(String courseId, String courseName, String coachId, String userId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachId = coachId;
        UserId = userId;
    }

    public Course(String courseId, String courseName, String coachId, String userId, WorkoutService workoutService, UserProgressService userProgressService) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachId = coachId;
        UserId = userId;
        this.workoutService = workoutService;
        this.userProgressService = userProgressService;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public void setWorkoutService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    public UserProgressService getUserProgressService() {
        return userProgressService;
    }

    public void setUserProgressService(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", coachId='" + coachId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", workoutService=" + workoutService +
                ", userProgressService=" + userProgressService +
                '}';
    }
}
