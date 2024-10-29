package service;

import exception.*;

import java.util.Map;
import java.util.function.Predicate;

import model.CoursePacket;
import repository.CoursePacketRepository;
import service.interfaces.ICoursePacketService;

public class CoursePacketService implements ICoursePacketService {


    @Override
    public void display() throws EmptyDataException {

    }

    @Override
    public void add(CoursePacket entry) throws EventException, InvalidDataException {

    }

    @Override
    public void delete(String id) throws EventException, NotFoundException {

    }

    @Override
    public void update(String id, Map<String, Object> entry) throws EventException, NotFoundException {

    }

    @Override
    public CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException {
        return null;
    }

    @Override
    public CoursePacket findById(String id) throws NotFoundException {
        return null;
    }
}
