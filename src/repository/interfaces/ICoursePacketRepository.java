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
    List<CoursePacket> readFile() throws IOException;

    @Override
    void writeFile(List<CoursePacket> entry) throws IOException;

    
}
