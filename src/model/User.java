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

    // Regular expressions for validation
    private static final Pattern USER_ID_PATTERN = Pattern.compile("US-\\d{4}");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    public User(String userId, String userName, Boolean gender, Date dateOfBirth, String phoneNumber, String email) throws InvalidDataException {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        runValidate();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (!isValidUserId(userId)) {
            throw new IllegalArgumentException("Invalid User ID. It should be in the format 'US-xxxx'.");
        }
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (!isValidName(userName)) {
            throw new IllegalArgumentException("Invalid user name. It should only contain letters and spaces.");
        }
        this.userName = userName;
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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid Email format.");
        }
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
        if (!isValidUserId(userId)) {
            throw new InvalidDataException("User ID is invalid");
        }
        if (!isValidName(userName)) {
            throw new InvalidDataException("User Name is invalid");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new InvalidDataException("Phone Number is invalid");
        }
        if (dateOfBirth == null || dateOfBirth.after(new Date())) {
            throw new InvalidDataException("Date Of Birth is invalid");
        }
        if (gender == null) {
            throw new InvalidDataException("Gender is invalid");
        }
        if (!isValidEmail(email)) {
            throw new InvalidDataException("Email is invalid");
        }
    }


    private boolean isValidUserId(String id) {
        return USER_ID_PATTERN.matcher(id).matches();
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
