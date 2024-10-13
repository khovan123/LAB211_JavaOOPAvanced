package model.sub;

public class Workout {

    private String workoutId;
    private String workoutName;
    private String description;
    private int repetition;
    private int sets;
    private int duration;
    private boolean done;

    public Workout() {
    }

    public Workout(String workoutId, String workoutName, String description, int repetition, int sets, int duration, boolean done) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.description = description;
        this.repetition = repetition;
        this.sets = sets;
        this.duration = duration;
        this.done = done;
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

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString(){
        return String.format("%-15s | %-20s | %-25s | %-8d rep| %-8d sets| %-8d min| %s", workoutId, workoutName, description, repetition, sets, duration, done ? "✔" : "✘");
    }
}
