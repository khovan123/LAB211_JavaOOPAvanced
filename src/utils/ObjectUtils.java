package utils;

import exception.InvalidDataException;

import java.util.Date;

public class ObjectUtils {

//    public static boolean validCoachID(String id) {
//        return id.matches("C[0-9]{4}");
//    }
//
//    public static boolean validUserID(String id) {
//        return id.matches("U[0-9]{4}");
//    }
//
//    public static boolean validCodeNutrition(String code) {
//        return code.matches("NT[0-9]{3}");
//    }
//
//    public static boolean validCodePracticalDay(String code) {
//        return code.matches("PD[0-9]{3}");
//    }
//
//    public static boolean validCodeSchedule(String code) {
//        return code.matches("SD[0-9]{3}");
//    }
//
//    public static boolean validCodeWorkout(String code) {
//        return code.matches("WK[0-9]{3}");
//    }
//
//    public static boolean validCodeCourseCombo(String code) {
//        return code.matches("CB[0-9]{3}");
//    }
//
//    public static boolean validCodeRegistedWorkout(String code) {
//        return code.matches("RW[0-9]{3}");
//    }

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

//    public static boolean validCodeUserProgress(String code) {
//        return code.matches("UP[0-9]{3}");
//    }
//
//    public static boolean validCodeRegistedCourse(String code) {
//        return code.matches("RC[0-9]{3}");
//    }
//
//    public static boolean validCourseID(String code) {
//        return code.matches("CS[0-9]{3}");
//    }

    public static boolean validCoursePrice(String coursePrice) {
        try {
            return Double.parseDouble(coursePrice) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validCourseComboSale(String sale) {
        try {
            return Double.parseDouble(sale) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    public static boolean validCourseRegistedID(String code) {
//        return code.matches("RC[0-9]{3}");
//    }

    public static boolean isRegisteredDateBeforeFinishDate(Date registeredDate, Date finishRegisteredDate) {
        return registeredDate.before(finishRegisteredDate);
    }

    public static boolean checkSales(String sales) {
        try {
            double saleValue = Double.parseDouble(sales);
            return saleValue > 0 && saleValue < 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
