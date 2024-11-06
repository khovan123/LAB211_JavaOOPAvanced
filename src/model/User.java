package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.GlobalUtils;

public class User extends Person {

    private boolean addventor;

    public User(String userId, String fullName, String DoB, String phone, String active, String addventor) throws InvalidDataException, ParseException {
        super(userId, fullName, DoB, phone, active);
        this.setAddventor(addventor);
        runValidate();
    }

    public boolean isAddventor() {
        return addventor;
    }

    public void setAddventor(String addventor) throws InvalidDataException {
        try {
            this.addventor = GlobalUtils.booleanParse(addventor);
        } catch (InvalidDataException e) {
            throw new InvalidDataException("Addventor must be 'true' or 'false'");
        }
    }

    @Override
    public String getInfo() {
        return String.format("%s\t%s", super.getInfo(), (addventor ? "Weight gain" : "Weight lost"));
    }

    @Override
    public void runValidate() throws InvalidDataException {
        super.runValidate();

    }
}
