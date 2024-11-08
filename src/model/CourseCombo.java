package model;

import exception.InvalidDataException;
import java.text.ParseException;
import utils.ObjectUtils;

public class CourseCombo {

    private int comboId;
    private String comboName;
    private double sales;

    public CourseCombo() {
    }

    public CourseCombo(String comboId, String comboName, String sales) throws InvalidDataException, ParseException {
        this.setComboId(comboId);
        this.comboName = comboName;
        this.setSales(sales);
        this.runValidate();
    }

    public int getComboId() {
        return comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public double getSales() {
        return sales;
    }

    public String getInfo() {
        return String.format("%s, %s, %.2f", comboId, comboName, sales);
    }

    public void setComboId(String comboId) throws ParseException{
        this.comboId = Integer.parseInt(comboId);
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public void setSales(String sales) throws InvalidDataException {
        try {
            this.sales = Double.parseDouble(sales);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Sales must be a number between 0 and 1.");
        }
    }

    public void runValidate() throws InvalidDataException {
        if (!ObjectUtils.checkSales(String.valueOf(sales))) {
            throw new InvalidDataException("Sales must be a number between 0 and 1.");
        }
    }
}
