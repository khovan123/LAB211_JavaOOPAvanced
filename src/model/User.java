package model;

import exception.InvalidDataException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class User {

    private String userId;
    private String userName;
    private Date dateOfBirth;
    private Boolean gender;
    private String phoneNumber;
    private String email;

    public User(String userId, String userName, Boolean gender, Date dateOfBirth, String phoneNumber, String email) throws InvalidDataException {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        runValidate();
    }

    // Getters and Setters with validation logic
    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) {
        if (!Pattern.matches("([A-Z][a-z]*\\s*)+", userName)) {
            throw new IllegalArgumentException("Invalid user name. Each word must start with an uppercase letter.");
        }
        this.userName = userName;
    }

    public Date getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getGender() { return gender; }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
                "---------------------------------------------------------\n" +
                        "User ID: %s\n" +
                        "User Name: %s\n" +
                        "Gender: %s\n" +
                        "Date of Birth: %s\n" +
                        "Phone Number: %s\n" +
                        "Email: %s\n" +
                        "---------------------------------------------------------\n",
                userId, userName, gender ? "Male" : "Female", dateFormat.format(dateOfBirth), phoneNumber, email
        );
    }

    public String toCSV() {
        return String.format("%s,%s,%d,%s,%s,%s",
                userId, userName, dateOfBirth.getTime(), gender, phoneNumber, email);
    }

    private void runValidate() throws InvalidDataException {
        if (userId == null || userId.isEmpty()) throw new InvalidDataException("User ID is invalid");
        if (userName == null || userName.isEmpty()) throw new InvalidDataException("User Name is invalid");
        if (phoneNumber == null || phoneNumber.isEmpty()) throw new InvalidDataException("Phone Number is invalid");
        if (dateOfBirth == null) throw new InvalidDataException("Date Of Birth is invalid");
        if (gender == null) throw new InvalidDataException("Gender is invalid");
        if (email == null || email.isEmpty()) throw new InvalidDataException("Email is invalid");
    }
}
