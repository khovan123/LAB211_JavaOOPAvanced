package service;

import exception.EventException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import model.CoursePacket;
import model.CourseSegment;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;

public class CoursePacketService implements ICoursePacketService {

    private static CoursePacketRepository coursePacketRepository = new CoursePacketRepository();
    private static List<CoursePacket> coursePacketList = new ArrayList<>();

    public CoursePacketService() throws IOException {

    }

    public void addCoursePacketFromRepository() throws IOException {
        try {
            for (CoursePacket coursePacket : coursePacketRepository.readFile()) {
                add(coursePacket);
            }
        } catch (IOException e) {
            throw new IOException("-> Error While Reading File");
        } catch (Exception e) {
            throw new RuntimeException("-> Error While Adding Course Packet From Repository - " + e.getMessage());
        }
    }

    @Override
    public void display() {
        try {
            if (!coursePacketList.isEmpty()) {
                for (CoursePacket coursePacket : coursePacketList) {
                    System.out.println(coursePacket);
                }
            } else {
                System.out.println("-> The List Is Empty");
            }
        } catch (Exception e) {
            throw new RuntimeException("-> Error Occur While Displaying Course Packet: " + e.getMessage());
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
        } catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }
    }

}
