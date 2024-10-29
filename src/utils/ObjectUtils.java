package utils;

import exception.InvalidDataException;

public class ObjectUtils {

    public static boolean validCoachID(String id) {
        return id.matches("C[0-9]{4}");
    }

    public static boolean validUserID(String id) {
        return id.matches("U[0-9]{4}");
      
    public static boolean validCodeNutrition(String code) {
        return code.matches("^NT[0-9]{3}");
    }
    public static boolean validCodePracticalDay(String code) {
        return code.matches("^PD[0-9]{3}");
    }
    public static boolean validCodeSchedule(String code) {
        return code.matches("^SD[0-9]{3}");
    }
    public static boolean validCodeWorkout(String code) {
        return code.matches("^WK[0-9]{3}");
    }

    public static boolean validDouble(String calories) throws InvalidDataException {
        try {
            return Double.parseDouble(calories) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validInteger(String repetition) throws InvalidDataException {
        try {
            return Integer.parseInt(repetition) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
