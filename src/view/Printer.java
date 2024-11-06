package view;

import java.util.Arrays;
import java.util.List;

public class Printer {

    public static <T> void printTable(String title, String column, T[] array) {
        List<T> list = Arrays.asList(array);
        printTable(title, column, list);
    }

    public static <T> void printTable(String title, String column, List<T> list) {
        int columnWidth = getColumnWidth(list);
        String format = "| %-2s | %-" + columnWidth + "s |%n";
        String separator = "-".repeat(columnWidth + 7);

        System.out.println(separator);
        System.out.println(title);
        System.out.println(separator);
        System.out.printf(format, "No", column);
        System.out.println(separator);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf(format, i + 1, list.get(i));
        }
        System.out.println(separator);
    }

    private static <T> int getColumnWidth(List<T> list) {
        int maxWidth = 6; // Minimum width for the "Option" column
        for (T item : list) {
            if (item != null) {
                maxWidth = Math.max(maxWidth, item.toString().length());
            }
        }
        return maxWidth + 2;
    }
}
