package service.interfaces;

import exception.EmptyDataException;
import exception.EventException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.function.Predicate;
import model.CoursePacket;

public interface ICoursePacketService extends Service<CoursePacket> {

    @Override
    void display() throws EmptyDataException;

    @Override
    void add(CoursePacket coursePacket) throws EventException;

    @Override
    void delete(String id) throws EventException, NotFoundException;

    @Override
    void update(CoursePacket coursePacket) throws EventException, NotFoundException;

    @Override
    CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException;

    @Override
    CoursePacket filter(String entry, String regex) throws InvalidDataException;

}
