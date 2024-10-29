package model;

import exception.InvalidDataException;
import utils.ObjectUtils;

public class CourseCombo {
    private String comboId;
    private String comboName;
    private double sales;

    public CourseCombo() {
    }

    public CourseCombo(String comboId, String comboName, String sales) throws InvalidDataException {
        this.comboId = comboId;
        this.comboName = comboName;
        this.setSales(sales);
        this.runValidate();
    }

    public String getComboId() {
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

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public void setSales(String sales) throws InvalidDataException {
        try {
            double saleValue = Double.parseDouble(sales);
            if (saleValue < 0 || saleValue > 1) {
                throw new InvalidDataException("-> Sales Must Be A Percentage Between 0 And 1.");
            }
            this.sales = saleValue;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("-> Sales must be a number between 0 and 1.");
        }
    }

    public void runValidate() throws InvalidDataException {
        if (comboId == null || comboId.isEmpty()) {
            throw new InvalidDataException("-> Combo ID Must Not Be Null or Empty.");
        }
        if (!ObjectUtils.valideCourseComboID(comboId)) {
            throw new InvalidDataException("-> Course Combo ID Must Be CByyyy.");
        }
        if (!ObjectUtils.checkSales(String.valueOf(sales))) {
            throw new InvalidDataException("-> Sales Must Be A Percentage Between 0 And 1.");
        }
    }
}
