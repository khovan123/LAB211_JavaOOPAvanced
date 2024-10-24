package repository;

import exception.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


import model.CoursePacket;

import repository.interfaces.ICoursePacketRepository;

public class CoursePacketRepository implements ICoursePacketRepository {

    private static List<CoursePacket> coursePackets = new ArrayList<>();

    // Sample data format: CP-YYYY, US-YYYY, CS-YYYY

    @Override
    public List<CoursePacket> readFile() throws IOException {
        List<CoursePacket> coursePacketList = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("-> File not found");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                try {
                    CoursePacket coursePacket = new CoursePacket(
                            data[0],
                            data[1],
                            data[2]
                    );
                    coursePacketList.add(coursePacket);
                } catch (Exception e) {
                    throw new IOException("-> Error While Adding - " + e.getMessage());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("-> Error While Reading File - " + e.getMessage());
        }
        return coursePacketList;
    }

    @Override
    public void writeFile(List<CoursePacket> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
