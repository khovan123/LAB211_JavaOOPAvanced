package model;

import exception.InvalidDataException;
import java.text.ParseException;
import java.util.Date;
import utils.GlobalUtils;

public class Person {

    private int personId;
    private String fullName;
    private Date DoB;
    private String phone;
    private boolean active;

    public Person() {
    }

    public Person(String personId, String fullName, String DoB, String phone, String active) throws InvalidDataException, ParseException {
        this.setPersonId(personId);
        this.fullName = fullName;
        this.phone = phone;
        this.setDoB(DoB);
        this.setActive(active);
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) throws ParseException{
        this.personId = Integer.parseInt(personId);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(String DoB) throws InvalidDataException {
        this.DoB = GlobalUtils.dateParse(DoB);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(String active) throws InvalidDataException {
        this.active = GlobalUtils.booleanParse(active);
    }

    public String getInfo() {
        return String.format("%s\t%s\t%s\t%s", personId, fullName, GlobalUtils.dateFormat(DoB), phone);
    }

    public void runValidate() throws InvalidDataException {
        if (!GlobalUtils.validName(fullName)) {
            throw new InvalidDataException("Full Name must be letters");
        }
        if (!GlobalUtils.validPhone(phone)) {
            throw new InvalidDataException("Phone must start with 0 and have 10 digits");
        }

    }

}
