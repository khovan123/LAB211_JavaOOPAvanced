package repository;

import exception.IOException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Workout;
import repository.interfaces.IWorkoutRepository;

public class WorkoutRepository implements IWorkoutRepository {

    private static List<Workout> workoutList = new ArrayList<>();

    //data sample: WK-YYYY, Leg Day, Do not off, 2, 4, 2, true, CS-YYYY
    static {
        workoutList.add(new Workout("WK-2024", "Leg Day", "Do not off", "2", "4", "2", "true", "CS-0001"));
        workoutList.add(new Workout("WK-2025", "Arm Day", "Focus on biceps", "3", "3", "1", "false", "CS-0002"));
        workoutList.add(new Workout("WK-2026", "Cardio", "Run for 30 mins", "1", "1", "30", "false", "CS-0003"));
    }

    @Override
    public List<Workout> readFile() throws IOException {
        List<Workout> workoutList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File not found!!!");
            return workoutList;
        }

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            try {
                String[] data = line.split(",");
                Workout workout = new Workout(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                workoutList.add(workout);
            } catch (Exception e) {
                throw new IOException("Add failed (" + e.getMessage() + ")");
            }
        }
        return workoutList;
    }

    @Override
    public void writeFile(List<Workout> workoutList) throws IOException {
        System.out.println("Not yet supported!!!");
    }

}
