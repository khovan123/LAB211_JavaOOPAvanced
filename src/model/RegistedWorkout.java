package model;

import exception.InvalidDataException;
import java.text.ParseException;

public class RegistedWorkout {

    private int registedWorkoutID;
    private int workoutID;
    private int practicalDayID;
    private Workout workout;

    public RegistedWorkout() {
    }

    public RegistedWorkout(String registedWorkoutID, String workoutID, String practicalDayID) throws InvalidDataException, ParseException {
        this.setRegistedWorkoutID(registedWorkoutID);
        this.setWorkoutID(workoutID);
        this.setPracticalDayID(practicalDayID);
        this.runValidate();
    }

    public int getRegistedWorkoutID() {
        return registedWorkoutID;
    }

    public void setRegistedWorkoutID(String registedWorkoutID)throws ParseException {
        this.registedWorkoutID = Integer.parseInt(registedWorkoutID);
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(String workoutID)throws ParseException {
        this.workoutID = Integer.parseInt(workoutID);
    }

    public int getPracticalDayID() {
        return practicalDayID;
    }

    public void setPracticalDayID(String practicalDayID)throws ParseException {
        this.practicalDayID = Integer.parseInt(practicalDayID);
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getInfo() {
        return String.format("%s", workout.getInfoForRegistedWorkout());
    }

    public void runValidate() throws InvalidDataException {

    }

}
