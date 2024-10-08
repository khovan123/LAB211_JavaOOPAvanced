package utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public interface IGeneralUtils {

    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    final Scanner sc = new Scanner(System.in);
    final DecimalFormat df = new DecimalFormat("#.###");

    public String getValue(String label, String messageError);

    public String getDouble(String label, String messageError);

    public String getDate(String label, String messageError);

    public String getInteger(String label, String messageError);

    public String dateFormat(Date date);

    public Date dateParse(String string);
}
