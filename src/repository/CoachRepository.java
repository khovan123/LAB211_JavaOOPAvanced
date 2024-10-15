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
    protected ArrayList<Coach> coachList = new ArrayList<>();

public  ArrayList<Coach> getCoachList() {
    return coachList;
}

    @Override
    public List<Coach> readFile() throws IOException {
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

    @Override
    public void add(Coach coach) throws EventException, InvalidDataException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Coach ID: ");
        String coachId = scanner.nextLine().trim();
        if (coachId.isEmpty()) {
            throw new EventException("Coach ID cannot be empty.");
        }
        boolean exists = coachList.stream().anyMatch(c -> c.getCoachId().equals(coachId));
        if (exists) {
            throw new EventException("Coach with ID " + coachId + " already exists.");
        }
        System.out.print("Enter Coach Name: ");
        String coachName = scanner.nextLine().trim();
        if (coachName.isEmpty()) {
            throw new EventException("Coach Name cannot be empty.");
        }
        coach = new Coach(coachId, coachName);

        coachList.add(coach);
        try {
            writeFile(coachList);
        } catch (IOException e) {
            throw new EventException("Error adding coach with ID: " + coachId);
        }

        System.out.println("Coach added successfully: " + coach);
    }

    @Override
    public void delete(String id) throws EventException {
        boolean removed = coachList.removeIf(coach -> coach.getCoachId().equals(id));
        if (!removed) {
            throw new EventException("Coach not found with ID: " + id);
        }
        try {
            writeFile(coachList);
        } catch (IOException e) {
            throw new EventException("Error deleting coach: " + id);
        }
    }

    @Override
    public Coach search(Predicate<Coach> p) throws NotFoundException {
        return coachList.stream()
                .filter(p)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Coach not found"));
    }

    @Override
    public Coach filter(String entry, String regex) throws InvalidDataException {
        return coachList.stream()
                .filter(coach -> coach.getCoachName().matches(regex))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException("No matching coach found for: " + entry));
    }

    @Override
    public void addFromDatabase() throws EventException {


        throw new UnsupportedOperationException("Method not implemented yet.");
    }
}
