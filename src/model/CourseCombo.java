package model;

public class CourseCombo {
    private String comboId;
    private String comboName;
    private double sales;

    public CourseCombo(String comboId, String comboName, double sales) {
        this.comboId = comboId;
        this.comboName = comboName;
        this.sales = sales;
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

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getInfo() {
        return String.format("");
    }
}
