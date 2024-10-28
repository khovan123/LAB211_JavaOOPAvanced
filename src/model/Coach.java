package model;

import exception.InvalidDataException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Coach {
    private String coachId;
    private String coachName;
    private String coachEmail;
    private String phoneNumber;
    private Date dateOfBirth;

    public Coach(String coachId, String coachName, Date dateOfBirth, String phoneNumber, String coachEmail) throws InvalidDataException {
        this.coachId = coachId;
        this.coachName = coachName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.coachEmail = coachEmail;
        runValidate();
    }

    // Getters and Setters with validation logic
    public String getCoachId() { return coachId; }
    public void setCoachId(String coachId) { this.coachId = coachId; }

    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) {
        if (!Pattern.matches("([A-Z][a-z]*\\s*)+", coachName)) {
            throw new IllegalArgumentException("Invalid coach name. Each word must start with an uppercase letter.");
        }
        this.coachName = coachName;
    }

    public String getCoachEmail() { return coachEmail; }
    public void setCoachEmail(String coachEmail) { this.coachEmail = coachEmail; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
                "---------------------------------------------\n" +
                        "Coach ID   : %s\n" +
                        "Coach Name : %s\n" +
                        "BirthDay   : %s\n" +
                        "PhoneNumber: %s\n" +
                        "Email      : %s\n" +
                        "---------------------------------------------",
                coachId, coachName, dateFormat.format(dateOfBirth), phoneNumber, coachEmail
        );
    }

    public String toCSV() {
        return String.format("%s,%s,%d,%s,%s",
                coachId, coachName, dateOfBirth.getTime(), phoneNumber, coachEmail);
    }

    private void runValidate() throws InvalidDataException {
        if (coachId == null || coachId.isEmpty()) throw new InvalidDataException("Coach ID is invalid");
        if (coachName == null || coachName.isEmpty()) throw new InvalidDataException("Coach Name is invalid");
        if (phoneNumber == null || phoneNumber.isEmpty()) throw new InvalidDataException("Phone Number is invalid");
        if (dateOfBirth == null) throw new InvalidDataException("Date Of Birth is invalid");
        if (coachEmail == null || coachEmail.isEmpty()) throw new InvalidDataException("Email is invalid");
    }
}
