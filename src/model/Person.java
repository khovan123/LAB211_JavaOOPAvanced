package model;

import exception.InvalidDataException;
import java.util.Date;
import utils.GlobalUtils;

public class Person {
    
    private String personId;
    private String fullName;
    private Date DoB;
    private String phone;
    private boolean active;
    
    public Person() {
    }
    
    public Person(String personId, String fullName, String DoB, String phone) throws InvalidDataException {
        this.personId = personId;
        this.fullName = fullName;
        this.phone = phone;
        this.setDoB(DoB);
        this.active = true;
    }
    
    public String getPersonId() {
        return personId;
    }
    
    public void setPersonId(String personId) {
        this.personId = personId;
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
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getInfo() {
        return String.format("%s\t%s\t%s\t%s", personId, fullName, GlobalUtils.dateFormat(DoB), phone);
    }
    
    public void runValidate() throws InvalidDataException {
        if (GlobalUtils.validName(fullName)) {
            throw new InvalidDataException("Full Name must be letters");
        }
        if (!GlobalUtils.validDoB(DoB)) {
            throw new InvalidDataException("Date of birth must be in the past");
        }
        if (!GlobalUtils.validPhone(phone)) {
            throw new InvalidDataException("Phone must start with 0 and have 10 digits");
        }
        
    }
    
}
