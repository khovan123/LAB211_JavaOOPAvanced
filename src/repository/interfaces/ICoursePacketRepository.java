package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.CoursePacket;

public interface ICoursePacketRepository extends Repository<CoursePacket, List<CoursePacket>> {

    final String path = "";

    @Override
    void addFromDatabase() throws EventException;

    @Override
    List<CoursePacket> readFile() throws IOException;

    @Override
    void writeFile(List<CoursePacket> entry) throws IOException;

    @Override
    void add(CoursePacket coursePacket) throws EventException;

    @Override
    void delete(String id) throws EventException;

    @Override
    CoursePacket search(Predicate<CoursePacket> p) throws NotFoundException;

    @Override
    CoursePacket filter(String entry, String regex) throws InvalidDataException;
}
