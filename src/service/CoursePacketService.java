package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CoursePacket;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;

public class CoursePacketService implements ICoursePacketService {

    private final CoursePacketRepository coursePacketRepository = new CoursePacketRepository();
    private final List<CoursePacket> coursePacketList;

    public CoursePacketService() {
        coursePacketList = new ArrayList<>();
        readFromDataBase();
    }

    public CoursePacketService(List<CoursePacket> coursePacketList) {
        this.coursePacketList = coursePacketList;
        readFromDataBase();
    }

    public void readFromDataBase() {
        try {
            coursePacketList.addAll(coursePacketRepository.readFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (coursePacketList.isEmpty()) {
            throw new EmptyDataException("-> No Course Packet Found");
        }
        for (CoursePacket coursePacket : coursePacketList) {
            System.out.println(coursePacket);
        }
    }

    @Override
    public void add(CoursePacket coursePacket) throws EventException {
        if (coursePacket == null) {
            throw new EventException("-> Course Packet Cannot Be Null.");
        }

        if (!coursePacket.getCoursePacketId().matches("CP-\\d{4}")) {
            throw new EventException("-> Invalid Course Packet ID Format: " + coursePacket.getCoursePacketId() + " - Must Be CP-YYYY");
        }

        if (existID(coursePacket)) {
            throw new EventException("-> Course Packet With ID - " + coursePacket.getCoursePacketId() + " - Already Exist");
        }
        try {
            coursePacketList.add(coursePacket);
            System.out.println("-> Course Packet Added Successfully!");
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Packet - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new EventException("-> Course Packet ID Cannot Be Null Or Empty.");
        }
        CoursePacket packetToRemove = findById(id);
        if (packetToRemove != null) {
            coursePacketList.remove(packetToRemove);
            System.out.println("-> Course Packet With ID - " + id + " - Removed Successfully");
        } else {
            throw new NotFoundException("-> Course Packet with ID - " + id + " - Not Found.");
        }
    }

    @Override
    public void update(CoursePacket coursePacket) throws NotFoundException, EventException {
        if (coursePacket == null) {
            throw new EventException("-> Course Packet Cannot Be Null.");
        }
        CoursePacket existCourse = findById(coursePacket.getCoursePacketId());
        if (existCourse == null) {
            throw new NotFoundException("-> Course Packet with ID - " + coursePacket.getCoursePacketId() + " - Not Found.");
        }
        try {
            coursePacketList.remove(existCourse);
            coursePacketList.add(coursePacket);
            System.out.println("-> Update Course Packet - " + coursePacket.getCoursePacketId() + " - Successfully");
        } catch (Exception e) {
            throw new EventException("-> Error Occurred While Updating Course Packet With ID - " + coursePacket.getCoursePacketId() + " - " + e.getMessage());
        }
    }

    @Override
    public CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException {
        for (CoursePacket coursePacket : coursePacketList) {
            if (p.test(coursePacket)) {
                return coursePacket;
            }
        }
        throw new NotFoundException("-> Course Packet not found matching the given criteria.");
    }

    @Override
    public CoursePacket findById(String id) throws NotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("-> Course Packet ID cannot be null or empty.");
        }
        for (CoursePacket coursePacket : coursePacketList) {
            if (coursePacket.getCoursePacketId().equalsIgnoreCase(id)) {
                return coursePacket;
            }
        }
        throw new NotFoundException("-> Course Packet With ID - " + id + " - Not Found.");
    }

    public boolean existID(CoursePacket coursePacket) {
        try {
            return findById(coursePacket.getCoursePacketId()) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
