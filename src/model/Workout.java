package model;

import exception.InvalidDataException;
import utils.ObjectUtils;

public class Workout {

    private String workoutId;
    private String workoutName;
    private int repetition;
    private int sets;
    private int duration;
    private String courseId;

    public Workout(String workoutId) {
        this.workoutId = workoutId;
    }

    public Workout(String workoutId, String workoutName, String repetition, String sets, String duration, String courseId) throws InvalidDataException {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.setRepetition(repetition);
        this.setSets(sets);
        this.setDuration(duration);
        this.courseId = courseId;
        this.runValidate();
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInfo() {
        return String.format("%s\t%s\t%d\t%d\t%d\t%s", getWorkoutId(), getWorkoutName(), getRepetition(), getSets(), getDuration(), getCourseId());
    }

    public void runValidate() throws InvalidDataException {
        if(!ObjectUtils.validCodeWorkout(workoutId)){
            throw new InvalidDataException("Workout ID must be WKYYY with YYY are numbers");
        }
        if(!ObjectUtils.validInteger(String.valueOf(repetition))){
            throw new InvalidDataException("Repetition must be a positive number");
        }
        if(!ObjectUtils.validInteger(String.valueOf(sets))){
            throw new InvalidDataException("Sets must be a positive number");
        }
        if(!ObjectUtils.validInteger(String.valueOf(duration))){
            throw new InvalidDataException("Duration must be a positive number");
        }
    }
}
