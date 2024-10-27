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
}
