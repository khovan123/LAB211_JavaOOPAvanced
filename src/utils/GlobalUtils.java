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

    public static String getDouble(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static Date getDate(String date) throws InvalidDataException {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new InvalidDataException("Date must be dd/MM/yyyy");
        }
    }

    public static String getDateString(Date date) {
        return sdf.format(date);
    }

    public static int getInteger(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String dateFormat(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static Date dateParse(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
