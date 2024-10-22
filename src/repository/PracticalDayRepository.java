package repository;

import exception.IOException;

import java.io.*;
import java.util.TreeSet;

import exception.InvalidDataException;
import model.Nutrition;
import model.PracticalDay;
import repository.interfaces.IPracticalDayRepository;
import utils.GlobalUtils;

public class PracticalDayRepository implements IPracticalDayRepository {

    private static TreeSet<PracticalDay> practicalDays = new TreeSet<>();

    static {
        try {
            Nutrition nutrition1 = new Nutrition("NT-2024", "18");
            Nutrition nutrition2 = new Nutrition("NT-2025", "22");

            practicalDays.add(new PracticalDay("PD-2024", "14/10/2024", nutrition1, "CP-2024"));
            practicalDays.add(new PracticalDay("PD-2025", "15/10/2024", nutrition2, "CP-2025"));
        } catch (InvalidDataException e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    public TreeSet<PracticalDay> getPracticalDays() {
        return practicalDays;
    }

    @Override
    public TreeSet<PracticalDay> readFile() throws IOException {
        TreeSet<PracticalDay> practicalDaysFromFile = new TreeSet<>();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File not found!!!");
            return practicalDaysFromFile;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    Nutrition nutrition = new Nutrition(data[2], data[3]);
                    PracticalDay practicalDay = new PracticalDay(data[0], data[1], nutrition, data[4]);
                    practicalDaysFromFile.add(practicalDay);
                } catch (Exception e) {
                    throw new IOException("Add failed (" + e.getMessage() + ")");
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("Read file failed!!! (" + e.getMessage() + ")");
        }
        return practicalDaysFromFile;
    }

    @Override
    public void writeFile(TreeSet<PracticalDay> practicalDays) throws IOException {
        System.out.println("Not yet supported!!!");
    }

}
