package utils;

import exception.InvalidDataException;
import java.text.*;
import java.util.*;

public class GlobalUtils {

    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static DecimalFormat df = new DecimalFormat("#");

    public static String decimalFormat(double num) {
        return df.format(num);
    }

    public static String getValue(String label, String messageError) {
        try {
            System.out.print(label);
            return sc.nextLine().trim();
        } catch (Exception e) {
            getValue(label, messageError);
        }
        return null;

    }

    public static String dateFormat(Date date) {
        return sdf.format(date);
    }

    public static Date dateParse(String dateString) throws InvalidDataException {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidDataException("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }

    public static boolean booleanParse(String string) throws InvalidDataException {
        try {
            return Boolean.parseBoolean(string);
        } catch (Exception e) {
            throw new InvalidDataException(e);
        }
    }

    public static boolean validText(String text) {
        return text.matches("^[a-zA-Z\\s\\-&]+$");
    }

    public static boolean validName(String name) {
        return name.matches("^[A-Z][a-z]*( [A-Z][a-z]*)*$");
    }

    public static boolean validPhone(String phone) {
        return phone.matches("0[0-9]{9}");
    }

    public static String convertToString(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Double
                || obj instanceof Float
                || obj instanceof Boolean
                || obj instanceof Byte
                || obj instanceof Short
                || obj instanceof Character) {
            return obj.toString();
        } else {
            throw new IllegalArgumentException("Unsupported object type: " + obj.getClass().getName());
        }
    }

    public static boolean validDateNow(Date date) {
        Date currentDate = new Date();
        if (currentDate.getYear() == date.getYear() && currentDate.getMonth() == date.getMonth() && currentDate.getDay() == date.getDay()) {
            return true;
        }
        return date.compareTo(new Date()) >= 0;
    }

}
