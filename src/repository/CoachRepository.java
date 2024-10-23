package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.Coach;
import model.User;
import repository.interfaces.ICoachRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class CoachRepository implements ICoachRepository {
    private List<Coach> coachList = new ArrayList<>();

public  List<Coach> getCoaches() {
    return coachList;
}

    @Override
    public List<Coach> readFile() {

        return coachList;
    }

    @Override
    public void writeFile(List<Coach> coachs) throws IOException, java.io.IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(path + coachPath))) {

        } catch (java.io.IOException e) {
            throw new java.io.IOException("Error writing to the file: " + coachPath);
        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }




}
