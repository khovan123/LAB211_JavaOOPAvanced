package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class Workout {

    private int workoutId;
    private String workoutName;
    private int repetition;
    private int sets;
    private int duration;
    private int courseId;

    public Workout() {
    }

    public Workout(String workoutId, String workoutName, String repetition, String sets, String duration, String courseId) throws InvalidDataException, ParseException {
        this.setWorkoutId(workoutId);
        this.workoutName = workoutName;
        this.setRepetition(repetition);
        this.setSets(sets);
        this.setDuration(duration);
        this.setCourseId(courseId);
        this.runValidate();
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(String workoutId)throws ParseException {
        this.workoutId = Integer.parseInt(workoutId);
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

    public void setRepetition(String repetition)throws ParseException {
        this.repetition = Integer.parseInt(repetition);
    }

    public int getSets() {
        return sets;
    }

    public void setSets(String sets) throws ParseException{
        this.sets = Integer.parseInt(sets);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(String duration) throws ParseException{
        this.duration = Integer.parseInt(duration);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId)throws ParseException {
        this.courseId = Integer.parseInt(courseId);
    }

    public String getInfo() {
        return String.format("%s\t%s\t%d\t%d\t%d\t%s", getWorkoutId(), getWorkoutName(), getRepetition(), getSets(), getDuration(), getCourseId());
    }

    public String getInfoForRegistedWorkout() {
        return String.format("%s\t%d\t%d\t%d", getWorkoutName(), getRepetition(), getSets(), getDuration());
    }

    public void runValidate() throws InvalidDataException {

        if (!ObjectUtils.validInteger(String.valueOf(repetition))) {
            throw new InvalidDataException("Repetition must be a positive number");
        }
        if (!ObjectUtils.validInteger(String.valueOf(sets))) {
            throw new InvalidDataException("Sets must be a positive number");
        }
        if (!ObjectUtils.validInteger(String.valueOf(duration))) {
            throw new InvalidDataException("Duration must be a positive number");
        }
        if (!GlobalUtils.validText(workoutName)) {
            throw new InvalidDataException("Workout Name must be letters");
        }
    }
}
