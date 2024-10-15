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

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
    
    public String getInfo(){
        return String.format("", "");
    }
    
    public void runValidate()throws InvalidDataException{
        
    }

}
