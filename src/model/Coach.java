package model;

import exception.InvalidDataException;
import utils.GlobalUtils;
import utils.ObjectUtils;

public class Coach extends Person {

    private String certificate;

    public Coach() {
    }

    public Coach(String coachId, String coachName, String DoB, String phone, String certificate) throws InvalidDataException {
        super(coachId, coachName, DoB, phone);
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

        if (!ObjectUtils.validCoachID(getPersonId())) {
            throw new InvalidDataException("Coach ID must be CXXXX with XXXX are numbers");
        }

        if (!GlobalUtils.validText(certificate)) {
            throw new InvalidDataException("Certificate must be letters");
        }
    }
}
