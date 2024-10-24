package service;

import exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import model.CoursePacket;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;

public class CoursePacketService implements ICoursePacketService {

    private static CoursePacketRepository coursePacketRepository = new CoursePacketRepository();
    private static List<CoursePacket> coursePacketList;

    public CoursePacketService() throws IOException {
        coursePacketList = new ArrayList<>();
        readFromDataBase();
    }

    public void readFromDataBase() throws IOException {
        try {
            for (CoursePacket coursePacket : coursePacketRepository.readFile()) {
                add(coursePacket);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void display() throws EmptyDataException {
        if (coursePacketList.isEmpty()) {
            throw new EmptyDataException("-> No Course Packet Found");
        } else {
            for (CoursePacket coursePacket : coursePacketList) {
                System.out.println(coursePacket);
            }
        }
    }

    @Override
    public void add(CoursePacket coursePacket) throws EventException {
        try {
            for (CoursePacket coursePacket1 : coursePacketList) {
                if (coursePacket1.getCoursePacketId().equalsIgnoreCase(coursePacket.getCoursePacketId())) {
                    throw new InvalidDataException(coursePacket.getCoursePacketId() + " Was Existed");
                }
            }
            coursePacketList.add(coursePacket);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Packet - " + e.getMessage());
        }
    }


    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = coursePacketList.removeIf(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id));
            if (!removed) {
                throw new NotFoundException("-> Course Packet with ID " + id + " not found.");
            }
            System.out.println("-> Course Packet with ID " + id + " has been removed.");

        } catch (Exception e) {
            throw new EventException("-> An error occurred while deleting the Course Packet " + e.getMessage());
        }
    }

    @Override
    public void update(CoursePacket coursePacket) {
        // empty--dont write this code block
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
        try {
            return search(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}
