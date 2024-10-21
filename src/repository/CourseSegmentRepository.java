package repository;

import exception.EventException;
import exception.IOException;
import exception.InvalidDataException;
import exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.CourseSegment;
import repository.interfaces.ICourseSegmentRepository;

public class CourseSegmentRepository implements ICourseSegmentRepository {

    private static List<CourseSegment> courseSegments = new ArrayList<>();
    private static WorkoutRepository workoutRepository = new WorkoutRepository();
    private static CoachRepository coachRepository = new CoachRepository();
    //data sample: CS-YYYY, 30 days full body master, CA-YYYY 

    static {

    }

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
