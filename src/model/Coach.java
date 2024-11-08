package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.GlobalUtils;

public class Coach extends Person {

    private String certificate;

    public Coach() {
    }

    public Coach(String coachId, String coachName, String DoB, String phone, String active, String certificate) throws InvalidDataException, ParseException {
        super(coachId, coachName, DoB, phone, active);
        this.certificate = certificate;
        this.runValidate();
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Override
    public String getInfo() {
        return String.format("%s\t%s", super.getInfo(), certificate);
    }

    @Override
    public void runValidate() throws InvalidDataException {
        super.runValidate();

        if (!GlobalUtils.validText(certificate)) {
            throw new InvalidDataException("Certificate must be letters");
        }
    }
}
