package model;

import exception.InvalidDataException;

public class Coach {

    private String coachId;
    private String coachName;

    public Coach() {
    }

    public Coach(String coachId, String coachName) throws InvalidDataException {
        this.coachId = coachId;
        this.coachName = coachName;
        this.runValidate();
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) throws InvalidDataException {
        this.coachId = coachId;
        this.runValidate();
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) throws InvalidDataException {
        this.coachName = coachName;
        this.runValidate();
    }

    public String getInfo() {
        return String.format("Coach ID: %s, Coach Name: %s", coachId, coachName);
    }

    public void runValidate() throws InvalidDataException {
        if (coachId == null || coachId.isEmpty()) {
            throw new InvalidDataException("Coach ID is invalid");
        }
        if (coachName == null || coachName.isEmpty()) {
            throw new InvalidDataException("Coach Name is invalid");
        }
    }
}
