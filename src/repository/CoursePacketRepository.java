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

    @Override
    public void addFromDatabase() throws EventException {

    }

    @Override
    public List<CoursePacket> readFile() throws IOException {

        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<CoursePacket> entry) throws IOException {

    }

    @Override
    public void add(CoursePacket coursePacket) throws EventException {
        try {
            // Check if the user ID exists in the user repository
            if (!userRepository.getUsers().stream().anyMatch(user -> user.getUserId().equals(coursePacket.getUserId()))) {
                throw new InvalidDataException("-> User ID Does Not Exist: " + coursePacket.getUserId());
            }

            // Check if the course segment ID exists in the course segment repository
            if (!courseSegmentRepository.getCourseSegments().stream().anyMatch(courseSegment -> courseSegment.getCourseId().equals(coursePacket.getCourseSegmentId()))) {
                throw new InvalidDataException("-> Course Segment ID Does Not Exist: " + coursePacket.getCourseSegmentId());
            }

            // Check if the course packet ID already exists in the user progress repository
            if (userProgressRepository.getUserProgresses().stream().anyMatch(userProgress ->
                    userProgress.getUserId().equals(coursePacket.getCoursePacketId()))) {
                throw new InvalidDataException("-> Course Packet ID Already Exists: " + coursePacket.getCoursePacketId());
            }

            // Validate format of the course packet ID
            if (!coursePacket.getCoursePacketId().matches("CP-\\d{4}")) {
                throw new InvalidDataException("-> Invalid Course Packet ID Format: " + coursePacket.getCoursePacketId());
            }

            // Validate format of the user ID
            if (!coursePacket.getUserId().matches("US-\\d{4}")) {
                throw new InvalidDataException("-> Invalid User ID Format: " + coursePacket.getUserId());
            }

            // Validate format of the course segment ID
            if (!coursePacket.getCourseSegmentId().matches("CS-\\d{4}")) {
                throw new InvalidDataException("-> Invalid Course Segment ID Format: " + coursePacket.getCourseSegmentId());
            }

            // Add the course packet to the list
            coursePackets.add(coursePacket);
            System.out.println("-> Course Packet Added Successfully.");
        } catch (InvalidDataException ide) {
            System.out.println("-> Invalid Data Exception: " + ide.getMessage());
        } catch (Exception e) {
            System.out.println("-> Error While Adding Course Packet - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = coursePackets.removeIf(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id));
            if (removed) {
                System.out.println("-> Course With ID " + id + " Has Been Removed");
            } else {
                System.out.println("-> Course With ID " + id + " Not Found");
            }
        } catch (Exception e) {
            System.out.println("-> Error While Deleting ID - " + id + " " + e.getMessage());
        }
    }

    @Override
    public CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException {
        try {
            for (CoursePacket c : coursePackets) {
                if (p.test(c)) {
                    return c;
                }
            }
            throw new NotFoundException("Course Packet Not Found.");
        } catch (Exception e) {
            System.out.println("-> Error While Searching Course Packet - " + e.getMessage());
        }
        return null;
    }

    @Override
    public CoursePacket filter(String entry, String regex) throws InvalidDataException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
