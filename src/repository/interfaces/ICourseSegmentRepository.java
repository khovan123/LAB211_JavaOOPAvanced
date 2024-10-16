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

    

    List<CourseSegment> readFile() throws IOException;

    void writeFile(List<CourseSegment> courseSegments) throws IOException;

    
}
