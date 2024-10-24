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

    public CoursePacketService() throws IOException {
        coursePacketList = new ArrayList<>();
        readFromDataBase();
    }

    public CoursePacketService(List<CoursePacket> coursePacketList) throws IOException {
        this.coursePacketList = coursePacketList;
        readFromDataBase();
    }

    public void readFromDataBase() {
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
            coursePacketList.add(coursePacket);
        } catch (Exception e) {
            throw new EventException("-> Error While Adding Course Packet - " + e.getMessage());
        }
    }


    @Override
    public void delete(String id) throws EventException, NotFoundException {
        try {
            coursePacketList.remove(this.search(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id)));
            System.out.println("-> Course Packet with ID " + id + " has been removed.");
        } catch (Exception e) {
            throw new EventException("-> An error occurred while deleting the Course Packet " + e.getMessage());
        }
    }

    @Override
    public void update(CoursePacket coursePacket) throws NotFoundException, EventException {
        try {
            coursePacketList.remove(this.search(coursePacket1 -> coursePacket1.getCoursePacketId().equalsIgnoreCase(coursePacket.getCoursePacketId())));
            coursePacketList.add(coursePacket);
            System.out.println("-> Updated Course Packet Successfully!");
        } catch (Exception e){
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
        try {
            return search(coursePacket -> coursePacket.getCoursePacketId().equalsIgnoreCase(id));
        } catch (NotFoundException e) {
            throw new NotFoundException("-> Course Packet With ID - " + id + " - Not Found.");
        }
    }

}
