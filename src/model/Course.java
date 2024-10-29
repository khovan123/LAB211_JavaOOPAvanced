package model;

import exception.InvalidDataException;
import service.WorkoutService;
import utils.GlobalUtils;
import utils.ObjectUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Course {

    private String courseId;
    private String courseName;
    private boolean addventor;
    private Date generateDate;
    private double price;
    private String comboID;
    private String coachId;
    private WorkoutService workoutService;

    public Course() {
    }

    public Course(String courseId, String courseName, String addventor, String generateDate, String price, String comboID, String coachId, List<Workout> workouts) throws InvalidDataException {
        this.setCourseId(courseId);
        this.setCourseName(courseName);
        this.setAddventor(addventor);
        this.setGenerateDate(generateDate);
        this.setPrice(price);
        this.setComboID(comboID);
        this.setCoachId(coachId);
        this.setWorkoutService(workouts);
        this.runValidate();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isAddventor() {
        return addventor;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public double getPrice() {
        return price;
    }

    public String getComboID() {
        return comboID;
    }

    public String getCoachId() {
        return coachId;
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public double getAmount(CourseCombo courseCombo)  {
        return price - (price * courseCombo.getSales());
    }

    public String getInfo() {
        return String.format("Course ID: %s, Course Name: %s, Adventor: %b, Generate Date: %s, Price: %.2f, Combo ID: %s, Coach ID: %s",
                courseId, courseName, addventor, GlobalUtils.getDateString(generateDate), price, comboID, coachId);
    }

    public void setCourseId(String courseId) throws InvalidDataException {
        if (!ObjectUtils.validCourseID(courseId)) {
            throw new InvalidDataException("-> Course ID Must Follow Format CSyyyy.");
        }
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setAddventor(String addventor) throws InvalidDataException {
        if (addventor.equalsIgnoreCase("true") || addventor.equalsIgnoreCase("false")) {
            this.addventor = Boolean.parseBoolean(addventor);
        } else {
            throw new InvalidDataException("-> Adventor Must Be 'true' Or 'false'.");
        }
    }

    public void setGenerateDate(String generateDate) throws InvalidDataException {
        try {
            this.generateDate = GlobalUtils.getDate(generateDate);
            if (this.generateDate == null) throw new ParseException("", 0);
        } catch (ParseException e) {
            throw new InvalidDataException("-> Generate Date Must Be In Format dd/MM/yyyy.");
        }
    }

    public void setPrice(String price) throws InvalidDataException {
        try {
            this.price = Double.parseDouble(price);
            if (this.price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new InvalidDataException("-> Price Must Be A Positive Number.");
        }
    }

    public void setComboID(String comboID) throws InvalidDataException {
        if (!ObjectUtils.valideCourseComboID(comboID)) {
            throw new InvalidDataException("-> Combo ID Must Follow Format CByyyy.");
        }
        this.comboID = comboID;
    }

    public void setCoachId(String coachId) throws InvalidDataException {
        if (!ObjectUtils.validCoachID(coachId)) {
            throw new InvalidDataException("-> Coach ID Must Follow Format Cyyyy.");
        }
        this.coachId = coachId;
    }

    public void setWorkoutService(List<Workout> workouts) {
        this.workoutService = new WorkoutService(workouts);
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.validCourseID(courseId)) {
            throw new InvalidDataException("-> Course ID Must Follow Format CSyyyy.");
        }
        if (courseName == null || courseName.isEmpty()) {
            throw new InvalidDataException("-> Course Name Must Not Be Empty.");
        }
        if (!ObjectUtils.validCoachID(coachId)) {
            throw new InvalidDataException("-> Coach ID Must Follow Format Cyyyy.");
        }
        if (!ObjectUtils.valideCourseComboID(comboID)) {
            throw new InvalidDataException("-> Combo ID Must Follow Format CByyyy.");
        }
        if (!ObjectUtils.validCoursePrice(String.valueOf(price)) || price <= 0) {
            throw new InvalidDataException("-> Price Must Be A Positive Number.");
        }
        if (generateDate == null) {
            throw new InvalidDataException("-> Generate Date Must Not Be Null.");
        }
    }
}
