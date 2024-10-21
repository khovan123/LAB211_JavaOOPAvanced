package repository;

import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.Coach;
import repository.interfaces.ICoachRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class CoachRepository implements ICoachRepository {

    @Override
    public List<Coach> readFile() throws IOException {
        ArrayList<Coach> coachList = new ArrayList<>();
        String line;
        coachList.clear();
        try (BufferedReader input = new BufferedReader(new FileReader(path + coachPath))) {
            while ((line = input.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    coachList.add(new Coach(data[0], data[1]));
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + coachPath, e);
        } catch (Exception e) {
            throw new IOException("Error reading the file: " + coachPath, e);
        }
        return coachList;
    }

    @Override
    public void writeFile(List<Coach> coachs) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path + coachPath))) {
            for (Coach coach : coachs) {
                output.write(coach.getCoachId() + "," + coach.getCoachName());
                output.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error writing to the file: " + coachPath, e);
        }
    }
    //data sample: CA-YYYY, Cris Rona
}
