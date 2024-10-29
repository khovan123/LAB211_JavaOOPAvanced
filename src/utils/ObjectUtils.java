package utils;

public class ObjectUtils {

    public static boolean validID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean validCoachID(String id) {
        return id.matches("C[0-9]{4}");
    }

    public static boolean validUserID(String id) {
        return id.matches("U[0-9]{4}");
    }

}
