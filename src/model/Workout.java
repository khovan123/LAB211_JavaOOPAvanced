package model;

import exception.InvalidDataException;

public class Workout {

    private String workoutId;
    private String workoutName;
    private String description;
    private int repetition;
    private int sets;
    private int duration;
    private boolean done;
    private String courseSegmentId;

    public Workout(String workoutId) {
        this.workoutId = workoutId;
    }

    public Workout(String workoutId, String workoutName, String description, String repetition, String sets, String duration, String done, String courseSegmentId) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.description = description;
        this.setRepetition(repetition);
        this.setSets(sets);
        this.setDuration(duration);
        this.setDone(done);
        this.courseSegmentId = courseSegmentId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = Boolean.parseBoolean(done);
    }

    public String getCourseSegmentId() {
        return courseSegmentId;
    }

    public void setCourseSegmentId(String courseSegmentId) {
        this.courseSegmentId = courseSegmentId;
    }

    public String getInfo() {
        return String.format("");
    }

    public void runValidate() throws InvalidDataException {

    }
}
