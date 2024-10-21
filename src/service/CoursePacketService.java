package service;

import exception.EventException;

import java.util.function.Predicate;

import exception.IOException;
import exception.NotFoundException;
import model.CoursePacket;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;

public class CoursePacketService implements ICoursePacketService {

    private static CoursePacketRepository coursePacketRepository = new CoursePacketRepository();

    public CoursePacketService() throws IOException {

    }

    @Override
    public void display() {
        try {
            for (CoursePacket course : coursePacketRepository.getCoursePackets()) {
                System.out.println(course);
            }
        } catch (Exception e) {
            throw new RuntimeException("-> Error Occur While Displaying Course Packet: " + e.getMessage());
        }

    }

    @Override
    public void add(CoursePacket coursePacket) throws EventException {
        if (!coursePacket.getCoursePacketId().matches("CP-\\d{4}")) {
            throw new EventException("-> Invalid Course Packet ID Format: " + coursePacket.getCoursePacketId());
        }
        try {

            coursePacketRepository.getCoursePackets().add(coursePacket);
            System.out.println("-> Course Packet Added Successfully.");
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Packet - " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) throws EventException {
        try {
            boolean removed = coursePacketRepository.getCoursePackets().removeIf(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id));
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
        for (CoursePacket coursePacket : coursePacketRepository.getCoursePackets()) {
            if (p.test(coursePacket)) {
                return coursePacket;
            }
        }
        throw new NotFoundException("-> Course Packet not found matching the given criteria.");
    }
}
