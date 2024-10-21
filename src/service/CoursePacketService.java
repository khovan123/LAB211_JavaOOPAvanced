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

        for (CoursePacket course : coursePacketRepository.getCoursePackets()) {
            System.out.println(course);
        }

    }

    @Override
    public void add(CoursePacket coursePacket) throws EventException {
        coursePacketRepository.add(coursePacket);
    }

    @Override
    public void delete(String id) throws EventException {
        coursePacketRepository.delete(id);
    }

    @Override
    public void update(CoursePacket coursePacket) {
        // empty--dont write this code block
    }

    @Override
    public CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException {
        return coursePacketRepository.search(p);
    }

    @Override
    public CoursePacket findById(String id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
