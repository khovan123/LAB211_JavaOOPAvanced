package repository;

import exception.IOException;

import java.util.ArrayList;
import java.util.List;

import model.CoursePacket;
import repository.interfaces.ICoursePacketRepository;

public class CoursePacketRepository implements ICoursePacketRepository {

    @Override
    public List<CoursePacket> readFile() throws IOException {
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<CoursePacket> entry) throws IOException {

    }

}
