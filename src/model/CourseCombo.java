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
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(String sales) throws InvalidDataException {
        try {
            double saleValue = Double.parseDouble(sales);
            if (saleValue <= 0) {
                throw new InvalidDataException("-> Sales must be larger than 0.");
            }
            this.sales = saleValue;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("-> Sales must be a number.");
        }
    }

    public String getInfo() {
        return String.format("Combo ID: %s, Combo Name: %s, Sales: %.2f", comboId, comboName, sales);
    }

    public void validate() throws InvalidDataException {
        if (!ObjectUtils.valideCourseComboID(comboId)) {
            throw new InvalidDataException("-> Course Combo ID Must Be CByyyy.");
        }
        if (!ObjectUtils.validCourseComboSale(String.valueOf(sales))) {
            throw new InvalidDataException("-> Sales Must Be Larger Than 0.");
        }
    }
}
