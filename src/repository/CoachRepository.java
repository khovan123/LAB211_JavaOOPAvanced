package repository;

import exception.IOException;
import exception.InvalidDataException;
import model.Coach;
import repository.interfaces.ICoachRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoachRepository implements ICoachRepository {
    private List<Coach> coachList = new ArrayList<>();
    private final String path = "path_to_your_data_directory/"; // Set your path accordingly
    private final String coachPath = "coaches.txt"; // File name to store coach data

    @Override
    public List<Coach> getCoaches() {
        return coachList;
    }

    @Override
    public List<Coach> readFile() throws IOException {
        coachList.clear();
        File file = new File(path + coachPath);
        if (!file.exists()) {
            return coachList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                Coach coach = new Coach(
                        details[0], // Coach ID
                        details[1], // Name
                        new Date(Long.parseLong(details[2])), // Date of Birth
                        details[3], // Phone Number
                        details[4]  // Email
                );
                coachList.add(coach);
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error reading from file: " + coachPath);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }

        return coachList;
    }

    @Override
    public void writeFile(List<Coach> coaches) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + coachPath))) {
            for (Coach coach : coaches) {
                writer.write(coach.toCSV());
                writer.newLine();
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error writing to the file: " + coachPath);
        }
    }
}
