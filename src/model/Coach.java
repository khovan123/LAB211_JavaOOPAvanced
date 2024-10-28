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


    private static final Pattern COACH_ID_PATTERN = Pattern.compile("CA-\\d{4}");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    public Coach(String coachId, String coachName, Date dateOfBirth, String phoneNumber, String coachEmail) throws InvalidDataException {
        this.coachId = coachId;
        this.coachName = coachName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.coachEmail = coachEmail;
        runValidate();
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        if (!isValidCoachId(coachId)) {
            throw new IllegalArgumentException("Invalid Coach ID. It should be in the format 'CA-xxxx'.");
        }
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        if (!isValidName(coachName)) {
            throw new IllegalArgumentException("Invalid coach name. Each word must start with an uppercase letter.");
        }
        this.coachName = coachName;
    }

    public String getCoachEmail() {
        return coachEmail;
    }

    public void setCoachEmail(String coachEmail) {
        if (!isValidEmail(coachEmail)) {
            throw new IllegalArgumentException("Invalid Email format.");
        }
        this.coachEmail = coachEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.after(new Date())) {
            throw new IllegalArgumentException("Date of Birth must be a valid date in the past.");
        }
        this.dateOfBirth = dateOfBirth;
    }

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
        if (!isValidCoachId(coachId)) {
            throw new InvalidDataException("Coach ID is invalid");
        }
        if (!isValidName(coachName)) {
            throw new InvalidDataException("Coach Name is invalid");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new InvalidDataException("Phone Number is invalid");
        }
        if (dateOfBirth == null || dateOfBirth.after(new Date())) {
            throw new InvalidDataException("Date Of Birth is invalid");
        }
        if (!isValidEmail(coachEmail)) {
            throw new InvalidDataException("Email is invalid");
        }
    }

    // Validation methods
    private boolean isValidCoachId(String id) {
        return COACH_ID_PATTERN.matcher(id).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}
