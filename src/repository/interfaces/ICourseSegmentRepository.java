package repository.interfaces;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.List;
import java.util.function.Predicate;
import model.CourseSegment;

public interface ICourseSegmentRepository extends Repository<CourseSegment, List<CourseSegment>> {

    final String path = "";

    void addFromDatabase() throws EventException;

    List<CourseSegment> readFile() throws IOException;

    void writeFile(List<CourseSegment> courseSegments) throws IOException;

    void add(CourseSegment courseSegment) throws EventException;

    void delete(String id) throws EventException;

    CourseSegment search(Predicate<CourseSegment> p) throws NotFoundException;

    CourseSegment filter(String entry, String regex) throws InvalidDataException;
}
