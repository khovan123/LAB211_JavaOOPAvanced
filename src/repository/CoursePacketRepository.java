package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CoursePacket;
import repository.interfaces.ICoursePacketRepository;

public class CoursePacketRepository implements ICoursePacketRepository {

    private static List<CoursePacket> coursePackets = new ArrayList<>();
    private static CourseSegmentRepository courseSegmentRepository = new CourseSegmentRepository();
    private static UserRepository userRepository = new UserRepository();
    private static UserProgressRepository userProgressRepository = new UserProgressRepository();
    // Sample data format: CP-YYYY, US-YYYY, CS-YYYY

    public List<CoursePacket> getCoursePackets() {
        return coursePackets;
    }

    public static void setCoursePackets(List<CoursePacket> coursePackets) {
        CoursePacketRepository.coursePackets = coursePackets;
    }

    @Override
    public List<CoursePacket> readFile() throws IOException {

        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<CoursePacket> entry) throws IOException {

    }

}
