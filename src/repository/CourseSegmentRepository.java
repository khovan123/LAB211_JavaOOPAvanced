package repository;

import exception.IOException;

import java.util.ArrayList;
import java.util.List;

import model.CourseSegment;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    @Override
    public List<CourseSegment> readFile() throws IOException {
        try {

            throw new UnsupportedOperationException("Not supported yet.");
        } catch (Exception e) {
            System.out.println("-> Error While Reading File - " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void writeFile(List<CourseSegment> courseSegments) throws IOException {
        try {
            throw new UnsupportedOperationException("Not supported yet.");
        } catch (Exception e) {
            System.out.println("-> Error While Writing File - " + e.getMessage());
        }
    }

}
