package repository;

import exception.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import exception.InvalidDataException;
import model.CoursePacket;
import model.CourseSegment;
import repository.interfaces.ICoursePacketRepository;

public class CoursePacketRepository implements ICoursePacketRepository {

    private static List<CoursePacket> coursePackets = new ArrayList<>();

    // Sample data format: CP-YYYY, US-YYYY, CS-YYYY
    static {
        CoursePacket coursePacket1 = new CoursePacket("CP-0001", "US-0001", "CS-0001");
        CoursePacket coursePacket2 = new CoursePacket("CP-0002", "US-0002", "CS-0002");

        coursePackets.add(coursePacket1);
        coursePackets.add(coursePacket2);
    }

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
