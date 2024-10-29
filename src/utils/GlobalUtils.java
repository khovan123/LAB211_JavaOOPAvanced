package utils;

import exception.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;

public class GlobalUtils {

    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

    public static boolean validDoB(Date dob) {
        return new Date().compareTo(dob) > 0;
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

    /**
     * Prompts the user for input and validates it using a provided predicate.
     * If the input is invalid, an error message is displayed. The user is
     * repeatedly prompted until valid input is received.
     *
     * @param inputPrompt The message to display when asking for input.
     * @param errorPrompt The message to display when the input is invalid.
     * @param validator A predicate that defines the validation logic for the
     * input.
     * @return The validated input string that meets the validation criteria.
     */
    public static String getValidatedInput(String inputPrompt, String errorPrompt, Predicate<String> validator) {
        String input;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print(inputPrompt);
            input = scanner.nextLine();
            if (!validator.test(input)) {
                System.out.println(errorPrompt);
            }
        } while (!validator.test(input));
        return input;
    }

    public static boolean validDateNow(Date date) {
        Date currentDate = new Date();
        if (currentDate.getYear() == date.getYear() && currentDate.getMonth() == date.getMonth() && currentDate.getDay() == date.getDay()) {
            return true;
        }
        return date.compareTo(new Date()) >= 0;
    }

}
