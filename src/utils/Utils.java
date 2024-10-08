package utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public interface Utils {

    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    final Scanner sc = new Scanner(System.in);
    final DecimalFormat df = new DecimalFormat("#.###");
    
    boolean validID(String id);

}
