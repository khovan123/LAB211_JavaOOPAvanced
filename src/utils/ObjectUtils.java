package utils;

public class ObjectUtils {

    public static boolean validID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean validCourseID(String code){
        return code.matches("^CS\\d*$");
    }

    public static boolean validCoursePrice(String coursePrice){
        try {
            return Double.parseDouble(coursePrice) > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean valideCourseComboID(String code){
        return code.matches("^CB\\d*$");
    }

    public static boolean validCoachID(String code){
        return code.matches("^C\\d*$");
    }

    public static boolean validCourseComboSale(String sale){
        try {
            return Double.parseDouble(sale) > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
