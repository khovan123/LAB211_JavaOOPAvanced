
package repository;

import exception.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import model.Schedule;
import model.UserProgress;
import repository.interfaces.IUserProgressRepository;
import service.PracticalDayService;


public class UserProgressRepository implements IUserProgressRepository{

   
    //generate with id: CP-YYYY in scheduleRepository
    static{
        
    }

    @Override
    public List<UserProgress> readFile() throws IOException {
        List<UserProgress> userProgresses = new ArrayList<>();
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    System.err.println("Skipping empty line.");
                    continue;
                }

                String[] data = line.split(",");
                if (data.length != 2) {  
                    System.err.println("Invalid line (incorrect number of fields): " + line);
                    continue;
                }
                String userProgressID = data[0];
                userProgresses.add(new UserProgress(userProgressID));
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return userProgresses;
    }

    @Override
    public void writeFile(List<UserProgress> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
