package model;

import exception.InvalidDataException;
import service.WorkoutService;
import utils.GlobalUtils;
import utils.ObjectUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Course {

    private int courseId;
    private String courseName;
    private boolean addventor;
    private Date generateDate;
    private double price;
    private int comboID;
    private int coachId;
    private WorkoutService workoutService;

    public Course() {
    }

    public Course(String courseId, String courseName, String addventor, String generateDate, String price, String comboID, String coachId) throws InvalidDataException, ParseException {
        this.setCourseId(courseId);
        this.setCourseName(courseName);
        this.setAddventor(addventor);
        this.setGenerateDate(generateDate);
        this.setPrice(price);
        this.setComboID(comboID);
        this.setCoachId(coachId);
        this.runValidate();
    }

    public int getCourseId() {
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

    public int getComboID() {
        return comboID;
    }

    public int getCoachId() {
        return coachId;
    }

    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public double getAmount(CourseCombo courseCombo) {
        return price - (price * courseCombo.getSales());
    }

    public String getInfo() {
        return String.format(
                "%s\t%s\t%s\t%s\t%.3f",
                courseId, courseName, (addventor?"Weight gain":"Weight lost"), GlobalUtils.dateFormat(generateDate), price
        );
    }

    public String getRegistedInfo() {
        return String.format(
                "%s\t%s\t",
                courseName, (addventor?"Weight gain":"Weight lost")
        );
    }

    public void setCourseId(String courseId) throws ParseException {
        this.courseId = Integer.parseInt(courseId);
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setAddventor(String addventor) throws InvalidDataException {
        if (addventor.equalsIgnoreCase("true") || addventor.equalsIgnoreCase("false")) {
            this.addventor = Boolean.parseBoolean(addventor);
        } else {
            throw new InvalidDataException("Adventor Must Be 'true' Or 'false'.");
        }
    }

    public void setGenerateDate(String generateDate) throws InvalidDataException {
        try {
            this.generateDate = GlobalUtils.dateParse(generateDate);
            if (this.generateDate == null) {
                throw new ParseException("", 0);
            }
        } catch (ParseException e) {
            throw new InvalidDataException("Generate Date Must Be In Format yyyy-MM-dd");
        }
    }

    public void setPrice(String price) throws InvalidDataException {
        try {
            this.price = Double.parseDouble(price);
            if (this.price <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Price Must Be A Positive Number.");
        }
    }

    public void setComboID(String comboID) {
        this.comboID = Integer.parseInt(comboID);
    }

    public void setCoachId(String coachId) throws ParseException {
        this.coachId = Integer.parseInt(coachId);
    }

    public void setWorkoutService(List<Workout> workouts) {
        this.workoutService = new WorkoutService(workouts);
    }

    public void runValidate() throws InvalidDataException {
        if (!GlobalUtils.validText(courseName)) {
            throw new InvalidDataException("Course Name Must BE LETTERS.");
        }
        if (!ObjectUtils.validDouble(GlobalUtils.convertToString(price))) {
            throw new InvalidDataException("Price Must Be A Positive Number.");
        }
    }
}
