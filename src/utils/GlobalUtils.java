
package utils;

import java.util.Date;
import java.util.Scanner;

public class GlobalUtils{
    private static final Scanner scanner = new Scanner(System.in);

    public String getValue(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getDouble(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getDate(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getInteger(String label, String messageError) {
        int value;
        while (true) {
            System.out.print(label + ": ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(messageError + " - Input cannot be empty.");
                continue;
            }

            try {
                value = Integer.parseInt(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println(messageError + " - Please enter a valid integer.");
            }
        }
    }


    public String dateFormat(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Date dateParse(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
