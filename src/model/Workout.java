package model;

import exception.InvalidDataException;
import utils.ObjectUtils;

public class Workout {

    private String workoutId;
    private String workoutName;
//    private String description;
    private int repetition;
    private int sets;
    private int duration;
//    private boolean done;
    private String courseId;

    public Workout(String workoutId) {
        this.workoutId = workoutId;
    }

    public Workout(String workoutId, String workoutName, String repetition, String sets, String duration, String courseId) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
//        this.description = description;
        this.setRepetition(repetition);
        this.setSets(sets);
        this.setDuration(duration);
//        this.setDone(done);
        this.courseId = courseId;
    }

    public String getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = Integer.parseInt(repetition);
    }

    public int getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = Integer.parseInt(sets);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = Integer.parseInt(duration);
    }

//    public boolean isDone() {
//        return done;
//    }
//
//    public void setDone(String done) {
//        this.done = Boolean.parseBoolean(done);
//    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInfo() {
        return String.format("| %-10s | %-10s | %-5d | %-5d | %-5d | %-10s", getWorkoutId(), getWorkoutName(), getRepetition(), getSets(), getDuration(), getCourseId());
    }

    public void runValidate() throws InvalidDataException {
        if(!ObjectUtils.validCodeWorkout(workoutId)){
            throw new InvalidDataException("Workout ID must be WKYYY with YYY are numbers");
        }
        if(!ObjectUtils.validRepetition(String.valueOf(repetition))){
            throw new InvalidDataException("Repetition must be a positive number");
        }
        if(!ObjectUtils.validSet(String.valueOf(sets))){
            throw new InvalidDataException("Sets must be a positive number");
        }
        if(!ObjectUtils.validDuration(String.valueOf(duration))){
            throw new InvalidDataException("Duration must be a positive number");
        }
    }
}
