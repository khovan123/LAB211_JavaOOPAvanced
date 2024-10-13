package model;

public class Coach {

    private String coachId;
    private String coachName;

    public Coach() {
    }

    public Coach(String coachId, String coachName) {
        this.coachId = coachId;
        this.coachName = coachName;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

}
