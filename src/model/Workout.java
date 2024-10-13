package model;

public class Workout {

    private String workoutId;
    private String workoutName;
    private String description;
    private int repetition;
    private int sets;
    private int duration;
    private boolean done;
    private String courseSegmentId;

    public Workout() {
    }

    public Workout(String workoutId, String workoutName, String description, int repetition, int sets, int duration, boolean done, String courseSegmentId) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.description = description;
        this.repetition = repetition;
        this.sets = sets;
        this.duration = duration;
        this.done = done;
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

    public String getCourseSegmentId() {
        return courseSegmentId;
    }

    public void setCourseSegmentId(String courseSegmentId) {
        this.courseSegmentId = courseSegmentId;
    }

}
