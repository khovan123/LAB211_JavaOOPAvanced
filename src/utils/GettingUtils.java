package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GettingUtils {

    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getString(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            if (string.isBlank() || string.isEmpty() || string.length() == 0) {
                System.out.println(message);
                return getString(label, message);
            }
            return string;
        } catch (Exception e) {
            System.out.println(message);
            return getString(label, message);
        }
    }

    public static int getInteger(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            return Integer.parseInt(string) > 0 ? Integer.parseInt(string) : getInteger(label, message);
        } catch (NumberFormatException e) {
            System.out.println(message);
            return getInteger(label, message);
        }
    }
    
    public static int getPositiveInteger(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            return Integer.parseInt(string) >= 0 ? Integer.parseInt(string) : getInteger(label, message);
        } catch (NumberFormatException e) {
            System.out.println(message);
            return getInteger(label, message);
        }
    }

    public static double getDouble(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            return Double.parseDouble(string) > 0 ? Double.parseDouble(string) : getDouble(label, message);
        } catch (NumberFormatException e) {
            System.out.println(message);
            return getDouble(label, message);
        }
    }
    
    public static double getSales(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            double num = Double.parseDouble(string);
            if(num>0.0 && num<1.0){
                return num;
            }
            System.out.println(message);
            return getSales(label, message);
        } catch (NumberFormatException e) {
            System.out.println(message);
            return getDouble(label, message);
        }
    }

    public static Date getDate(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            return sdf.parse(string);
        } catch (ParseException e) {
            System.out.println(message);
            return getDate(label, message);
        }
    }

    public static Date getDateInFurute(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            Date date = sdf.parse(string);
            if (date.compareTo(new Date()) <= 0) {
                System.out.println(message);
                return getDateInFurute(label, message);
            }
            return date;
        } catch (ParseException e) {
            System.out.println(message);
            return getDate(label, message);
        }
    }

    public static boolean getBoolean(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            return Boolean.parseBoolean(string);
        } catch (NumberFormatException e) {
            System.out.println(message);
            return getBoolean(label, message);
        }
    }

    public static String getID(String label, String message, String regex) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            if (!string.matches(regex)) {
                System.out.println(message);
                return getString(label, message);
            }
            return string;
        } catch (Exception e) {
            System.out.println(message);
            return getString(label, message);
        }
    }

    public static String getText(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            if (!string.matches("^[a-zA-Z\\s\\-&]+$")) {
                System.out.println(message);
                return getString(label, message);
            }
            return string;
        } catch (Exception e) {
            System.out.println(message);
            return getString(label, message);
        }
    }

    public static String getName(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            if (!string.matches("^[A-Z][a-z]*( [A-Z][a-z]*)*$")) {
                System.out.println(message);
                return getString(label, message);
            }
            return string;
        } catch (Exception e) {
            System.out.println(message);
            return getString(label, message);
        }
    }

    public static String getPhone(String label, String message) {
        try {
            System.out.print(label);
            String string = sc.nextLine().trim().replace("\n", "");
            if (!string.matches("0[0-9]{9}")) {
                System.out.println(message);
                return getString(label, message);
            }
            return string;
        } catch (Exception e) {
            System.out.println(message);
            return getString(label, message);
        }
    }
}
