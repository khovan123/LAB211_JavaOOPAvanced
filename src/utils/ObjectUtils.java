package utils;

public class ObjectUtils {

    public static boolean validID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public static boolean validCodeUserProgress(String code) {
        return code.matches("^UP[0-9]{3}");
    }
    public static boolean validCodeRegistedCourse(String code) {
        return code.matches("^RC[0-9]{3}");
    }
    public static boolean validCodeSchedule(String code) {
        return code.matches("^SD[0-9]{3}");
    }

}
