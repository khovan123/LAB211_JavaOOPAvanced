package repository;

import exception.IOException;
import java.util.List;
import model.CoursePacket;
import repository.interfaces.ICoursePacketRepository;

public class CoursePacketRepository implements ICoursePacketRepository {

    @Override
    public List<CoursePacket> readFile() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void writeFile(List<CoursePacket> entry) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Sample data format: CP-YYYY, US-YYYY, CS-YYYY

}
