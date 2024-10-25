package repository;

import exception.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import model.Schedule;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;
import service.PracticalDayService;
import service.ScheduleService;

public class UserProgressRepository implements IUserProgressRepository {

    //generate with id: CP-YYYY in scheduleRepository
    static {

    }

    @Override
    public List<UserProgress> readFile() throws IOException {
        List<UserProgress> userProgresses = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found at " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] data = line.split(",");

                String userProgressID = data[0];
                Schedule schedule = new Schedule(data[0]);
                UserProgress userProgress = new UserProgress(userProgressID, schedule );
                userProgresses.add(userProgress);
            }
        } catch (Exception e) {
            throw new IOException("Add failed " + e.getMessage());
        }

        return userProgresses;
    }

    @Override
    public void writeFile(List<UserProgress> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
