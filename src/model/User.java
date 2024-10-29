package model;

import exception.InvalidDataException;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class User extends Person {

    private boolean addventor;

    public User() {
    }

    public User(String userId, String fullName, String DoB, String phone, String addventor) throws InvalidDataException {
        super(userId, fullName, DoB, phone);
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
        if (!ObjectUtils.validUserID(getPersonId())) {
            throw new InvalidDataException("User ID must be UXXXX with XXXX are numbers");
        }
    }
}
