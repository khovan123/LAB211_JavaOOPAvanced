package utils;

import exception.InvalidDataException;

public class ObjectUtils {

    public boolean validID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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

    public static boolean validCalories(String calories) throws InvalidDataException {
        try {
            return Double.parseDouble(calories) > 0;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Calories must be a number");
        }
    }

    public static boolean validRepetition(String repetition) throws InvalidDataException {
        try {
            return Integer.parseInt(repetition) > 0;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Repetition must be a number");
        }
    }
    public static boolean validSet(String set) throws InvalidDataException {
        try {
            return Integer.parseInt(set) > 0;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Set must be a number");
        }
    }

    public static boolean validDuration(String duration) throws InvalidDataException {
        try {
            return Integer.parseInt(duration) > 0;
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Duration must be a number");
        }
    }

}
