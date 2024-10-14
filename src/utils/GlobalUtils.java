package utils;

import java.util.Date;
import java.util.Scanner;

public class GlobalUtils {

    Scanner sc = new Scanner(System.in);

    public String getValue(String label, String messageError) {
        String value = "";
        boolean valid = false;
        do {
            System.out.print(label);
            value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                valid = true;
            } else {
                System.out.println(messageError);
            }
        } while (!valid);
        return null;

    }

    public String getDouble(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getDate(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getInteger(String label, String messageError) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String dateFormat(Date date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Date dateParse(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
