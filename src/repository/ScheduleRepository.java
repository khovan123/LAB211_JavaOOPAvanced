package repository;

import exception.IOException;
import exception.NotFoundException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.PracticalDay;
import model.Schedule;
import repository.interfaces.IScheduleRepository;
import service.PracticalDayService;

public class ScheduleRepository implements IScheduleRepository {
    //no path, just handle practicalRepository

    //generate Schdule with id of CP-YYYY in practicalDayRepository
    static {

    }

    @Override
    public List<Schedule> readFile() throws IOException {
        List<Schedule> schedules = new ArrayList<>();
        Map<String, List<PracticalDay>> coursePacketMap = new HashMap<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found at " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    line = line.trim();
                    String[] data = line.split(",");
                    String userProgressID = data[0];
                    String coursePacketID = data[1];
                    List<PracticalDay> practicalDays = coursePacketMap.getOrDefault(coursePacketID, new ArrayList<>());
                    PracticalDayService practicalDayService = new PracticalDayService();
                    TreeSet<PracticalDay> practicalDayTreeSet = new TreeSet<>();
                    for (int i = 2; i < data.length; i++) {
                        PracticalDay practicalDay = practicalDayService.findById(data[i]);
                        if (practicalDay != null) {
                            practicalDayTreeSet.add(practicalDay);
                            practicalDays.add(practicalDay);
                        } else {
                            System.out.println("Practical Day with ID " + data[i] + " not found, skipping....");
                        }
                    }
                    coursePacketMap.put(coursePacketID, practicalDays);
                    Schedule schedule = new Schedule(userProgressID, practicalDayTreeSet);
                    schedules.add(schedule);

                } catch (Exception e) {
                    throw new IOException("Add failed " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error reading file: " + e.getMessage());
        }
        coursePacketMap.forEach((key, value) -> {
            System.out.println("Course Packet ID: " + key + ", Practical Days: " + value);
        });

        return schedules;

    }

    @Override
    public void writeFile(List<Schedule> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
